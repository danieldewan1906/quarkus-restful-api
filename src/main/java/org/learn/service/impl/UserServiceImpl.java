package org.learn.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.learn.core.StringUtils;
import org.learn.entity.*;
import org.learn.model.affiliate.AffiliateDto;
import org.learn.model.affiliate.AffiliateRequestDto;
import org.learn.model.affiliate.AffiliateRequestProductDto;
import org.learn.model.merchant.MerchantDto;
import org.learn.model.merchant.MerchantRequestDto;
import org.learn.model.product.ProductDto;
import org.learn.model.user.UserDto;
import org.learn.model.user.UserRequestDto;
import org.learn.repository.*;
import org.learn.service.ProductService;
import org.learn.service.UserService;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private MerchantRepository merchantRepository;
    @Inject
    private ProductService productService;
    @Inject
    private AffiliateRepository affiliateRepository;
    @Inject
    private ObjectMapper mapper;
    @Inject
    private AffiliateProductRepository affiliateProductRepository;
    @Inject
    private HistoryUserAffiliateRepository historyUserAffiliateRepository;

    @Override
    @Transactional
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).firstResultOptional().orElseThrow(() -> new WebApplicationException("User Not Found", 404));
        Double totalFee = 0.0;
        if (user.getIsAffiliate()) {
            Affiliate affiliate = affiliateRepository.findByUserId(user).firstResultOptional()
                    .orElseThrow(() -> new WebApplicationException("Affiliate Not Found", 404));

            List<HistoryUserAffiliate> historyUserAffiliates = historyUserAffiliateRepository.getByAffiliate(affiliate);
            for (int i = 0; i < historyUserAffiliates.size(); i++) {
                totalFee += historyUserAffiliates.get(i).getTotalFee();
            }
        }
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getIsMerchant(), user.getIsAffiliate(), totalFee, null);
    }

    @Override
    @Transactional
    public UserDto getCurrentUser(String token) {
        User user = userRepository.findByToken(token)
                .firstResultOptional().orElseThrow(() -> new WebApplicationException("User Not Found", 404));

        if (user.getIsMerchant()) {
            List<ProductDto> productDtos = productService.getProductByMerchant(user.getEmail());
            return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getIsMerchant(), user.getIsAffiliate(), 0.0, productDtos);
        }

        Double totalFee = 0.0;
        if (user.getIsAffiliate()) {
            Affiliate affiliate = affiliateRepository.findByUserId(user).firstResultOptional()
                    .orElseThrow(() -> new WebApplicationException("Affiliate Not Found", 404));

            List<HistoryUserAffiliate> historyUserAffiliates = historyUserAffiliateRepository.getByAffiliate(affiliate);
            for (int i = 0; i < historyUserAffiliates.size(); i++) {
                totalFee += historyUserAffiliates.get(i).getTotalFee();
            }
        }

        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getIsMerchant(), user.getIsAffiliate(), totalFee, null);
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

    @Override
    @Transactional
    public AffiliateDto requestForAffiliate(String email, AffiliateRequestDto request) {
        User user = userRepository.findByEmail(email).firstResultOptional().orElseThrow(() -> new WebApplicationException("User Not Found", 404));
        if (user.getIsMerchant()) {
            throw new WebApplicationException("User is Merchant. Cannot Request For Affiliate", 500);
        }

        Affiliate checkAffiliate = affiliateRepository.findByUserId(user).firstResult();
        if (checkAffiliate != null) {
            throw new WebApplicationException("User Already Affiliate!", 500);
        }

        Affiliate affiliate = new Affiliate();
        affiliate.setPhoneNumber(request.phoneNumber());
        affiliate.setAddress(request.address());
        affiliate.setUser(user);
        affiliateRepository.persist(affiliate);

        user.setIsAffiliate(true);
        userRepository.persist(user);
        return new AffiliateDto(affiliate.getId(), affiliate.getUser().getId(), affiliate.getAddress(), affiliate.getPhoneNumber(), null);
    }

    @Override
    @Transactional
    public AffiliateDto requestProducts(String email, AffiliateRequestProductDto request) {
        User user = userRepository.findByEmail(email).firstResultOptional().orElseThrow(() -> new WebApplicationException("User Not Found", 404));
        if (!user.getIsAffiliate()) {
            throw new WebApplicationException("User is Not Affiliate. Cannot Request Product", 500);
        }

        Affiliate affiliate = affiliateRepository.findByUserId(user).firstResult();
        if (!affiliate.getIsActive()) {
            throw new WebApplicationException("User Affiliate Doesn't Active!", 500);
        }

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < request.productCode().size(); i++) {
            ProductDto productDto = productService.getProductDetail(request.productCode().get(i));
            Product product = mapper.convertValue(productDto, Product.class);
            products.add(product);

            AffiliateProducts affiliateProducts = new AffiliateProducts();
            String referralCode = StringUtils.randomString(10, null);
            affiliateProducts.setAffiliate(affiliate);
            affiliateProducts.setIsValidMerchant(false);
            affiliateProducts.setProduct(product);
            affiliateProducts.setReferralCode(referralCode);

            String hashReferral = StringUtils.base64Encode(referralCode);
            String url = productDto.linkProduct() + "&referral=" + hashReferral;
            affiliateProducts.setLinkAffiliate(url);
            affiliateProductRepository.persist(affiliateProducts);
        }

        List<ProductDto> productDtos = products.stream().map(data -> mapper.convertValue(data, ProductDto.class)).toList();
        return new AffiliateDto(affiliate.getId(), affiliate.getUser().getId(), affiliate.getAddress(), affiliate.getPhoneNumber(), productDtos);
    }
}
