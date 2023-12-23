package com.company.ordermanagementsystem.controller;

import com.company.ordermanagementsystem.controller.objectmother.CreateOrderRequestObjectMother;
import com.company.ordermanagementsystem.controller.objectmother.OrderDTOObjectMother;
import com.company.ordermanagementsystem.domain.model.Order;
import com.company.ordermanagementsystem.domain.service.objectmother.OrderObjectMother;
import com.company.ordermanagementsystem.dto.CreateOrderRequest;
import com.company.ordermanagementsystem.dto.OrderDTO;
import com.company.ordermanagementsystem.exception.OrderNotFoundException;
import com.company.ordermanagementsystem.mapper.OrderMapper;
import com.company.ordermanagementsystem.port.in.OrderApiInPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class OrderControllerTest {

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderApiInPort orderApiInPort;

    @InjectMocks
    private OrderController orderController;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void itShouldGetAllOrders() {
        OrderDTO orderDTO = OrderDTOObjectMother.aRandomOrderDTO();
        Order order = OrderObjectMother.aRandomOrder();
        when(orderApiInPort.getAllOrders()).thenReturn(Collections.singletonList(order));
        when(orderMapper.toOrderDTO(order)).thenReturn(orderDTO);

        ResponseEntity<List<OrderDTO>> response = orderController.getAllOrders();

        List<OrderDTO> expectedBody = Collections.singletonList(orderDTO);
        List<OrderDTO> actualBody = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void itShouldGetAnOrder() throws OrderNotFoundException {
        UUID id = UUID.randomUUID();
        OrderDTO expectedOrderDTO = OrderDTOObjectMother.aRandomOrderDTO();
        Order order = OrderObjectMother.aRandomOrder();
        when(orderApiInPort.getOrderById(id)).thenReturn(Optional.of(order));
        when(orderMapper.toOrderDTO(order)).thenReturn(expectedOrderDTO);

        ResponseEntity<OrderDTO> response = orderController.getOrder(id);

        OrderDTO actualOrderDTO = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedOrderDTO, actualOrderDTO);
    }

    @Test
    void itShouldThrowAnOrderNotFound() {
        UUID id = UUID.randomUUID();
        when(orderApiInPort.getOrderById(id)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderController.getOrder(id));
    }

    @Test
    public void itShouldCreateAnOrder() {
        CreateOrderRequest createOrderRequest = CreateOrderRequestObjectMother.aRandomCreateOrderRequest();
        UUID expectedBody = UUID.randomUUID();
        Order order = OrderObjectMother.aRandomOrder();
        when(orderMapper.toOrder(createOrderRequest)).thenReturn(order);
        when(orderApiInPort.createOrder(order)).thenReturn(expectedBody);

        ResponseEntity<UUID> response = orderController.createOrder(createOrderRequest);

        UUID actualBody = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedBody, actualBody);
    }

    @Test
    public void itShouldDeleteAnOrder() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> response = orderController.deleteOrder(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(orderApiInPort, times(1)).deleteOrder(id);
    }
}