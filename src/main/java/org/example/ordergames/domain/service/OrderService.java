package org.example.ordergames.domain.service;

import org.example.ordergames.domain.model.OrderDO;
import org.example.ordergames.domain.model.OrderStatus;

public interface OrderService {
    long createOrder(Long userId, Long cartId);

    void updateStatus(Long id, OrderStatus orderStatus);

    OrderDO findById(Long id);
}
