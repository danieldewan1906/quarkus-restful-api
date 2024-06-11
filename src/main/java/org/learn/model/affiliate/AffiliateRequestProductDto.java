package org.learn.model.affiliate;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AffiliateRequestProductDto(
        @JsonProperty("product_code")
        List<String> productCode
) {
}
