package com.example.demo.controller;

import com.example.demo.dto.AddToCartRequest;
import com.example.demo.dto.UpdateCartRequest;
import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import com.example.demo.service.CartService;
import com.example.demo.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    // thêm sản phẩm
    @PostMapping("/add")
    public String addToCart(
            @RequestBody AddToCartRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("USERNAME FROM TOKEN = " + userDetails.getUsername());

        User user = userService.findByUsername(userDetails.getUsername());
        cartService.addToCart(user, request.getProductId(), request.getQuantity());
        return "Added to cart";
    }

    // xem giỏ hàng
    @GetMapping
    public Cart getMyCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());

        return cartService.getCart(user);
    }

    @PutMapping("/update")
    public Cart updateCart(
            @RequestBody UpdateCartRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());
        return cartService.updateItem(
                user,
                request.getProductId(),
                request.getQuantity());
    }

}
