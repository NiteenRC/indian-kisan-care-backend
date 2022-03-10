package com.nc.med.auth.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class SignupRequest {
    private String username;
    private Set<String> role;
    private String password;
}
