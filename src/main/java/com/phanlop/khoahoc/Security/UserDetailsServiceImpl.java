package com.phanlop.khoahoc.Security;

import com.phanlop.khoahoc.DAO.UserRepository;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Entity.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " is not found !");
        }
        return new CustomUserDetail(user);
    }

    @AllArgsConstructor
    static class CustomUserDetail implements UserDetails{
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
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return null;
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
}
