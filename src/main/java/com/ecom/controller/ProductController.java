package com.ecom.controller;

import com.ecom.model.Product;
import com.ecom.payload.ProductDto;
import com.ecom.payload.ProductResponse;
import com.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody Product product,
                                                 @PathVariable Long categoryId){
        ProductDto productDto = productService.addProduct(product,categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProduct(){
        ProductResponse allProducts=productService.getAllProducts();
        return new ResponseEntity<>(allProducts,HttpStatus.OK);
    }
    @GetMapping("/public/product/{categoryId}")
    public ResponseEntity<ProductResponse> getProductByCategoryIdId(@PathVariable Long categoryId){
        ProductResponse allProducts=productService.getProductByCategoryId(categoryId);
        return new ResponseEntity<>(allProducts,HttpStatus.OK);
    }

    @GetMapping("/public/product/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword){
        ProductResponse product=productService.getProductByKeyword(keyword);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    @DeleteMapping("/admin/product/{productId}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long productId){
        ProductDto productDto=productService.deleteProductById(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    @PutMapping("/admin/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody Product product,@PathVariable Long productId){
        ProductDto  productDto=productService.updateProductById(product,productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
}
