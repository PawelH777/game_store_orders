package org.example.ordergames.application.controller;

import org.example.ordergames.application.model.CreateOrderRequest;
import org.example.ordergames.application.model.OrderDTO;
import org.example.ordergames.application.model.UpdateStatusRequest;
import org.example.ordergames.domain.model.CartNotFoundException;
import org.example.ordergames.domain.model.OrderDO;
import org.example.ordergames.domain.model.OrderNotFoundException;
import org.example.ordergames.domain.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<Long> createOrder(@RequestBody final CreateOrderRequest createOrderRequest) {
        if (createOrderRequest.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "User ID needs to be provided");
        }

        if (createOrderRequest.getCartId() == null) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Cart ID needs to be provided");
        }

        final long orderId;
        try {
            orderId = orderService.createOrder(createOrderRequest.getUserId(), createOrderRequest.getCartId());
        } catch (final CartNotFoundException ex) {
            logger.error("Cart with cart id {} was not found in database, error stacktrace: ", ex.getCartId(), ex);
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Cart with id " + ex.getCartId() + " was not found in database");
        }

        return ResponseEntity.status(200)
                .body(orderId);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Object> updateStatus(@PathVariable final Long id, @RequestBody final UpdateStatusRequest updateStatusRequest) {
        if (updateStatusRequest.getOrderStatus() == null) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Order status needs to be provided");
        }

        try {
            orderService.updateStatus(id, updateStatusRequest.getOrderStatus());
        } catch (final OrderNotFoundException ex) {
            logger.error("Order with order id {} was not found in database, error stacktrace: ", ex.getOrderId(), ex);
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Order with id " + ex.getOrderId() + " was not found in database");
        }


        return ResponseEntity.status(200)
                .build();
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderDTO> findById(@PathVariable final Long id) {
        final OrderDO order;

        try {
            order = orderService.findById(id);
        } catch (final OrderNotFoundException ex) {
            logger.error("Order with order id {} was not found in database, error stacktrace: ", ex.getOrderId(), ex);
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Order with id " + ex.getOrderId() + " was not found in database");
        }

        return ResponseEntity.status(200)
                .body(buildOrderDTO(order));
    }

    private OrderDTO buildOrderDTO(final OrderDO order) {
        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .gamesIds(order.getGamesIds())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
