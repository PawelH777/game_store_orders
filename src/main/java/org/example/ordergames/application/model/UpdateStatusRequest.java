package org.example.ordergames.application.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ordergames.domain.model.OrderStatus;

@Getter
@Setter
@NoArgsConstructor
public class UpdateStatusRequest {

    private OrderStatus orderStatus;
}
