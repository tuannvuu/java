package com.example.demo.entity;

public enum OrderStatus {
    PENDING, // Chờ xác nhận
    CONFIRMED, // Đã xác nhận - ✅ THÊM
    PROCESSING, // Đang xử lý
    SHIPPING, // Đang giao hàng - ✅ ĐỔI TỪ SHIPPED
    DELIVERED, // Đã giao hàng
    COMPLETED, // Hoàn thành - ✅ THÊM
    CANCELLED, // Đã hủy
    REFUNDED // Đã hoàn tiền - ✅ THÊM (optional)
}