package com.oopsw.kostaerpserver.repository.entity.admin;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RegistrationRequestedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @Column(nullable = false, unique = true)
    private String bId;

    @Column(nullable = false)
    private String pwHash;

    private String name;
    private String email;
    private String phone;
    private String storeName;
    private String storeType;
    private String storeCategory;
    private boolean marketingAgree;

    @Column(nullable = false)
    private String documentPath;

    private String ocrConfidence;
    @Column(length = 1000)
    private String reason;
    private String reviewedBy;
    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime requestedAt = LocalDateTime.now();
    private LocalDateTime reviewDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus reviewStatus;

    public void approve(String adminName) {
        reviewStatus = ReviewStatus.APPROVED;
        reviewedBy = adminName;
        reviewDate = LocalDateTime.now();
    }
}
