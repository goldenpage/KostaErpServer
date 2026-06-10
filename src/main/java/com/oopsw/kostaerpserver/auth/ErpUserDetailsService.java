package com.oopsw.kostaerpserver.auth;

import com.oopsw.kostaerpserver.repository.dao.LoginSecurityDAO;
import com.oopsw.kostaerpserver.repository.entity.admin.AdminUser;
import com.oopsw.kostaerpserver.repository.entity.admin.AdminUserRepository;
import com.oopsw.kostaerpserver.vo.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ErpUserDetailsService implements UserDetailsService {
    private final LoginSecurityDAO loginSecurityDAO;
    private final AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
            LoginUser loginUser = loginSecurityDAO.findByBId(username);
            if (loginUser != null) {
                return new ErpUserDetails(loginUser);
            }

        AdminUser adminUser =
            adminUserRepository.findByAdminName(username).orElseThrow(() ->
                new UsernameNotFoundException("존재하지 않는 사용자입니다.")
            );
            return new ErpAdminUserDetails(adminUser);
        }
    }


