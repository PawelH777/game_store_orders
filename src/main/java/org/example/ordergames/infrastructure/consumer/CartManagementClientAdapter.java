package org.example.ordergames.infrastructure.consumer;

import org.example.ordergames.domain.model.CartDO;

import java.util.Optional;

public interface CartManagementClientAdapter {

    Optional<CartDO> findById(final long id);
}
