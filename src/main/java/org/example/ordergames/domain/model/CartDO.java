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
public class CartDO {

    private Long id;

    private List<Long> gamesIds;

    private BigDecimal totalPrice;
}
