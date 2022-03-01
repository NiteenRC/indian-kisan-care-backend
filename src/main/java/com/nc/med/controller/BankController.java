package com.nc.med.controller;

import com.nc.med.auth.payload.MessageResponse;
import com.nc.med.model.BankAccount;
import com.nc.med.model.User;
import com.nc.med.repo.BankAccountRepo;
import com.nc.med.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
@AllArgsConstructor
@CrossOrigin("*")
public class BankController {
    private final BankAccountRepo bankAccountRepo;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> registerBank(@RequestBody BankAccount bankAccount) {
        try {
            bankAccount.setId(1L);
            bankAccountRepo.save(bankAccount);
            userRepository.findAll().stream()
                    .filter(x -> x.getBankAccount() == null).forEach(user -> {
                User user1 = userRepository.getById(user.getId());
                user1.setBankAccount(bankAccount);
                userRepository.save(user1);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new MessageResponse("Bank details added successfully!"));
    }
}
