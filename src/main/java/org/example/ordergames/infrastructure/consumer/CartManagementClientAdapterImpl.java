package org.example.ordergames.infrastructure.consumer;

import org.example.ordergames.domain.model.CartDO;
import org.example.ordergames.infrastructure.consumer.model.CartDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartManagementClientAdapterImpl implements CartManagementClientAdapter {

    private final CartManagementClient cartManagementClient;

    public CartManagementClientAdapterImpl(final CartManagementClient cartManagementClient) {
        this.cartManagementClient = cartManagementClient;
    }

    @Override
    public Optional<CartDO> findById(final long id) {
        final ResponseEntity<CartDTO> res = cartManagementClient.findById(id);
        if (!res.getStatusCode().is2xxSuccessful()) {
            return Optional.empty();
        }
        return res.getBody() != null ? buildCart(res.getBody()) : Optional.empty();
    }

    private Optional<CartDO> buildCart(final CartDTO body) {
        final CartDO cart = CartDO.builder()
                .id(body.getId())
                .gamesIds(body.getGamesIds())
                .totalPrice(body.getTotalPrice())
                .build();
        return Optional.of(cart);
    }
}
