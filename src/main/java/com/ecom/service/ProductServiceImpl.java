package com.ecom.service;

import com.ecom.exception.ApiException;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.payload.ProductDto;
import com.ecom.payload.ProductResponse;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto addProduct(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category","categoryId",categoryId,"category not found"));
        product.setCategory(category);
        double specialPrice = product.getPrice()-
                ((product.getDiscount()*0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        product.setImage("default_image");
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> product=productRepository.findAll();
        if(product.isEmpty()){
            throw new ApiException("No Product Present");
        }
         List<ProductDto> productDtoList = product.stream()
                .map(product1 -> modelMapper.map(product1,ProductDto.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtoList);
        return productResponse;
    }

    @Override
    public ProductResponse getProductByCategoryId(Long categoryId) {
        categoryRepository.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId,"category not found"));
        List<Product> productList = productRepository.findByCategory(categoryId);
        List<ProductDto> productDto = productList.stream().map(product -> modelMapper.map(product,ProductDto.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDto);
        return productResponse;
    }
}
