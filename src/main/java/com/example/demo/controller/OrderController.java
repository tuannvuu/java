package com.example.demo.controller;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.UpdateOrderStatusRequest;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // USER: xem đơn của mình
    @GetMapping("/my-orders")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Order>> getMyOrders(Authentication authentication) {
        System.out.println("=== GET MY ORDERS ===");
        logAuthentication();

        String username = authentication.getName();
        return ResponseEntity.ok(
                orderService.findOrdersByUsername(username));
    }

    // USER: tạo order
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody CreateOrderRequest request,
            Authentication authentication) {

        System.out.println("========================================");
        System.out.println("=== CREATE ORDER ENDPOINT HIT ===");
        System.out.println("========================================");

        // Log toàn bộ authentication context
        logAuthentication();

        System.out.println("--- Request Details ---");
        System.out.println("Request Body: " + request);
        System.out.println("Authentication param: " + authentication);

        if (authentication != null) {
            System.out.println("Username from auth param: " + authentication.getName());
            System.out.println("Authorities from auth param: " + authentication.getAuthorities());
        } else {
            System.out.println("❌ Authentication parameter is NULL!");
            return ResponseEntity.status(401).build();
        }

        System.out.println("========================================");

        String username = authentication.getName();
        return ResponseEntity.ok(
                orderService.createOrder(request, username));
    }

    // USER + ADMIN
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        System.out.println("=== GET ORDER BY ID ===");
        logAuthentication();

        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ADMIN
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Order>> getAllOrdersAdmin() {
        System.out.println("=== GET ALL ORDERS ADMIN ===");
        logAuthentication();

        return ResponseEntity.ok(orderService.findAll());
    }

    @PutMapping("/admin/{orderId}/status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusRequest request) {

        System.out.println("=== UPDATE ORDER STATUS ===");
        logAuthentication();

        OrderStatus status = OrderStatus.valueOf(request.getStatus());
        return ResponseEntity.ok(
                orderService.updateOrderStatus(orderId, status));
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        System.out.println("=== DELETE ORDER ===");
        logAuthentication();

        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    // Helper method để log authentication details
    private void logAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("--- Security Context ---");
        if (auth != null) {
            System.out.println("✅ Authentication exists");
            System.out.println("Principal: " + auth.getPrincipal());
            System.out.println("Name: " + auth.getName());
            System.out.println("Authorities: " + auth.getAuthorities());
            System.out.println("Is Authenticated: " + auth.isAuthenticated());
            System.out.println("Details: " + auth.getDetails());

            // Log từng authority riêng
            System.out.println("--- Individual Authorities ---");
            auth.getAuthorities().forEach(authority -> System.out.println("  → " + authority.getAuthority()));
        } else {
            System.out.println("❌ Authentication is NULL in SecurityContext!");
        }
        System.out.println("------------------------");
    }
}
