package com.oopsw.kostaerpserver.auth.repository;

import com.oopsw.kostaerpserver.auth.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
    boolean existsByUsername(String username);
}
