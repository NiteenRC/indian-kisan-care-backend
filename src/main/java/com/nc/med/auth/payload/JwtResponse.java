package com.nc.med.auth.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private static final long serialVersionUID = -100019048147252917L;
    private final List<String> roles;
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;

    public JwtResponse(String accessToken, Long id, String username, List<String> roles, byte[] image) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
