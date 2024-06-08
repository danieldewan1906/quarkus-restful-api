package org.learn.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.learn.core.StringUtils;
import org.learn.entity.Merchant;
import org.learn.entity.User;
import org.learn.model.merchant.MerchantDto;
import org.learn.model.merchant.MerchantRequestDto;
import org.learn.model.product.ProductDto;
import org.learn.model.user.UserDto;
import org.learn.model.user.UserRequestDto;
import org.learn.repository.MerchantRepository;
import org.learn.repository.UserRepository;
import org.learn.service.ProductService;
import org.learn.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private MerchantRepository merchantRepository;
    @Inject
    private ProductService productService;

    @Override
    @Transactional
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).firstResultOptional().orElseThrow(() -> new WebApplicationException("User Not Found", 404));
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getIsMerchant(), user.getIsAffiliate(), null);
    }

    @Override
    @Transactional
    public UserDto getCurrentUser(String token) {
        User user = userRepository.findByToken(token)
                .firstResultOptional().orElseThrow(() -> new WebApplicationException("User Not Found", 404));

        if (user.getIsMerchant()) {
            List<ProductDto> productDtos = productService.getProductByMerchant(user.getEmail());
            return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getIsMerchant(), user.getIsAffiliate(), productDtos);
        }

        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getIsMerchant(), user.getIsAffiliate(), null);
    }

    @Override
    @Transactional
    public void updateUser(String email, UserRequestDto request) {
        User user = userRepository.findByEmail(email).firstResultOptional().orElseThrow(() -> new WebApplicationException("User Not Found", 404));

        if (!request.password().equals(request.confirmPassword())) {
            throw new RuntimeException("Your password wrong!");
        }

        String hashPassword = StringUtils.base64Encode(request.password());
        user.setName(request.name());
        user.setPassword(hashPassword);
        userRepository.persist(user);
    }

    @Override
    @Transactional
    public MerchantDto createMerchant(String email, MerchantRequestDto request) {
        User user = userRepository.findByEmail(email).firstResultOptional().orElseThrow(() -> new WebApplicationException("User Not Found", 404));
        Merchant checkMerchant = merchantRepository.findByUserId(user).firstResult();
        if (checkMerchant != null) {
            throw new WebApplicationException("User Already Merchant!", 500);
        }

        Merchant merchant = new Merchant();
        merchant.setUser(user);
        merchant.setAddress(request.address());
        merchant.setPhoneNumber(request.phoneNumber());
        merchantRepository.persist(merchant);

        user.setIsMerchant(true);
        userRepository.persist(user);
        return new MerchantDto(merchant.getId(), merchant.getUser().getId(), merchant.getAddress(), merchant.getPhoneNumber());
    }
}
