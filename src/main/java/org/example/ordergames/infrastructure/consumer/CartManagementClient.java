package org.example.ordergames.infrastructure.consumer;

import org.example.ordergames.infrastructure.consumer.model.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("carts")
public interface CartManagementClient {

    @GetMapping("/api/{id}")
    ResponseEntity<CartDTO> findById(@PathVariable final long id);
}
