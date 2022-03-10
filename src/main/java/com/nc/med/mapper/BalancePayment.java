package com.nc.med.mapper;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BalancePayment {
    private Long id;
    private Double payAmount;
    private double currentBalance;
    private OrderStatus status;
    private Date dueDate;
}
