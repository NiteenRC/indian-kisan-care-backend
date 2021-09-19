package com.nc.med.auth.payload;

import com.nc.med.model.BankAccount;

import javax.persistence.OneToOne;
import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private final List<String> roles;
    private String brandName;
    private String gstNo;
    private String panNo;
    private String phoneNumber;
    @OneToOne
    private BankAccount bankAccount;

    public JwtResponse(String accessToken, Long id, String username, String email, String gstNo, String panNo,
                       String phoneNumber, String brandName, BankAccount bankAccount, List<String> roles, byte[] image) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.gstNo = gstNo;
        this.panNo = panNo;
        this.phoneNumber = phoneNumber;
        this.brandName = brandName;
        this.bankAccount = bankAccount;
        this.roles = roles;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
