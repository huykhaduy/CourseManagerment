package com.phanlop.khoahoc.Security;

import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Enums.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getUserRole() == UserRole.STUDENT){
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }
        if (user.getUserRole() == UserRole.TEACHER){
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_TEACHER"));
        }
        if (user.getUserRole() == UserRole.ADMIN){
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return Collections.singleton(null);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
