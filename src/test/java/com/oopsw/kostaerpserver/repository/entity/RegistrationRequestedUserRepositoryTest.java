package com.oopsw.kostaerpserver.repository.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUser;
import com.oopsw.kostaerpserver.repository.entity.admin.RegistrationRequestedUserRepository;
import com.oopsw.kostaerpserver.repository.entity.admin.ReviewStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RegistrationRequestedUserRepositoryTest {

    @Autowired
    RegistrationRequestedUserRepository repository;

    @Test
    void saveAndFindTest() {
        RegistrationRequestedUser saved = repository.save(
            pendingReview("9999999911")
        );

        RegistrationRequestedUser found = repository
            .findById(saved.getReviewId())
            .orElseThrow();

        assertEquals("9999999911", found.getBId());
        assertEquals(ReviewStatus.PENDING, found.getReviewStatus());
    }

    @Test
    void findPendingReviewsTest() {
        repository.save(pendingReview("9999999912"));

        Page<RegistrationRequestedUser> result =
            repository.findAllByReviewStatusOrderByRequestedAtAsc(
                ReviewStatus.PENDING,
                PageRequest.of(0, 10)
            );

        assertTrue(
            result.getContent().stream()
                .anyMatch(review ->
                    review.getBId().equals("9999999912")
                )
        );
    }

    @Test
    void findByIdForUpdateTest() {
        RegistrationRequestedUser saved = repository.save(
            pendingReview("9999999913")
        );

        RegistrationRequestedUser found = repository
            .findByIdForUpdate(saved.getReviewId())
            .orElseThrow();

        assertEquals(saved.getReviewId(), found.getReviewId());
    }

    private RegistrationRequestedUser pendingReview(String bId) {
        return RegistrationRequestedUser.builder()
            .bId(bId)
            .pwHash("encoded-password")
            .documentPath("/tmp/document.pdf")
            .reviewStatus(ReviewStatus.PENDING)
            .build();
    }
}
