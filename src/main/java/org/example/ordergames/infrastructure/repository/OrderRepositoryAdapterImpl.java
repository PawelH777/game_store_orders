package org.example.ordergames.infrastructure.repository;

import org.example.ordergames.domain.model.OrderDO;
import org.example.ordergames.infrastructure.config.MongodbSequenceGenerator;
import org.example.ordergames.infrastructure.model.Order;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepositoryAdapterImpl implements OrderRepositoryAdapter {

    private final OrderRepository orderRepository;

    private final MongodbSequenceGenerator sequenceGenerator;

    public OrderRepositoryAdapterImpl(final OrderRepository orderRepository, final MongodbSequenceGenerator sequenceGenerator) {
        this.orderRepository = orderRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public long save(final OrderDO orderDO) {
        final Order order = orderRepository.save(buildOrder(orderDO));
        return order.getId();
    }

    @Override
    public Optional<OrderDO> findById(final long id) {
        final Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.map(this::buildOrderDO);
    }

    private Order buildOrder(final OrderDO orderDO) {
        final long id = orderDO.getId() != 0 ? orderDO.getId() : sequenceGenerator.generateSequence(Order.SEQUENCE_NAME);
        return Order.builder()
                .id(id)
                .userId(orderDO.getUserId())
                .gamesIds(orderDO.getGamesIds())
                .totalPrice(orderDO.getTotalPrice())
                .orderStatus(orderDO.getOrderStatus())
                .build();
    }

    private OrderDO buildOrderDO(final Order order) {
        return OrderDO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .gamesIds(order.getGamesIds())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
