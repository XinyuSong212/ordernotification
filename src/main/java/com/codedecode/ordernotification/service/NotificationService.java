package com.codedecode.ordernotification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import com.codedecode.ordernotification.listener.OrderUpdateListener;
import com.codedecode.ordernotification.dto.NotificationDTO;
import com.codedecode.ordernotification.dto.OrderUpdateNotificationDTO;
import com.codedecode.ordernotification.dto.SubscriptionResponseDTO;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {

    private static final String ORDER_UPDATE_CHANNEL = "order_updates";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisMessageListenerContainer listenerContainer;

    private final Map<String, OrderUpdateListener> clientListeners = new ConcurrentHashMap<>();

    public SubscriptionResponseDTO subscribeClient(String clientId) {
        OrderUpdateListener listener = new OrderUpdateListener(clientId);
        clientListeners.put(clientId, listener);

        listenerContainer.addMessageListener(listener, new ChannelTopic(ORDER_UPDATE_CHANNEL));

        return new SubscriptionResponseDTO(
                clientId,
                "SUBSCRIBED",
                "Client " + clientId + " subscribed successfully"
        );
    }

    public SubscriptionResponseDTO unsubscribeClient(String clientId) {
        OrderUpdateListener listener = clientListeners.remove(clientId);
        if (listener != null) {
            listenerContainer.removeMessageListener(listener);
            return new SubscriptionResponseDTO(
                    clientId,
                    "UNSUBSCRIBED",
                    "Client " + clientId + " unsubscribed successfully"
            );
        }
        return new SubscriptionResponseDTO(
                clientId,
                "NOT_FOUND",
                "Client " + clientId + " not found"
        );
    }

    public NotificationDTO publishOrderUpdate(OrderUpdateNotificationDTO updateDTO) {
        NotificationDTO notification = new NotificationDTO(
                updateDTO.getOrderId(),
                updateDTO.getStatus(),
                String.format("Order %s status updated to %s",
                        updateDTO.getOrderId(),
                        updateDTO.getStatus()),
                LocalDateTime.now().toString()
        );

        String message = String.format("%s|status=%s",
                updateDTO.getOrderId(),
                updateDTO.getStatus());
        redisTemplate.convertAndSend(ORDER_UPDATE_CHANNEL, message);

        return notification;
    }
}