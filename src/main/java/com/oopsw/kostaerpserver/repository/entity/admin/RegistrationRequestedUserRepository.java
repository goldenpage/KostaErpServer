package com.oopsw.kostaerpserver.repository.entity.admin;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRequestedUserRepository extends JpaRepository<RegistrationRequestedUser, Integer> {

    @Query("""
        select (count(review) > 0)
        from RegistrationRequestedUser review
        where review.bId = :bId
        """)
    boolean existsByBusinessId(@Param("bId") String bId);
}
