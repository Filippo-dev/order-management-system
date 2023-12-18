package com.company.ordermanagementsystem.mapper;

import com.company.ordermanagementsystem.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public com.company.ordermanagementsystem.domain.model.OrderItem mapToModel(OrderItem orderItem) {
        return new com.company.ordermanagementsystem.domain.model.OrderItem(orderItem.getId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice());
    }

    public OrderItem mapToEntity(com.company.ordermanagementsystem.domain.model.OrderItem orderItem) {
        return new OrderItem(orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice());
    }

}
