package org.learn.service;

import jakarta.ws.rs.core.SecurityContext;
import org.learn.model.affiliate.AffiliateDto;
import org.learn.model.affiliate.AffiliateRequestDto;
import org.learn.model.affiliate.AffiliateRequestProductDto;
import org.learn.model.merchant.MerchantDto;
import org.learn.model.merchant.MerchantRequestDto;
import org.learn.model.user.UserDto;
import org.learn.model.user.UserRequestDto;

public interface UserService {

    UserDto getUserByEmail(String email);
    UserDto getCurrentUser(String token);
    void updateUser(String email, UserRequestDto request);
    MerchantDto createMerchant(String email, MerchantRequestDto request);
    AffiliateDto requestForAffiliate(String email, AffiliateRequestDto request);
    AffiliateDto requestProducts(String email, AffiliateRequestProductDto request);
}
