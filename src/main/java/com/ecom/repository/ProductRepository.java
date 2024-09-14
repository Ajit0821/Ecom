package com.ecom.repository;

import com.ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p from Product p where p.category.id = :categoryId")
    List<Product> findByCategory(@Param("categoryId") Long categoryId);

    List<Product> findByProductNameLikeIgnoreCase(String productName);



}
