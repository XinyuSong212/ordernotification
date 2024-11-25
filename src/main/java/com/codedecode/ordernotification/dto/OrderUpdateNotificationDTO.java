// OrderUpdateNotificationDTO.java
package com.codedecode.ordernotification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateNotificationDTO {
    private String orderId;
    private String status;
}
