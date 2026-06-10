package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.auth.RegisterRequest;
import com.oopsw.kostaerpserver.dto.auth.RegistrationResponse;
import com.oopsw.kostaerpserver.repository.dao.UserInfoDAO;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUser;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUserRepository;
import com.oopsw.kostaerpserver.repository.entity.admin.ReviewStatus;
import com.oopsw.kostaerpserver.service.Interface.LoginService;
import com.oopsw.kostaerpserver.service.Interface.RegistrationService;
import com.oopsw.kostaerpserver.vo.User;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRequestedUserRepository repository;
    private final UserInfoDAO userInfoDAO;
    private final LoginService loginService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RegistrationDocumentStorageService documentStorageService;

    @Override
    public void approve(int reviewId, String adminName) {
        RegistrationRequestedUser review = repository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("심사 신청이 없습니다."));

        if (review.getReviewStatus() != ReviewStatus.PENDING) {
            throw new RuntimeException("이미 처리된 사항입니다.");
        }

        User user = User.builder()
            .bId(review.getBId())
            .pw(review.getPwHash())
            .name(review.getName())
            .phone(review.getPhone())
            .email(review.getEmail())
            .storeName(review.getStoreName())
            .storeType(review.getStoreType())
            .storeCategory(review.getStoreCategory())
            .signDate(LocalDateTime.now())
            .agreementDate(LocalDateTime.now())
            .marketingDate(review.isMarketingAgree() ? LocalDateTime.now() : null)
            .build();

        userInfoDAO.register(user);
        review.approve(adminName);
    }


    @Override
    public RegistrationResponse register(RegisterRequest request, MultipartFile file) {
        String normalizedBid = validateNewRegistration(request);
        request.setBId(normalizedBid);

        createPendingReview(
            request,
            file,
            "외부 사업자등록번호 검증 연동 전 관리자 심사 대기"
        );

        return new RegistrationResponse(
            "PENDING",
            "회원가입 신청이 접수되었습니다. 관리자 심사중입니다."
        );
    }

    private String validateNewRegistration(RegisterRequest request) {
        if (request == null || request.getBId() == null || request.getBId().isBlank()) {
            throw new IllegalArgumentException("사업자등록번호는 필수입니다.");
        }
        if (request.getPw() == null || request.getPw().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }

        String normalizedBid = request.getBId().replaceAll("\\D", "");
        if (normalizedBid.length() != 10) {
            throw new IllegalArgumentException("사업자등록번호 형식이 올바르지 않습니다.");
        }

        if (userInfoDAO.getBidCheck(normalizedBid) > 0
            || repository.existsByBusinessId(normalizedBid)) {
            throw new IllegalArgumentException("이미 가입되었거나 심사 중인 사업자등록번호입니다.");
        }
        return normalizedBid;
    }

    private void createPendingReview(
        RegisterRequest request,
        MultipartFile file,
        String reason
    ) {
        String documentPath = documentStorageService.store(request.getBId(), file);

        repository.save(RegistrationRequestedUser.builder()
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
            .reason(reason)
            .reviewStatus(ReviewStatus.PENDING)
            .build());
    }
}
