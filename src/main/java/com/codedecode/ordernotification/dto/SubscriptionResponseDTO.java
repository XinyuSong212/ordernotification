// SubscriptionResponseDTO.java
package com.codedecode.ordernotification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponseDTO {
    private String clientId;
    private String status;
    private String message;
}