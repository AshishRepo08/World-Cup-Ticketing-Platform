package com.fifa.fifaWorldCup_OrderService.repository;

import com.fifa.fifaWorldCup_OrderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
