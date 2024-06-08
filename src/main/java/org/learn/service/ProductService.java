package org.learn.service;

import org.learn.model.product.ProductDto;
import org.learn.model.product.ProductRequestDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(String email, ProductRequestDto request);
    ProductDto getProductDetail(String code);
    List<ProductDto> getProductByMerchant(String email);
}
