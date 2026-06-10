package com.oopsw.kostaerpserver.repository.entity;

import com.oopsw.kostaerpserver.repository.entity.admin.AdminUser;
import com.oopsw.kostaerpserver.repository.entity.admin.AdminUserRepository;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class AdminUserDAOTest {

    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void registerAdminTest() {
        AdminUser adminUser = AdminUser.builder()
            .adminName("김관리")
            .pw(bCryptPasswordEncoder.encode("admin123"))
            .role("ROLE_MANAGER")
            .createdAt(LocalDate.parse("2026-06-09"))
            .build();

        AdminUser saved = adminUserRepository.save(adminUser);
        log.info("saved adminId = {}", saved.getAdminId());
    }

    @Test
    void findByAdminNameTest() {
        log.info(String.valueOf(adminUserRepository.findByAdminName("김관리")));
        Assertions.assertNotNull(adminUserRepository.findByAdminName("김관리"));
    }
}