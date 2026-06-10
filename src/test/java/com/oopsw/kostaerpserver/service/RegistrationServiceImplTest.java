package com.oopsw.kostaerpserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.oopsw.kostaerpserver.dto.auth.RegisterRequest;
import com.oopsw.kostaerpserver.dto.auth.RegistrationResponse;
import com.oopsw.kostaerpserver.dto.ocr.DocumentReviewResponse;
import com.oopsw.kostaerpserver.repository.dao.UserInfoDAO;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUser;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUserRepository;
import com.oopsw.kostaerpserver.repository.entity.admin.ReviewStatus;
import com.oopsw.kostaerpserver.service.Interface.DocumentReviewService;
import com.oopsw.kostaerpserver.service.Interface.LoginService;
import com.oopsw.kostaerpserver.vo.User;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    @Mock
    private RegistrationRequestedUserRepository repository;
    @Mock
    private UserInfoDAO userInfoDAO;
    @Mock
    private DocumentReviewService documentReviewService;
    @Mock
    private LoginService loginService;
    @Mock
    private RegistrationDocumentStorageService documentStorageService;

    private RegistrationServiceImpl service;
    private MockMultipartFile document;

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl(
            repository,
            userInfoDAO,
            documentReviewService,
            loginService,
            new BCryptPasswordEncoder(4),
            documentStorageService
        );
        document = new MockMultipartFile(
            "document",
            "license.png",
            "image/png",
            "image".getBytes()
        );
    }

    @Test
    void registersImmediatelyWhenDocumentIsApproved() {
        RegisterRequest request = request();
        when(documentReviewService.review("1234567890", document))
            .thenReturn(new DocumentReviewResponse("APPROVED", List.of(), "사업자등록증"));

        RegistrationResponse response = service.register(request, document);

        assertEquals("APPROVED", response.status());
        assertEquals("1234567890", request.getBId());
        verify(loginService).register(request);
        verify(documentStorageService, never()).store(eq("1234567890"), eq(document));
    }

    @Test
    void savesPendingReviewWhenOcrNeedsReview() {
        RegisterRequest request = request();
        when(documentReviewService.review("1234567890", document))
            .thenReturn(new DocumentReviewResponse(
                "NEED_REVIEW",
                List.of("OCR 평균 신뢰도가 낮습니다."),
                "사업자등록증"
            ));
        when(documentStorageService.store("1234567890", document))
            .thenReturn("/documents/license.png");

        RegistrationResponse response = service.register(request, document);

        ArgumentCaptor<RegistrationRequestedUser> reviewCaptor =
            ArgumentCaptor.forClass(RegistrationRequestedUser.class);
        verify(repository).save(reviewCaptor.capture());
        RegistrationRequestedUser review = reviewCaptor.getValue();

        assertEquals("PENDING", response.status());
        assertEquals("1234567890", review.getBId());
        assertEquals(ReviewStatus.PENDING, review.getReviewStatus());
        assertTrue(new BCryptPasswordEncoder().matches("password", review.getPwHash()));
        verify(loginService, never()).register(request);
    }

    @Test
    void rejectsRegistrationWhenBusinessNumberDoesNotMatch() {
        RegisterRequest request = request();
        when(documentReviewService.review("1234567890", document))
            .thenReturn(new DocumentReviewResponse(
                "REJECTED",
                List.of("사업자등록번호가 일치하지 않습니다."),
                "사업자등록증"
            ));

        assertThrows(IllegalArgumentException.class, () -> service.register(request, document));

        verify(loginService, never()).register(request);
        verify(documentStorageService, never()).store(eq("1234567890"), eq(document));
    }

    @Test
    void approvesPendingReviewAndCreatesMybatisUser() {
        RegistrationRequestedUser review = RegistrationRequestedUser.builder()
            .bId("1234567890")
            .pwHash("encoded-password")
            .name("김사장")
            .documentPath("/documents/license.png")
            .reviewStatus(ReviewStatus.PENDING)
            .build();
        when(repository.findById(1)).thenReturn(Optional.of(review));

        service.approve(1, "manager");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userInfoDAO).register(userCaptor.capture());
        assertEquals("encoded-password", userCaptor.getValue().getPw());
        assertEquals(ReviewStatus.APPROVED, review.getReviewStatus());
        assertEquals("manager", review.getReviewedBy());
    }

    private RegisterRequest request() {
        return RegisterRequest.builder()
            .bId("123-45-67890")
            .pw("password")
            .name("김사장")
            .build();
    }
}
