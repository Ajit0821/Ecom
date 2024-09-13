package com.ecom.service;

import com.ecom.exception.ResourceNotFoundException;
import com.ecom.model.Category;
import com.ecom.payload.CategoryDto;
import com.ecom.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNo,Integer pageSize,String sortBy,String sortOrder);

    CategoryDto createCategory(Category categoryDto);

    CategoryDto deleteCategory(Long categoryId) throws ResourceNotFoundException;

    CategoryDto updateCategory(Category category, Long categoryId);

    List<String> findPincodes(String pincode) throws Exception;

    String deleteAllCategegory();

}
