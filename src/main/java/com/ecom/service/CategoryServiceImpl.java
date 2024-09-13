package com.ecom.service;

import com.ecom.exception.ApiException;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.model.Category;
import com.ecom.payload.CategoryDto;
import com.ecom.payload.CategoryResponse;
import com.ecom.pincode.PostOffice;
import com.ecom.pincode.PostalApi;
import com.ecom.pincode.PostalResponse;
import com.ecom.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    private Long nextId = 1L;

    @Autowired
    private PostalApi postalApi;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNo,Integer pageSize,String sortBy,String sortOrder) {
            Sort sortByAndOrder =sortOrder.equalsIgnoreCase("asc")
                    ? Sort.by(sortBy).ascending()
                    :Sort.by(sortBy).descending();
            Pageable pageDetails = PageRequest.of(pageNo, pageSize,sortByAndOrder);
            Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
            List<Category> categoryList = categoryPage.getContent();
            List<CategoryDto> categoryDtoList = categoryList.stream()
                    .map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryDtoList(categoryDtoList);
            categoryResponse.setPageNumber(categoryPage.getNumber());
            categoryResponse.setPageSize(categoryPage.getSize());
            categoryResponse.setTotalPages(categoryPage.getTotalPages());
            categoryResponse.setLastPage(categoryPage.isLast());
            categoryResponse.setTotalElements(categoryPage.getTotalElements());
            return categoryResponse;
    }

    @Override
    public CategoryDto createCategory(Category category) {
        Category allCategory=categoryRepository.findByCategoryName(category.getCategoryName());
        if(allCategory!=null) {
            throw new ApiException("category already exist: " + category.getCategoryName());
        }
        category.setCategoryId(nextId++);
        Category savedCategory= categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto deleteCategory(Long categoryId) throws ResourceNotFoundException {
        List<Category> categories =categoryRepository.findAll();
        if(categories.isEmpty()){
            throw  new ApiException("Not Category present");
        }
        Category category = categories.stream().
                filter(e->e.getCategoryId().equals(categoryId)).
                findFirst().
                orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId,"category not found"));
        categoryRepository.delete(category);

        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Category category, Long categoryId) {
        List<Category> categories = categoryRepository.findAll();

        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        if(optionalCategory.isPresent()){
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            Category savedCategory = categoryRepository.save(existingCategory);
            CategoryDto updatedCategoryDto=modelMapper.map(savedCategory,CategoryDto.class);
            return updatedCategoryDto;
        } else {
            throw new ResourceNotFoundException("Category","categoryId",category.getCategoryId(),"Category Not Found");
        }
    }

    @Override
    public List<String> findPincodes(String pincode) throws Exception {
        PostalResponse[] responses = postalApi.getPostalData(pincode);
        List<String> distinctDistricts = Arrays.stream(responses)
                .flatMap(response -> response.getPostOffice().stream())
                .map(PostOffice::getDistrict)
                .distinct()
                .toList();
        return distinctDistricts;
    }

    @Override
    public String deleteAllCategegory() {
        if(categoryRepository.findAll().isEmpty()){
            throw  new ApiException("Not Category present");
        }
        categoryRepository.deleteAll();
        return "SuccessFully Deleted";
    }
}
