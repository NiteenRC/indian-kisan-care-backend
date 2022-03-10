package com.nc.med.auth.payload;

import com.nc.med.model.BankAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private final List<String> roles;
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    @OneToOne
    private BankAccount bankAccount;

    public JwtResponse(String accessToken, Long id, String username,
                       BankAccount bankAccount, List<String> roles, byte[] image) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.bankAccount = bankAccount;
        this.roles = roles;
    }
}
