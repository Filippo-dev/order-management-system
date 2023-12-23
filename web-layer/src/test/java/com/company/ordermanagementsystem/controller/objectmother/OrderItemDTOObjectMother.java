package com.company.ordermanagementsystem.controller.objectmother;

import com.company.ordermanagementsystem.dto.OrderItemDTO;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.UUID;

public class OrderItemDTOObjectMother {

    public static OrderItemDTO aRandomOrderItemDTO() {
        SecureRandom secureRandom = new SecureRandom();
        return new OrderItemDTO(
                UUID.randomUUID(),
                secureRandom.nextInt(5),
                BigDecimal.valueOf(secureRandom.nextFloat(100))
        );
    }
}
