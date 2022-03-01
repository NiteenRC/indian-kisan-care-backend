package com.nc.med.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue
    private Long id;
    private String bankName;
    private String accountNo;
    private String ifscCode;
    private String branchName;
    private String brandName;
    private String gstNo;
    private String panNo;
    private String phoneNumber;
}
