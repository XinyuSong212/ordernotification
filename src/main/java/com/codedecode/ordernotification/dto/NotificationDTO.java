// NotificationDTO.java
package com.codedecode.ordernotification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private String orderId;
    private String status;
    private String message;
    private String timestamp;
}