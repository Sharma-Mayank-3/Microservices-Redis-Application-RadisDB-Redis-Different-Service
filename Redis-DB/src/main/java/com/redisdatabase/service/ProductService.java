package com.redisdatabase.service;

import com.redisdatabase.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto getProductById(int productId);

    List<ProductDto> getAllProduct();

    String deleteProduct(int productId);

    ProductDto updateProduct(int productId, ProductDto productDto);

}
