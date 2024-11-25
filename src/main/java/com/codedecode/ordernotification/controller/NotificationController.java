package com.codedecode.ordernotification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codedecode.ordernotification.service.NotificationService;
import com.codedecode.ordernotification.dto.NotificationDTO;
import com.codedecode.ordernotification.dto.OrderUpdateNotificationDTO;
import com.codedecode.ordernotification.dto.SubscriptionResponseDTO;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/subscribe/{clientId}")
    public ResponseEntity<SubscriptionResponseDTO> subscribeToUpdates(@PathVariable String clientId) {
        SubscriptionResponseDTO response = notificationService.subscribeClient(clientId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/unsubscribe/{clientId}")
    public ResponseEntity<SubscriptionResponseDTO> unsubscribeFromUpdates(@PathVariable String clientId) {
        SubscriptionResponseDTO response = notificationService.unsubscribeClient(clientId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/publish")
    public ResponseEntity<NotificationDTO> publishUpdate(@RequestBody OrderUpdateNotificationDTO updateDTO) {
        NotificationDTO notification = notificationService.publishOrderUpdate(updateDTO);
        return ResponseEntity.ok(notification);
    }
}