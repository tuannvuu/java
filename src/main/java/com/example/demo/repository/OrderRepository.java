package com.example.demo.repository;

import com.example.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Tìm orders theo username
    List<Order> findByUserUsername(String username);

    // Tìm orders theo user ID
    List<Order> findByUserId(Long userId);
}