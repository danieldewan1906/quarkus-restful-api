package org.learn.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.learn.entity.Merchant;
import org.learn.entity.Product;
import org.learn.entity.User;
import org.learn.model.product.ProductDto;
import org.learn.model.product.ProductRequestDto;
import org.learn.model.user.UserDto;
import org.learn.repository.MerchantRepository;
import org.learn.repository.ProductRepository;
import org.learn.service.ProductService;
import org.learn.service.UserService;

import java.util.List;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    @Inject
    private ProductRepository productRepository;
    @Inject
    private UserService userService;
    @Inject
    private MerchantRepository merchantRepository;
    @Inject
    private ObjectMapper mapper;

    @Override
    @Transactional
    public ProductDto createProduct(String email, ProductRequestDto request) {
        UserDto userDto = userService.getUserByEmail(email);
        if (userDto == null && !userDto.isMerchant() && userDto.isAffiliate()) {
            throw new WebApplicationException("User Not Allowed to Create Products", 500);
        }

        User user = mapper.convertValue(userDto, User.class);
        Merchant merchant = merchantRepository.findByUserId(user)
                .firstResultOptional().orElseThrow(() -> new WebApplicationException("User Merchant Not Found", 404));

        if (!merchant.getIsActive()) {
            throw new WebApplicationException("User Merchant Not Activated By Admin", 403);
        }

        Product checkProduct = productRepository.findById(request.code());
        if (checkProduct != null) {
            throw new WebApplicationException("Product Already Exists!", 500);
        }

        String link = "www.ecommerceku.com/products?code="+request.code();
        Product product = new Product();
        product.setCode(request.code());
        product.setProductName(request.productName());
        product.setRetailPrice(request.retailPrice());
        product.setStocks(request.stocks());
        product.setLinkProduct(link);
        product.setMerchant(merchant);

        productRepository.persist(product);
        return new ProductDto(product.getCode(), product.getProductName(), product.getRetailPrice(),
                product.getStocks(), product.getIsActive(), product.getLinkProduct(), product.getCreatedAt());
    }

    @Override
    @Transactional
    public ProductDto getProductDetail(String code) {
        Product product = productRepository.findByIdOptional(code)
                .orElseThrow(() -> new WebApplicationException("Product Not Found", 404));
        return new ProductDto(product.getCode(), product.getProductName(), product.getRetailPrice(),
                product.getStocks(), product.getIsActive(), product.getLinkProduct(),
                product.getCreatedAt());
    }

    @Override
    @Transactional
    public List<ProductDto> getProductByMerchant(String email) {
        UserDto userDto = userService.getUserByEmail(email);
        User user = mapper.convertValue(userDto, User.class);
        Merchant merchant = merchantRepository.findByUserId(user).firstResultOptional()
                .orElseThrow(() -> new WebApplicationException("User Merchant Not Found", 404));
        List<ProductDto> productDtos = productRepository.findByMerchantId(merchant)
                .stream().map(data -> mapper.convertValue(data, ProductDto.class)).toList();
        return productDtos;
    }
}
