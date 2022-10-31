package org.example.ordergames.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum OrderStatus {

    CREATED("created"),
    PAID("paid"),
    SHIPPED("shipped"),
    COMPLETED("completed"),
    CANCELLED("cancelled");

    private final String status;

    OrderStatus(final String status) {
        this.status = status;
    }

    @JsonCreator
    public static OrderStatus fromText(final String text) {
        for (final OrderStatus r : OrderStatus.values()) {
            if (r.getStatus().equals(text)) {
                return r;
            }
        }
        throw new IllegalArgumentException();
    }
}
