package com.store.orderservice.repository;

import com.store.orderservice.entities.Order;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Observed
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
