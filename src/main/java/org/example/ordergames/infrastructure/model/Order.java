package org.example.ordergames.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.ordergames.domain.model.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Document(collection = "orders")
public class Order {

    @Transient
    public static final String SEQUENCE_NAME = "orders_sequence";

    @Id
    private long id;

    private long userId;

    private List<Long> gamesIds;

    private BigDecimal totalPrice;

    private OrderStatus orderStatus;
}
