package org.example.ordergames.domain.model;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Order was not found in the database!";

    private final Long orderId;

    public OrderNotFoundException(final Long orderId) {
        super(ERROR_MESSAGE);
        this.orderId = orderId;
    }
}
