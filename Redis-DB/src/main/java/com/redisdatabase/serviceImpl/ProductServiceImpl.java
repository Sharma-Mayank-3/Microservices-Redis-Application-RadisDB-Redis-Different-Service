package com.redisdatabase.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redisdatabase.dto.ProductDto;
import com.redisdatabase.entity.Product;
import com.redisdatabase.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String HASH_KEY = "ProductHash";

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = this.modelMapper.map(productDto, Product.class);
        this.redisTemplate.opsForHash().put(HASH_KEY, product.getProductId(), product);
//        this.redisTemplate.opsForValue().set(product.getProductId(), product);
        return productDto;
    }

    @Override
    @Cacheable(key = "#productId", value = "Product")
    public ProductDto getProductById(int productId) {
        log.info("connect to db...");
        Object o = this.redisTemplate.opsForHash().get(HASH_KEY, productId);
//        Object o = this.redisTemplate.opsForValue().get(productId);
        return this.modelMapper.map(o, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProduct() {
        return this.redisTemplate.opsForHash().values(HASH_KEY);
//        this.redisTemplate.opsForValue().multiGet()
//        return (List<ProductDto>) values.stream().map(value-> this.modelMapper.map(value, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(key = "#productId", value = "Product")
    public String deleteProduct(int productId) {
        log.info("connect to db...");
        this.redisTemplate.opsForHash().delete(HASH_KEY, productId);
//        this.redisTemplate.opsForValue().getAndDelete(productId);
        return "product deleted";
    }

    @Override
    @CachePut(key = "#productId", value = "Product")
    public ProductDto updateProduct(int productId, ProductDto productDto) {
        log.info("connect to db...");
        Object o = this.redisTemplate.opsForHash().get(HASH_KEY, productId);
//        Object o = this.redisTemplate.opsForValue().get(productId);
        Product product = this.modelMapper.map(o, Product.class);
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        this.redisTemplate.opsForHash().put(HASH_KEY, productId, product);
//        this.redisTemplate.opsForValue().set(productId, product);
        return productDto;
    }
}
