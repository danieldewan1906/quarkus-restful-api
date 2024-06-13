package org.learn.model.affiliate;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.learn.model.product.ProductDto;

import java.util.List;

public record AffiliateDto(
        String id,
        @JsonProperty("user_id")
        Long userId,
        String address,
        @JsonProperty("phone_number")
        String phoneNumber,
        @JsonProperty("total_fee")
        List<ProductDto> products
) {
}
