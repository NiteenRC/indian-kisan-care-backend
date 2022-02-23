package com.nc.med.repo;

import com.nc.med.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {
}
