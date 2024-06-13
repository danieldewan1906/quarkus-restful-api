package org.learn.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.learn.core.StringUtils;
import org.learn.entity.*;
import org.learn.model.orders.OrderDto;
import org.learn.model.orders.OrderRequestDto;
import org.learn.model.product.ProductDto;
import org.learn.model.user.UserDto;
import org.learn.repository.*;
import org.learn.service.OrderService;
import org.learn.service.ProductService;
import org.learn.service.UserService;

import java.util.Date;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderRepository orderRepository;
    @Inject
    private UserReferralRepository userReferralRepository;
    @Inject
    private HistoryUserAffiliateRepository historyUserAffiliateRepository;
    @Inject
    private AffiliateProductRepository affiliateProductRepository;
    @Inject
    private AffiliateRepository affiliateRepository;
    @Inject
    private UserService userService;
    @Inject
    private ProductService productService;
    @Inject
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public OrderDto checkout(String email, OrderRequestDto request) {
        System.out.println(request.url());
        UserDto userDto = userService.getUserByEmail(email);
        User user = objectMapper.convertValue(userDto, User.class);
        ProductDto productDto = productService.getProductDetail(request.productCode());
        Product product = objectMapper.convertValue(productDto, Product.class);

        String orderNo = StringUtils.randomString(5, "A");
        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setAmount(request.amount());
        order.setQuantity(request.qty());
        order.setOrderNo(orderNo);
        order.setCreatedAt(new Date());

        orderRepository.persist(order);

        if (request.url() != null) {
            UserReferral userReferral = new UserReferral();
            String referralCode = request.url().split("referral=")[1];
            String decodeReferralCode = StringUtils.base64Decode(referralCode);
            userReferral.setOrder(order);
            userReferral.setReferralCode(decodeReferralCode);
            userReferral.setCreatedAt(new Date());

            userReferralRepository.persist(userReferral);

            AffiliateProducts affiliateProducts = affiliateProductRepository.getByReferralCode(decodeReferralCode)
                    .firstResultOptional()
                    .orElseThrow(() -> new WebApplicationException("Referral Not Found", 404));

            Affiliate affiliate = affiliateRepository.findByIdOptional(affiliateProducts.getAffiliate().getId())
                    .orElseThrow(() -> new WebApplicationException("Affiliate Not Found", 404));

            HistoryUserAffiliate historyUserAffiliate = new HistoryUserAffiliate();
            historyUserAffiliate.setAffiliate(affiliate);
            historyUserAffiliate.setOrder(order);

            Double totalFee = Math.floor(request.amount() * 0.1);
            historyUserAffiliate.setTotalFee(totalFee);
            historyUserAffiliate.setCreatedAt(new Date());

            historyUserAffiliateRepository.persist(historyUserAffiliate);
        }

        return new OrderDto(
                userDto,
                productDto,
                request.qty(),
                request.amount()
        );
    }
}
