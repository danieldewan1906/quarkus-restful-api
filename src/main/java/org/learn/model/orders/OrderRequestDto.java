package org.learn.model.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record OrderRequestDto(
        @JsonProperty("product_code")
        @NotBlank
        String productCode,
        @JsonProperty("quantity")
        @NotBlank
        Integer qty,
        @NotBlank
        Long amount,
        String url
) {
}
