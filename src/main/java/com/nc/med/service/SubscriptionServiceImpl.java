package com.nc.med.service;

import com.nc.med.model.BankAccount;
import com.nc.med.model.Subscription;
import com.nc.med.repo.BankAccountRepo;
import lombok.AllArgsConstructor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private static final String mpCryptoPassword = "nit";
    private final BankAccountRepo bankAccountRepo;

    @Override
    public void subscribe(Subscription subscription) {
        String decryptedAccessCode;
        try {
            decryptedAccessCode = decrypt(subscription.getAccessCode());
        } catch (RuntimeException e) {
            throw new RuntimeException("Wrong access code provided");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Optional<BankAccount> bankAccountOptional = bankAccountRepo.findById(1L);
        String brandNamePrefix = "";
        if (bankAccountOptional.isPresent()) {
            brandNamePrefix = bankAccountOptional.get().getBrandName().substring(0, 3).toLowerCase(Locale.ROOT);
        }
        String expectedAccessCode = brandNamePrefix.concat("_")
                .concat(subscription.getPeriod().name().concat("_")).toLowerCase(Locale.ROOT)
                .concat(dateFormat.format(new Date())).toLowerCase(Locale.ROOT);

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
    }

    public String decrypt(String encryptedPassword) {
        StandardPBEStringEncryptor decrypt = new StandardPBEStringEncryptor();
        decrypt.setPassword(mpCryptoPassword);
        return decrypt.decrypt(encryptedPassword);
    }
}
