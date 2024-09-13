package com.ecom.service;

import com.ecom.model.Product;
import com.ecom.payload.ProductDto;
import com.ecom.payload.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(Product product, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse getProductByCategoryId(Long categoryId);
}
