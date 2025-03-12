package com.store.notificationservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GatewayNotificationDTO {
    private String userId;
    private String message;
}
