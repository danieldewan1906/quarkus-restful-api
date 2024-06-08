package org.learn.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ProductRequestDto(
        @NotBlank
        String code,

        @NotBlank
        @JsonProperty("product_name")
        String productName,

        @JsonProperty("retail_price")
        Long retailPrice,

        @JsonProperty("stocks")
        Integer stocks
) {
}
