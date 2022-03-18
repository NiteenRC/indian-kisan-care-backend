package com.nc.med.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BankAccount {
    private static final long serialVersionUID = -100019078147252917L;
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
    private String email;
    @ManyToOne
    private Image image;
}
