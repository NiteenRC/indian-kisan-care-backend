package com.nc.med.auth.payload;

import com.nc.med.model.BankAccount;
import com.nc.med.model.User;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Set;

public class SignupRequest {
    private String username;
    private String email;
    private Set<String> role;
    private String password;
    private BankAccount bankAccount;
    private User user;

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
