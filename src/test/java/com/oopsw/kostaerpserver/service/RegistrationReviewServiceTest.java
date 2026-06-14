package com.oopsw.kostaerpserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.oopsw.kostaerpserver.repository.dao.UserInfoDAO;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUser;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUserRepository;
import com.oopsw.kostaerpserver.repository.entity.admin.ReviewStatus;
import com.oopsw.kostaerpserver.service.entity.ocr.RegistrationReviewServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
public class RegistrationReviewServiceTest {

    @Autowired
    RegistrationReviewServiceImpl service;

    @Autowired
    RegistrationRequestedUserRepository reviewRepository;

    @Autowired
    UserInfoDAO userInfoDAO;

    @Test
    void approveCreatesUserAndChangesStatus() {
        RegistrationRequestedUser review = reviewRepository.save(
            pendingReview("9999999901")
        );

        service.approve(review.getReviewId(), "김관리자");

        RegistrationRequestedUser approved = reviewRepository
            .findById(review.getReviewId())
            .orElseThrow();

        assertEquals(ReviewStatus.APPROVED, approved.getReviewStatus());
        assertEquals("김관리자", approved.getReviewedBy());
        assertEquals(1, userInfoDAO.getBidCheck("9999999901"));
    }
    @Test
    void rejectChangesStatusAndReason() {
        RegistrationRequestedUser review = reviewRepository.save(
            pendingReview("9999999902")
        );

        service.reject(
            review.getReviewId(),
            "김관리자",
            "서류가 불명확합니다."
        );

        RegistrationRequestedUser rejected = reviewRepository
            .findById(review.getReviewId())
            .orElseThrow();

        assertEquals(ReviewStatus.REJECTED, rejected.getReviewStatus());
        assertEquals("김관리자", rejected.getReviewedBy());
        assertEquals("서류가 불명확합니다.", rejected.getReason());
        assertNotNull(rejected.getReviewDate());
    }

    private RegistrationRequestedUser pendingReview(String bId) {
        return RegistrationRequestedUser.builder()
            .bId(bId)
            .pwHash("encoded-password")
            .name("김사장")
            .phone("01012345678")
            .email("test@example.com")
            .storeName("김밥천국")
            .storeType("일반음식점")
            .storeCategory("한식")
            .documentPath("/tmp/document.pdf")
            .reviewStatus(ReviewStatus.PENDING)
            .build();
    }

}
