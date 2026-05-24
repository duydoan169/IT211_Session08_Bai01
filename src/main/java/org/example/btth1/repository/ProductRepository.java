package org.example.btth1.repository;

import org.example.btth1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity + :amount WHERE p.sku = :sku")
    int increaseQuantityBySku(@Param("sku") String sku, @Param("amount") int amount);

    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity - :amount WHERE p.sku = :sku AND p.quantity >= :amount")
    void decreaseQuantityBySku(@Param("sku") String sku, @Param("amount") int amount);

    @Query("SELECT p.quantity FROM Product p WHERE p.sku = :sku")
    Optional<Integer> findQuantityBySku(@Param("sku") String sku);

    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Product p")
    Long sumAllQuantities();

    @Query("SELECT COALESCE(SUM(p.quantity * p.price), 0.0) FROM Product p")
    Double sumAllValues();
}
