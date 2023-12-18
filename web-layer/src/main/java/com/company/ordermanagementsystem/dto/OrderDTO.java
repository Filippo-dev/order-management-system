package com.company.ordermanagementsystem.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDTO(UUID id,
                       UUID customerId,
                       String status,
                       LocalDateTime orderDateTime,
                       List<OrderItemDTO> items,
                       double totalAmount) {
}
