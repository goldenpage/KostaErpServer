package com.oopsw.kostaerpserver.auth;

import com.oopsw.kostaerpserver.repository.entity.admin.AdminUser;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class ErpAdminUserDetails  implements UserDetails {
    private final AdminUser adminUser;

    public ErpAdminUserDetails(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(adminUser.getRole()));

    }

    @Override
    public String getPassword() {
        return adminUser.getPw();
    }

    @Override

    public String getUsername() {
        return adminUser.getAdminName();
    }

    @Override

    public boolean isAccountNonExpired() {
        return true;
    }

    @Override

    public boolean isAccountNonLocked() {
        return true;
    }

    @Override

    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override

    public boolean isEnabled() {
        return true;
    }
}
