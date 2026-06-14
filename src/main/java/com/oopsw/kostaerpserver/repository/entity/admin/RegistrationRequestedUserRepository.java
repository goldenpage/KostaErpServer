package com.oopsw.kostaerpserver.repository.entity.admin;


import jakarta.persistence.LockModeType;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRequestedUserRepository extends JpaRepository<RegistrationRequestedUser, Integer> {

    Page<RegistrationRequestedUser> findAllByReviewStatusOrderByRequestedAtAsc(
        ReviewStatus reviewStatus,
        Pageable pageable
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select review
        from RegistrationRequestedUser review
        where review.reviewId = :reviewId
        """)
    Optional<RegistrationRequestedUser> findByIdForUpdate(
        @Param("reviewId") int reviewId
    );

    @Query("""
        select count(review)
        from RegistrationRequestedUser review
        where review.bId = :bId
          and review.reviewStatus = :reviewStatus
        """)
    long countByBusinessIdAndReviewStatus(
        @Param("bId") String bId,
        @Param("reviewStatus") ReviewStatus reviewStatus
    );


}
