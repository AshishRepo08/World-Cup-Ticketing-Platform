package com.fifa.ticket_inventory_service.repository;

import com.fifa.ticket_inventory_service.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Match, Long> {
    boolean existsBySkuCodeAndTicketsLeftGreaterThanEqual(String skuCode, Integer ticketsNeeded);
}
