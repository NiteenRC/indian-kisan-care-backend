package com.nc.med.service;

import com.nc.med.model.Subscription;
import com.nc.med.model.User;
import com.nc.med.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private static final String mpCryptoPassword = "raj";
    private final UserRepository userRepository;

    @Override
    public void subscribe(Subscription subscription) {
        String decryptedAccessCode;
        try {
            decryptedAccessCode = decrypt(subscription.getAccessCode());
        } catch (RuntimeException e) {
            throw new RuntimeException("Wrong access code provided");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        Optional<User> userOptional = userRepository.findById(1L);
        String usernamePrefix = "";
        if (userOptional.isPresent()) {
            usernamePrefix = userOptional.get().getUsername().substring(0, 3).toLowerCase(Locale.ROOT);
        }
        String expectedAccessCode = usernamePrefix.concat("_")
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
