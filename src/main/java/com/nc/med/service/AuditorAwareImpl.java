package com.nc.med.service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = null;
        if (authentication != null) {
            currentPrincipalName = authentication.getName();
        } else {
            currentPrincipalName = "unknown";
        }
        return Optional.of(currentPrincipalName);
    }
}
