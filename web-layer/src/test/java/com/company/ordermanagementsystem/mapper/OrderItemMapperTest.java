package com.company.ordermanagementsystem.mapper;

import com.company.ordermanagementsystem.controller.objectmother.OrderItemDTOObjectMother;
import com.company.ordermanagementsystem.domain.model.OrderItem;
import com.company.ordermanagementsystem.domain.service.objectmother.OrderItemObjectMother;
import com.company.ordermanagementsystem.dto.OrderItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderItemMapperTest {

    private OrderItemMapper orderItemMapper;

    @BeforeEach
    public void setup() {
        orderItemMapper = new OrderItemMapper();
    }

    @Test
    void itShouldMapToModel() {
        OrderItemDTO orderItemDTO = OrderItemDTOObjectMother.aRandomOrderItemDTO();
        UUID expectedProductId = orderItemDTO.productId();
        int expectedQuantity = orderItemDTO.quantity();
        BigDecimal expectedUnitPrice = orderItemDTO.unitPrice();

        OrderItem orderItem = orderItemMapper.toOrderItem(orderItemDTO);

        UUID actualProductId = orderItem.getProductId();
        int actualQuantity = orderItem.getQuantity();
        BigDecimal actualUnitPrice = orderItem.getUnitPrice();

        assertEquals(expectedProductId, actualProductId);
        assertEquals(expectedQuantity, actualQuantity);
        assertEquals(expectedUnitPrice, actualUnitPrice);
    }

    @Test
    void itShouldMapToDTO() {
        OrderItem orderItem = OrderItemObjectMother.aRandomOrderItem();
        UUID expectedProductId = orderItem.getProductId();
        int expectedQuantity = orderItem.getQuantity();
        BigDecimal expectedUnitPrice = orderItem.getUnitPrice();

        OrderItemDTO orderItemDTO = orderItemMapper.toOrderItemDTO(orderItem);

        UUID actualProductId = orderItemDTO.productId();
        int actualQuantity = orderItemDTO.quantity();
        BigDecimal actualUnitPrice = orderItemDTO.unitPrice();

        assertEquals(expectedProductId, actualProductId);
        assertEquals(expectedQuantity, actualQuantity);
        assertEquals(expectedUnitPrice, actualUnitPrice);
    }
}