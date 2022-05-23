package com.nc.med.controller;

import com.nc.med.auth.payload.MessageResponse;
import com.nc.med.model.Subscription;
import com.nc.med.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<?> registerBank(@RequestBody Subscription subscription) {
        try {
            subscriptionService.subscribe(subscription);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new MessageResponse("Subscribed successfully!"));
    }
}
