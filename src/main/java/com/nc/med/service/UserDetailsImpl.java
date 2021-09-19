package com.nc.med.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nc.med.model.BankAccount;
import com.nc.med.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private final Long id;

	private final String username;

	private final String email;

	private final String gstNo;

	private String panNo;

	private String phoneNumber;

	private String brandName;

	private BankAccount bankAccount;

	@JsonIgnore
	private final String password;

	private final Collection<? extends GrantedAuthority> authorities;

	private byte[] image;

	public UserDetailsImpl(Long id, String username, String email, String password, String gstNo, String panNo,
			String phoneNumber, String brandName, BankAccount bankAccount,
			Collection<? extends GrantedAuthority> authorities, byte [] image) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.gstNo = gstNo;
		this.phoneNumber = phoneNumber;
		this.brandName = brandName;
		this.bankAccount = bankAccount;
		this.panNo = panNo;
		this.image = image;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
				user.getGstNo(), user.getPanNo(), user.getPhoneNumber(), user.getBrandName(), user.getBankAccount(),
				authorities, user.getImage());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getGstNo() {
		return gstNo;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte [] image) {
		this.image = image;
	}
}
