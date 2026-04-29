package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
