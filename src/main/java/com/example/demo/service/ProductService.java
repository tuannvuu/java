package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public ProductService(ProductRepository productRepository,
            CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findAll(Sort sort) {
        return productRepository.findAll(sort);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteById(Long id) {
        // Xóa cart items trước
        cartItemRepository.deleteByProductId(id);

        // Sau đó xóa product
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

    public Product update(Long id, Product request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImage(request.getImage());
        product.setDescription(request.getDescription());

        if (request.getCategory() != null && request.getCategory().getId() != null) {
            product.setCategory(
                    new com.example.demo.entity.Category(
                            request.getCategory().getId()));
        }

        return productRepository.save(product);
    }
}