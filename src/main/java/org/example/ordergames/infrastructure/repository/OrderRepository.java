package org.example.ordergames.infrastructure.repository;

import org.example.ordergames.infrastructure.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Long> {
}
