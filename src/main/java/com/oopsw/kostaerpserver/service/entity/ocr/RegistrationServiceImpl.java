package com.oopsw.kostaerpserver.service.entity.ocr;

import com.oopsw.kostaerpserver.dto.auth.RegisterRequest;
import com.oopsw.kostaerpserver.dto.auth.RegistrationResponse;
import com.oopsw.kostaerpserver.dto.ocr.BusinessVerificationResult;
import com.oopsw.kostaerpserver.repository.dao.UserInfoDAO;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUser;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUserRepository;
import com.oopsw.kostaerpserver.repository.entity.admin.ReviewStatus;
import com.oopsw.kostaerpserver.service.Interface.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserInfoDAO userInfoDAO;
    private final LoginService loginService;
    private final BusinessDocumentVerificationService verificationService;
    private final RegistrationRequestedUserRepository requestedUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RegistrationDocumentStorageService documentStorageService;

    @Override
    public RegistrationResponse register(
        RegisterRequest request,
        MultipartFile document
    ) {
        String normalizedBid = validateNewRegistration(request, document);
        request.setBId(normalizedBid);

        BusinessVerificationResult result =
            verificationService.verify(
                normalizedBid,
                request.getName(),
                request.getStoreName(),
                document
            );

        return switch (result.status()) {
            case APPROVED -> {
                loginService.register(request);
                yield new RegistrationResponse(
                    "APPROVED",
                    "회원가입이 완료되었습니다."
                );
            }
            case REJECTED -> new RegistrationResponse(
                "REJECTED",
                result.message()
            );
            case NEED_REVIEW, RETRY ->
                savePendingRegistration(request, document, result);
        };
    }

    private RegistrationResponse savePendingRegistration(
        RegisterRequest request,
        MultipartFile document,
        BusinessVerificationResult result
    ) {
        String documentPath = null;
        try {
            documentPath = documentStorageService.store(request.getBId(), document);
            requestedUserRepository.save(
                RegistrationRequestedUser.builder()
                    .bId(request.getBId())
                    .pwHash(passwordEncoder.encode(request.getPw()))
                    .name(request.getName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .storeName(request.getStoreName())
                    .storeType(request.getStoreType())
                    .storeCategory(request.getStoreCategory())
                    .marketingAgree(request.isMarketingAgree())
                    .documentPath(documentPath)
                    .reason(result.message())
                    .reviewStatus(ReviewStatus.PENDING)
                    .build()
            );
            return new RegistrationResponse(
                "PENDING",
                "회원가입 신청이 접수되었습니다. 관리자 승인 후 이용할 수 있습니다."
            );
        } catch (RuntimeException exception) {
            documentStorageService.delete(documentPath);
            return new RegistrationResponse(
                "RETRY",
                "회원가입 검토 신청 저장에 실패했습니다. 잠시 후 다시 시도해주세요."
            );
        }
    }

    private String validateNewRegistration(
        RegisterRequest request,
        MultipartFile document
    ) {
        if (request == null) {
            throw new IllegalArgumentException("회원가입 정보는 필수입니다.");
        }

        if (request.getBId() == null || request.getBId().isBlank()) {
            throw new IllegalArgumentException("사업자등록번호는 필수입니다.");
        }

        if (request.getPw() == null || request.getPw().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("대표자명은 필수입니다.");
        }

        if (request.getStoreName() == null || request.getStoreName().isBlank()) {
            throw new IllegalArgumentException("상호명은 필수입니다.");
        }

        if (document == null || document.isEmpty()) {
            throw new IllegalArgumentException("사업자등록증 파일은 필수입니다.");
        }

        String normalizedBid = request.getBId().replaceAll("\\D", "");

        if (normalizedBid.length() != 10) {
            throw new IllegalArgumentException(
                "사업자등록번호 형식이 올바르지 않습니다."
            );
        }

        if (userInfoDAO.getBidCheck(normalizedBid) > 0) {
            throw new IllegalArgumentException(
                "이미 가입된 사업자등록번호입니다."
            );
        }

        if (requestedUserRepository.existsByBusinessIdAndReviewStatus(
            normalizedBid,
            ReviewStatus.PENDING
        )) {
            throw new IllegalArgumentException(
                "이미 관리자 검토 중인 사업자등록번호입니다."
            );
        }

        return normalizedBid;
    }
}
