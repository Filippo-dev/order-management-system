package com.company.ordermanagementsystem.repository;

import com.company.ordermanagementsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
