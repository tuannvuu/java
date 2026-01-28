package com.example.demo.service;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderItemRequest;
import com.example.demo.entity.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ProductRepository; // ✅ THÊM
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository; // ✅ THÊM

    public OrderService(OrderRepository orderRepository,
            UserRepository userRepository,
            ProductRepository productRepository) { // ✅ INJECT
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // ===== QUERY =====
    @Transactional(readOnly = true) // ✅ THÊM để tránh LazyInitializationException
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> findOrdersByUsername(String username) {
        return orderRepository.findByUserUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    // ===== CREATE ORDER =====
    @Transactional
    public Order createOrder(CreateOrderRequest request, String username) {

        if (request.getShippingAddress() == null || request.getShippingAddress().isBlank()) {
            throw new RuntimeException("Địa chỉ giao hàng không được để trống");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());

        // ===== MOCK PAYMENT =====
        String method = request.getPaymentMethod();
        if (method == null || method.isBlank()) {
            method = "COD";
        }

        order.setPaymentMethod(method);

        // MOCK trạng thái đơn
        if ("COD".equalsIgnoreCase(method)) {
            order.setStatus(OrderStatus.PENDING); // chờ giao
        } else {
            order.setStatus(OrderStatus.CONFIRMED); // giả lập đã thanh toán
        }

        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        double total = 0;

        // ===== ORDER ITEMS =====
        if (request.getItems() != null) {
            for (OrderItemRequest i : request.getItems()) {

                Product product = productRepository.findById(i.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found: " + i.getProductId()));

                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProduct(product);
                item.setQuantity(i.getQuantity());
                item.setPrice(i.getPrice());

                total += i.getPrice() * i.getQuantity();
                order.getOrderItems().add(item);
            }
        }

        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    // ===== UPDATE STATUS =====
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    // Thêm vào OrderService.java
    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }
}