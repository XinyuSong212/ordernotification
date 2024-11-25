package com.codedecode.ordernotification.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderUpdateListener implements MessageListener {

    private final String clientId;

    public OrderUpdateListener(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String updateMessage = new String(message.getBody());
        log.info("Client {} received update: {}", clientId, updateMessage);
        // Here you can implement the logic to notify the client
        // e.g., through WebSocket, Server-Sent Events, or other mechanisms
    }
}