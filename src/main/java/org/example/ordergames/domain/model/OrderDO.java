package org.example.ordergames.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderDO {

    private long id;

    private long userId;

    private List<Long> gamesIds;

    private BigDecimal totalPrice;

    private OrderStatus orderStatus;
}
