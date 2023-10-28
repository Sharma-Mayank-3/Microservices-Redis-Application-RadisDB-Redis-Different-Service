package com.redisdatabase.controller;

import com.redisdatabase.dto.ApiResponse;
import com.redisdatabase.dto.ProductDto;
import com.redisdatabase.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/redis/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        ProductDto product = this.productService.createProduct(productDto);
        ApiResponse build = ApiResponse.builder().b(true).message("product created").data(product).build();
        return new ResponseEntity<>(build, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") int productId){
        ProductDto product = this.productService.getProductById(productId);
        ApiResponse build = ApiResponse.builder().b(true).message("get product byId").data(product).build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllProduct(){
        List<ProductDto> allProduct = this.productService.getAllProduct();
        ApiResponse build = ApiResponse.builder().b(true).message("get all products").data(allProduct).build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") int productId){
        String s = this.productService.deleteProduct(productId);
        ApiResponse build = ApiResponse.builder().b(true).message("delete product by Id").data(s).build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable("productId") int productId,
            @RequestBody ProductDto productDto
            ){
        ProductDto productDto1 = this.productService.updateProduct(productId, productDto);
        ApiResponse build = ApiResponse.builder().b(true).message("update product").data(productDto1).build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

}
