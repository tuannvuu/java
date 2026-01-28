package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ✅ Lấy tất cả products
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // ✅ Lấy product theo ID
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    // ✅ Tạo hoặc cập nhật product
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // ✅ Xóa product theo ID
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> findProductsByCategoryAndMinPrice(
            Long categoryId,
            double price) {

        return productRepository
                .findProductsByCategoryAndMinPrice(categoryId, price);
    }

    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

    public List<Product> findAll(Sort sort) {
        return productRepository.findAll(sort);
    }

}