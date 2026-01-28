package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //  tìm product theo categoryId
    List<Product> findByCategoryId(Long categoryId);

    //  tìm product theo tên
    List<Product> findByNameContaining(String keyword);

    //  tìm product có giá lớn hơn
    List<Product> findByPriceGreaterThan(double price);

    //  tìm product theo khoảng giá
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    //   JOIN Product với Category + điều kiện
    @Query("""
                SELECT p FROM Product p
                JOIN p.category c
                WHERE c.id = :categoryId
                AND p.price > :price
            """)
    List<Product> findProductsByCategoryAndMinPrice(
            @Param("categoryId") Long categoryId,
            @Param("price") double price);

}
