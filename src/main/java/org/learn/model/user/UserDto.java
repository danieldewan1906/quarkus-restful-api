package org.learn.model.user;

import org.learn.model.product.ProductDto;

import java.util.List;

public record UserDto(

        Long id,
        String name,
        String email,
        String role,
        Boolean isMerchant,
        Boolean isAffiliate,
        Double totalFee,
        List<ProductDto> products
) {
}
