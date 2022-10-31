package org.example.ordergames.domain.service;

import org.example.ordergames.domain.model.*;
import org.example.ordergames.infrastructure.consumer.CartManagementClientAdapter;
import org.example.ordergames.infrastructure.repository.OrderRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartManagementClientAdapter cartManagementClientAdapter;

    private final OrderRepositoryAdapter orderRepositoryAdapter;

    public OrderServiceImpl(final CartManagementClientAdapter cartManagementClientAdapter, final OrderRepositoryAdapter orderRepositoryAdapter) {
        this.cartManagementClientAdapter = cartManagementClientAdapter;
        this.orderRepositoryAdapter = orderRepositoryAdapter;
    }

    @Override
    public long createOrder(final Long userId, final Long cartId) {
        final Optional<CartDO> cartOptional = cartManagementClientAdapter.findById(cartId);

        if (cartOptional.isEmpty()) {
            throw new CartNotFoundException(cartId);
        }

        final OrderDO order = buildOrder(userId, cartOptional.get());
        return orderRepositoryAdapter.save(order);
    }

    @Override
    public void updateStatus(final Long id, final OrderStatus orderStatus) {
        final Optional<OrderDO> order = orderRepositoryAdapter.findById(id);

        if (order.isEmpty()) {
            throw new OrderNotFoundException(id);
        }

        orderRepositoryAdapter.save(buildOrder(order.get(), orderStatus));
    }

    @Override
    public OrderDO findById(final Long id) {
        final Optional<OrderDO> order = orderRepositoryAdapter.findById(id);

        if (order.isEmpty()) {
            throw new OrderNotFoundException(id);
        }

        return order.get();
    }

    private OrderDO buildOrder(final Long userId, final CartDO cartDO) {
        return OrderDO.builder()
                .userId(userId)
                .gamesIds(cartDO.getGamesIds())
                .totalPrice(cartDO.getTotalPrice())
                .orderStatus(OrderStatus.CREATED)
                .build();
    }

    private OrderDO buildOrder(final OrderDO orderDO, final OrderStatus orderStatus) {
        return OrderDO.builder()
                .id(orderDO.getId())
                .userId(orderDO.getUserId())
                .gamesIds(orderDO.getGamesIds())
                .totalPrice(orderDO.getTotalPrice())
                .orderStatus(orderStatus)
                .build();
    }
}
