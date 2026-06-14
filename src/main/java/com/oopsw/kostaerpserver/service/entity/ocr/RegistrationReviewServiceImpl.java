package com.oopsw.kostaerpserver.service.entity.ocr;

import com.oopsw.kostaerpserver.dto.manager.RegistrationDocument;
import com.oopsw.kostaerpserver.dto.manager.RegistrationReviewResponse;
import com.oopsw.kostaerpserver.repository.dao.UserInfoDAO;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUser;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUserRepository;
import com.oopsw.kostaerpserver.repository.entity.admin.ReviewStatus;
import com.oopsw.kostaerpserver.vo.User;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationReviewServiceImpl implements
    RegistrationReviewService {

    private final RegistrationRequestedUserRepository reviewRepository;
    private final UserInfoDAO userInfoDAO;
    private final RegistrationDocumentStorageService documentStorageService;


    @Override
    @Transactional(readOnly = true)
    public Page<RegistrationReviewResponse> getReviews(ReviewStatus status,
        Pageable pageable) {
        return reviewRepository
            .findAllByReviewStatusOrderByRequestedAtAsc(status, pageable)
            .map(RegistrationReviewResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public RegistrationReviewResponse getReview(int reviewId) {
        return reviewRepository.findById(reviewId)
            .map(RegistrationReviewResponse::from)
            .orElseThrow(() ->
                new IllegalArgumentException("가입 신청을 찾을 수 없습니다.")
            );
    }

    @Override
    @Transactional
    public void approve(int reviewId, String adminName) {
        RegistrationRequestedUser review = getPendingReview(reviewId);

        if (userInfoDAO.getBidCheck(review.getBId()) > 0) {
            throw new IllegalStateException("이미 가입된 사업자등록번호입니다.");
        }

        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
            .bId(review.getBId())
            .pw(review.getPwHash()) // 다시 BCrypt 암호화 금지
            .name(review.getName())
            .phone(review.getPhone())
            .email(review.getEmail())
            .storeName(review.getStoreName())
            .storeType(review.getStoreType())
            .storeCategory(review.getStoreCategory())
            .signDate(now)
            .agreementDate(now)
            .marketingDate(review.isMarketingAgree() ? now : null)
            .build();

        if (userInfoDAO.register(user) != 1) {
            throw new IllegalStateException("사용자 계정 생성에 실패했습니다.");
        }

        review.approve(adminName);
    }

    @Override
    @Transactional
    public void reject(int reviewId, String adminName, String reason) {
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("반려 사유는 필수입니다.");
        }

        RegistrationRequestedUser review = getPendingReview(reviewId);
        review.reject(adminName, reason.trim());
    }

    @Override
    public RegistrationDocument getDocument(int reviewId) {
        RegistrationRequestedUser review = reviewRepository.findById(reviewId)
            .orElseThrow(() ->
                new IllegalArgumentException("가입 신청을 찾을 수 없습니다.")
            );

        Resource resource = documentStorageService.loadAsResource(
            review.getDocumentPath()
        );

        MediaType mediaType = resolveMediaType(resource);

        return new RegistrationDocument(resource, mediaType);
    }

    private MediaType resolveMediaType(Resource resource) {
        try {
            String contentType = Files.probeContentType(
                resource.getFile().toPath()
            );

            if (contentType != null) {
                return MediaType.parseMediaType(contentType);
            }
        } catch (IOException ignored) {
            // 알 수 없는 파일 형식은 다운로드 가능한 바이너리로 응답한다.
        }

        return MediaType.APPLICATION_OCTET_STREAM;
    }

    private RegistrationRequestedUser getPendingReview(int reviewId) {
        RegistrationRequestedUser review =
            reviewRepository.findByIdForUpdate(reviewId)
                .orElseThrow(() ->
                    new IllegalArgumentException("가입 신청을 찾을 수 없습니다.")
                );

        if (review.getReviewStatus() != ReviewStatus.PENDING) {
            throw new IllegalStateException("이미 처리된 가입 신청입니다.");
        }

        return review;
    }
}
