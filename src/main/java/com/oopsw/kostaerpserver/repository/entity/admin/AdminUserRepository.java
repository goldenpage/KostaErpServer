package com.oopsw.kostaerpserver.repository.entity.admin;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser,Integer > {

    Optional<AdminUser> findByAdminName(String adminName);
}
