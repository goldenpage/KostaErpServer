package com.oopsw.kostaerpserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oopsw.kostaerpserver.dto.auth.RegisterRequest;
import com.oopsw.kostaerpserver.dto.auth.RegistrationResponse;
import com.oopsw.kostaerpserver.dto.ocr.BusinessVerificationResult;
import com.oopsw.kostaerpserver.repository.dao.UserInfoDAO;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUser;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUserRepository;
import com.oopsw.kostaerpserver.repository.entity.admin.ReviewStatus;
import com.oopsw.kostaerpserver.service.Interface.LoginService;
import com.oopsw.kostaerpserver.service.entity.ocr.BusinessDocumentVerificationService;
import com.oopsw.kostaerpserver.service.entity.ocr.RegistrationDocumentStorageService;
import com.oopsw.kostaerpserver.service.entity.ocr.RegistrationServiceImpl;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

class RegistrationServiceTest {

    @TempDir
    Path tempDirectory;

    private final AtomicReference<RegistrationRequestedUser> savedRequest =
        new AtomicReference<>();

    private MultipartFile document;
    private RegisterRequest request;

    @BeforeEach
    void setUp() {
        savedRequest.set(null);

        document = new MockMultipartFile(
            "document",
            "business-registration.jpg",
            "image/jpeg",
            new byte[]{1, 2, 3}
        );

        request = RegisterRequest.builder()
            .bId("000-00-00006")
            .pw("password")
            .name("김사장")
            .storeName("김밥전문점")
            .phone("01012345678")
            .email("owner@example.com")
            .storeType("일반음식점")
            .storeCategory("한식")
            .build();
    }

    @Test
    void registerStoresReviewRequiredApplicationAsPending() {
        RegistrationServiceImpl service = serviceWithResult(
            BusinessVerificationResult.needReview(
                "0000000006",
                "OCR 인식 정보와 사업자등록정보가 일치하지 않아 "
                    + "관리자 확인이 필요합니다."
            )
        );

        RegistrationResponse response =
            service.register(request, document);

        RegistrationRequestedUser saved = savedRequest.get();

        assertEquals("PENDING", response.status());
        assertNotNull(saved);
        assertEquals("0000000006", saved.getBId());
        assertEquals(
            ReviewStatus.PENDING,
            saved.getReviewStatus()
        );
        assertNotEquals("password", saved.getPwHash());
        assertTrue(
            Files.exists(Path.of(saved.getDocumentPath()))
        );
    }

    @Test
    void registerStoresTechnicalFailureAsPending() {
        RegistrationServiceImpl service = serviceWithResult(
            BusinessVerificationResult.retry(
                null,
                "OCR 서버 요청에 실패했습니다."
            )
        );

        RegistrationResponse response =
            service.register(request, document);

        RegistrationRequestedUser saved = savedRequest.get();

        assertEquals("PENDING", response.status());
        assertNotNull(saved);
        assertEquals(
            ReviewStatus.PENDING,
            saved.getReviewStatus()
        );
        assertEquals(
            "OCR 서버 요청에 실패했습니다.",
            saved.getReason()
        );
    }

    @Test
    void registerDoesNotStoreRejectedApplication() {
        RegistrationServiceImpl service = serviceWithResult(
            BusinessVerificationResult.rejected(
                "1234567890",
                "입력한 사업자등록번호와 서류 번호가 일치하지 않습니다."
            )
        );

        RegistrationResponse response =
            service.register(request, document);

        assertEquals("REJECTED", response.status());
        assertNull(savedRequest.get());
    }

    private RegistrationServiceImpl serviceWithResult(
        BusinessVerificationResult verificationResult
    ) {
        BusinessDocumentVerificationService verificationService =
            (businessNumber, representativeName, companyName, file) ->
                verificationResult;

        UserInfoDAO userInfoDAO = proxy(
            UserInfoDAO.class,
            (methodName, args) -> {
                if ("getBidCheck".equals(methodName)) {
                    return 0;
                }

                return 0;
            }
        );

        LoginService loginService = proxy(
            LoginService.class,
            (methodName, args) -> 0
        );

        RegistrationRequestedUserRepository reviewRepository =
            proxy(
                RegistrationRequestedUserRepository.class,
                (methodName, args) -> {
                    if (
                        "countByBIdAndReviewStatus"
                            .equals(methodName)
                    ) {
                        return 0L;
                    }

                    if ("saveAndFlush".equals(methodName)) {
                        RegistrationRequestedUser review =
                            (RegistrationRequestedUser) args[0];

                        savedRequest.set(review);
                        return review;
                    }

                    return null;
                }
            );

        return new RegistrationServiceImpl(
            userInfoDAO,
            loginService,
            verificationService,
            reviewRepository,
            new BCryptPasswordEncoder(),
            new RegistrationDocumentStorageService(
                tempDirectory.toString()
            )
        );
    }

    @SuppressWarnings("unchecked")
    private <T> T proxy(
        Class<T> type,
        StubInvocation invocation
    ) {
        return (T) Proxy.newProxyInstance(
            type.getClassLoader(),
            new Class<?>[]{type},
            (proxy, method, args) ->
                invocation.invoke(method.getName(), args)
        );
    }

    @FunctionalInterface
    private interface StubInvocation {
        Object invoke(String methodName, Object[] args);
    }
}