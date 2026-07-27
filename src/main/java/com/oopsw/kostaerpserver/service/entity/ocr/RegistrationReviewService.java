package com.oopsw.kostaerpserver.service.entity.ocr;

import com.oopsw.kostaerpserver.dto.manager.RegistrationDocument;
import com.oopsw.kostaerpserver.dto.manager.RegistrationReviewResponse;
import com.oopsw.kostaerpserver.repository.entity.admin.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegistrationReviewService {

    Page<RegistrationReviewResponse> getReviews(
        ReviewStatus status,
        Pageable pageable
    );

    RegistrationReviewResponse getReview(int reviewId);

    void approve(int reviewId, String adminName);

    void reject(int reviewId, String adminName, String reason);

    RegistrationDocument getDocument(int reviewId);
}
