package com.nc.med.controller;

import com.nc.med.auth.payload.MessageResponse;
import com.nc.med.model.Subscription;
import com.nc.med.repo.SubscriptionRepo;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private static final String mpCryptoPassword = "raj";
    private final SubscriptionRepo subscriptionRepo;

    @PostMapping
    public ResponseEntity<?> registerBank(@RequestBody Subscription subscription) {
        try {
            String decryptedAccessCode = null;
            try {
                decryptedAccessCode = decrypt(subscription.getAccessCode());
            } catch (RuntimeException e) {
                throw new RuntimeException("Wrong access code provided");
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
            String expectedAccessCode = "NSR-".concat(dateFormat.format(new Date()));

            if (!Objects.equals(decryptedAccessCode, expectedAccessCode)) {
                throw new RuntimeException("Access code is not valid");
            }

            subscription.setId(1L);
            Calendar date = Calendar.getInstance();
            date.setTime(new Date());
            int subscriptionPeriod = Integer.parseInt(subscription.getPeriod().name().split("_")[1]);

            if (subscription.getPeriod().name().startsWith("M")) {
                date.add(Calendar.MONTH, subscriptionPeriod);
            } else {
                date.add(Calendar.YEAR, subscriptionPeriod);
            }
            Date newDate = date.getTime();
            subscription.setEndAt(newDate);
            subscription.setCreatedAt(new Date());
            subscriptionRepo.save(subscription);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new MessageResponse("Subscribed successfully!"));
    }

    public String decrypt(String encryptedPassword) {
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(mpCryptoPassword);
        return decryptor.decrypt(encryptedPassword);
    }
}
