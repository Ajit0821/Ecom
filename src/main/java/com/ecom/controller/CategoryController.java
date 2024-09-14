package com.ecom.controller;

import com.ecom.Constants.Constants;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.model.Category;
import com.ecom.payload.CategoryDto;
import com.ecom.payload.CategoryResponse;
import com.ecom.pincode.PostalApi;
import com.ecom.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostalApi postalApi;



    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam (defaultValue = Constants.PAGE_NO)Integer pageNo   ,
                                                             @RequestParam (defaultValue = Constants.PAGE_SIZE) Integer pageSize,
                                                             @RequestParam (name="sortBy", defaultValue = Constants.SORT_BY) String sortBy,
                                                             @RequestParam (name = "sortOrder" , defaultValue = Constants.SORT_ORDER) String sortOrder){
        CategoryResponse categories = categoryService.getAllCategories(pageNo,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long categoryId) throws ResourceNotFoundException {
            CategoryDto categoryDto = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }


    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody Category category,
                                                 @PathVariable Long categoryId){
        try{
            CategoryDto savedCategory = categoryService.updateCategory(category, categoryId);
            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getStatusCode());
        }
    }
    @GetMapping("/pincode/{pincode}")
    public ResponseEntity<List<String>> noOfPincodesFound(@PathVariable String pincode) throws Exception {
        List<String> pincodes=categoryService.findPincodes(pincode);
        return (new ResponseEntity<>(pincodes, HttpStatus.OK));
    }
    @DeleteMapping("/admin/categories")
    public ResponseEntity<String> deleteAllCategories(){
        categoryService.deleteAllCategegory();
        return (new ResponseEntity<>("All caegories are deleted successfully", HttpStatus.OK));
    }
}
