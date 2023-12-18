package com.company.ordermanagementsystem.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemDTO(@NotNull UUID productId,
                           int quantity,
                           double unitPrice) {
}
