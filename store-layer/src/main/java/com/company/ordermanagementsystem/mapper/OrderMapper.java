package com.company.ordermanagementsystem.mapper;

import com.company.ordermanagementsystem.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    @Autowired
    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public Order mapToEntity(com.company.ordermanagementsystem.domain.model.Order orderModel) {
        Order orderEntity = new Order(
                orderModel.getCustomerId(),
                orderModel.getStatus(),
                orderModel.getCreatedAt(),
                orderModel.getTotalAmount()
        );
        orderModel.getItems().forEach(
                orderItem -> orderEntity.addItem(
                        orderItemMapper.mapToEntity(orderItem)
                )
        );
        return orderEntity;
    }

    public com.company.ordermanagementsystem.domain.model.Order mapToModel(Order orderEntity) {
        com.company.ordermanagementsystem.domain.model.Order orderModel = new com.company.ordermanagementsystem.domain.model.Order(
                orderEntity.getCustomerId(),
                orderEntity.getStatus(),
                orderEntity.getCreatedAt(),
                orderEntity.getItems().stream()
                        .map(orderItemMapper::mapToModel)
                        .toList(),
                orderEntity.getTotalAmount()
        );
        orderModel.setId(orderEntity.getId());
        return orderModel;

    }

}
