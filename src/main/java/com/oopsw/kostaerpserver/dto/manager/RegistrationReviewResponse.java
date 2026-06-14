package com.oopsw.kostaerpserver.dto.manager;

import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUser;
import com.oopsw.kostaerpserver.repository.entity.admin.ReviewStatus;
import java.time.LocalDateTime;

public record RegistrationReviewResponse(
    int reviewId,
    String businessId,
    String representativeName,
    String storeName,
    String email,
    String phone,
    String storeType,
    String storeCategory,
    String reason,
    ReviewStatus status,
    LocalDateTime requestedAt,
    String reviewedBy,
    LocalDateTime reviewDate
) {
    public static RegistrationReviewResponse from(
        RegistrationRequestedUser entity
    ) {
        return new RegistrationReviewResponse(
            entity.getReviewId(),
            entity.getBId(),
            entity.getName(),
            entity.getStoreName(),
            entity.getEmail(),
            entity.getPhone(),
            entity.getStoreType(),
            entity.getStoreCategory(),
            entity.getReason(),
            entity.getReviewStatus(),
            entity.getRequestedAt(),
            entity.getReviewedBy(),
            entity.getReviewDate()
        );
    }
}