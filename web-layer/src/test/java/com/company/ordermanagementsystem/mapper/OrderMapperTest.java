package com.company.ordermanagementsystem.mapper;

import com.company.ordermanagementsystem.controller.objectmother.CreateOrderRequestObjectMother;
import com.company.ordermanagementsystem.controller.objectmother.OrderItemDTOObjectMother;
import com.company.ordermanagementsystem.domain.model.Order;
import com.company.ordermanagementsystem.domain.model.OrderItem;
import com.company.ordermanagementsystem.domain.model.OrderStatus;
import com.company.ordermanagementsystem.domain.service.objectmother.OrderItemObjectMother;
import com.company.ordermanagementsystem.domain.service.objectmother.OrderObjectMother;
import com.company.ordermanagementsystem.dto.CreateOrderRequest;
import com.company.ordermanagementsystem.dto.OrderDTO;
import com.company.ordermanagementsystem.dto.OrderItemDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class OrderMapperTest {

    @InjectMocks
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setup() {
        autoCloseable = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void itShouldMapARequestToAModel() {
        CreateOrderRequest createOrderRequest = CreateOrderRequestObjectMother.aRandomCreateOrderRequest();
        OrderItem orderItem = OrderItemObjectMother.aRandomOrderItem();
        when(orderItemMapper.toOrderItem(any(OrderItemDTO.class))).thenReturn(orderItem);
        UUID expectedCustomerId = createOrderRequest.customerId();
        OrderStatus expectedStatus = OrderStatus.PENDING;
        BigDecimal expectedTotalAmount = BigDecimal.ZERO;
        for (OrderItemDTO orderItemDTO : createOrderRequest.items()) {
            expectedTotalAmount = expectedTotalAmount.add(orderItemDTO.unitPrice().multiply(BigDecimal.valueOf(orderItemDTO.quantity())));
        }

        Order order = orderMapper.toOrder(createOrderRequest);
        UUID actualCustomerId = order.getCustomerId();
        OrderStatus actualStatus = order.getStatus();
        BigDecimal actualTotalAmount = order.getTotalAmount();

        assertEquals(expectedCustomerId, actualCustomerId);
        assertEquals(expectedStatus, actualStatus);
        assertTrue(order.getCreatedAt().isBefore(LocalDateTime.now()));
        assertTrue(order.getCreatedAt().isAfter(LocalDateTime.now().minusSeconds(1)));
        assertTrue(order.getItems().contains(orderItem));
        assertEquals(expectedTotalAmount, actualTotalAmount);
    }

    @Test
    void itShouldMapAModelToADTO() {
        Order order = OrderObjectMother.aRandomOrder();
        OrderItem orderItem = OrderItemObjectMother.aRandomOrderItem();
        order.setItems(Collections.singletonList(orderItem));
        OrderItemDTO orderItemDTO = OrderItemDTOObjectMother.aRandomOrderItemDTO();
        when(orderItemMapper.toOrderItemDTO(any(OrderItem.class))).thenReturn(orderItemDTO);
        UUID expectedId = order.getId();
        UUID expectedCustomerId = order.getCustomerId();
        String expectedStatus = order.getStatus().name();
        LocalDateTime expectedCreatedAt = order.getCreatedAt();
        double expectedTotalAmount = order.getTotalAmount().doubleValue();

        OrderDTO orderDTO = orderMapper.toOrderDTO(order);
        UUID actualId = orderDTO.id();
        UUID actualCustomerId = orderDTO.customerId();
        String actualStatus = orderDTO.status();
        LocalDateTime actualCreatedAt = orderDTO.createdAt();
        double actualTotalAmount = orderDTO.totalAmount();

        assertEquals(expectedId, actualId);
        assertEquals(expectedCustomerId, actualCustomerId);
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedCreatedAt, actualCreatedAt);
        assertEquals(expectedTotalAmount, actualTotalAmount);
        assertTrue(orderDTO.items().contains(orderItemDTO));
    }
}