package com.store.productservice.config;

import com.store.productservice.dto.ProductNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductNotificationProducer {

    private final KafkaTemplate<String, ProductNotificationDTO> kafkaTemplate;

    private static final String PRODUCT_TOPIC = "product-notif";

    public void sendProductSuggestion(ProductNotificationDTO dto) {
        kafkaTemplate.send(PRODUCT_TOPIC, dto);
    }

    public void sendProductAddedNotification(ProductNotificationDTO dto) {
        kafkaTemplate.send(PRODUCT_TOPIC, dto);
    }
}
