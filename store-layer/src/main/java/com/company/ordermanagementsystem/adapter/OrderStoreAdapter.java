package com.company.ordermanagementsystem.adapter;

import com.company.ordermanagementsystem.domain.model.Order;
import com.company.ordermanagementsystem.mapper.OrderMapper;
import com.company.ordermanagementsystem.port.out.OrderOutPort;
import com.company.ordermanagementsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderStoreAdapter implements OrderOutPort {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderStoreAdapter(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::mapToModel)
                .toList();
    }

    @Override
    public Optional<Order> getOrderById(UUID id) {
        return orderRepository.findById(id).map(orderMapper::mapToModel);
    }

    @Override
    public UUID createOrder(Order order) {
        return orderRepository.save(orderMapper.mapToEntity(order)).getId();
    }

    @Override
    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }
}
