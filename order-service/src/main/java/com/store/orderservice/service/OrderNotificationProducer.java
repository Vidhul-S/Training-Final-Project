package com.store.orderservice.service;

import com.store.orderservice.dto.OrderNotificationDTO;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderNotificationProducer {

    private final KafkaTemplate<String, OrderNotificationDTO> kafkaTemplate;

    private static final String ORDER_TOPIC = "order-notif";
    private static final String VENDOR_TOPIC = "vendor-notif";

    public void sendOrderNotification(OrderNotificationDTO dto) {
        kafkaTemplate.send(ORDER_TOPIC, dto);
    }

    public void sendVendorNotification(OrderNotificationDTO dto) {
        kafkaTemplate.send(VENDOR_TOPIC, dto);
    }
}
