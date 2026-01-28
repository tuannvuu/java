package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ Ai cũng xem được - danh sách (REST thường)
    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return productService.findAll(sort);
    }

    // ✅ Ai cũng xem được - chi tiết (🔥 HATEOAS)
    @GetMapping("/{id}")
    public EntityModel<Product> getProductById(@PathVariable Long id) {

        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 🔥 HATEOAS: trả dữ liệu + link điều hướng
        return EntityModel.of(
                product,
                linkTo(methodOn(ProductController.class)
                        .getProductById(id))
                        .withSelfRel(), // link chính nó

                linkTo(methodOn(ProductController.class)
                        .getAllProducts("price", "asc"))
                        .withRel("all-products"),

                linkTo(methodOn(ProductController.class)
                        .getProductsByCategory(product.getCategory().getId()))
                        .withRel("category-products") // link theo category
        );
    }

    // ✅ Chỉ ADMIN mới tạo được
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    // ✅ Chỉ ADMIN mới xóa được
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // ✅ ĐÚNG
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    // ✅ Lấy sản phẩm theo category
    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.findByCategory(categoryId);
    }

    @GetMapping("/filter")
    public List<Product> filterProducts(
            @RequestParam Long categoryId,
            @RequestParam double price) {

        return productService
                .findProductsByCategoryAndMinPrice(categoryId, price);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String keyword) {

        return productService.searchByName(keyword);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product request) {
        return productService.update(id, request);
    }
}
