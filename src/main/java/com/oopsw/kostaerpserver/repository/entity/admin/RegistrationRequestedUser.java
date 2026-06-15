package com.oopsw.kostaerpserver.repository.entity.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "registration_requested_user",
    indexes = {
        @Index(
            name = "idx_registration_review_status_requested_at",
            columnList = "review_status, requested_at"
        ),
        @Index(
            name = "idx_registration_review_b_id",
            columnList = "b_id"
        )
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RegistrationRequestedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    @Column(name = "b_id", nullable = false, length = 10)
    private String bId;

    @Column(name = "pw_hash", nullable = false)
    private String pwHash;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_type")
    private String storeType;

    @Column(name = "store_category")
    private String storeCategory;

    @Column(name = "marketing_agree", nullable = false)
    private boolean marketingAgree;

    @Column(name = "document_path", nullable = false)
    private String documentPath;

    @Column(name = "ocr_confidence")
    private String ocrConfidence;

    @Column(name = "reason", length = 1000)
    private String reason;

    @Column(name = "reviewed_by")
    private String reviewedBy;

    @Builder.Default
    @Column(name = "requested_at", nullable = false, updatable = false)
    private LocalDateTime requestedAt = LocalDateTime.now();

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status", nullable = false)
    private ReviewStatus reviewStatus;

    public void approve(String adminName) {
        validatePending();

        reviewStatus = ReviewStatus.APPROVED;
        reviewedBy = adminName;
        reviewDate = LocalDateTime.now();
    }

    public void reject(String adminName, String rejectionReason) {
        validatePending();

        reviewStatus = ReviewStatus.REJECTED;
        reviewedBy = adminName;
        reason = rejectionReason;
        reviewDate = LocalDateTime.now();
    }

    private void validatePending() {
        if (reviewStatus != ReviewStatus.PENDING) {
            throw new IllegalStateException("심사 대기 중인 신청만 처리할 수 있습니다.");
        }
    }
}