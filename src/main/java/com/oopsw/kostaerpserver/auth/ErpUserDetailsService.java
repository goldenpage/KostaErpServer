package com.oopsw.kostaerpserver.auth;

import com.oopsw.kostaerpserver.repository.LoginSecurityDAO;
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

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
            LoginUser loginUser = loginSecurityDAO.findByBId(username);
            if (loginUser == null) {
                throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
            }
            return new ErpUserDetails(loginUser);
        }
    }


