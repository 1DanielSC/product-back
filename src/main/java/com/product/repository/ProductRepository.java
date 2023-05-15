package com.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    @Query(value = "SELECT * FROM Produto p WHERE p.name = ?1", nativeQuery = true)
    Optional<Product> findByName(String name);
}
