package org.learn.model.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record MerchantRequestDto(
        @NotBlank
        @JsonProperty("phone_number")
        String phoneNumber,
        String address
) {
}
