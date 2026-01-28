package com.example.demo;

import com.example.demo.service.ProductService;

import com.example.demo.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void testSaveProduct() {
        // given
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100000);
        product.setQuantity(10);
        product.setDescription("Unit test product");

        // when
        Product savedProduct = productService.save(product);

        // then
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("Test Product", savedProduct.getName());
    }

    @Test
    void testFindAllProducts() {
        // when
        var products = productService.findAll();

        // then
        assertNotNull(products);
    }
}
