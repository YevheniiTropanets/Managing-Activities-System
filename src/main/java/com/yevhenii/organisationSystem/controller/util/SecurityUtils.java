package com.yevhenii.organisationSystem.controller.util;

import com.yevhenii.organisationSystem.security.userdetails.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtils {
    public long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }

    public boolean checkAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<? extends GrantedAuthority> maybeAdminRole = ((UserDetailsImpl) authentication.getPrincipal()).getAuthorities().stream()
                .filter(a -> a.getAuthority().equals("ROLE_ADMIN"))
                .findFirst();

        return maybeAdminRole.isPresent();
    }

}
