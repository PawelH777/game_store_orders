package org.example.ordergames.infrastructure.repository;

import org.example.ordergames.domain.model.OrderDO;

import java.util.Optional;

public interface OrderRepositoryAdapter {

    long save(final OrderDO orderDO);

    Optional<OrderDO> findById(long id);
}
