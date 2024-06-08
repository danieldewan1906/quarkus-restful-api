package org.learn.model.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MerchantDto(
        String id,
        @JsonProperty("user_id")
        Long userId,
        String address,
        @JsonProperty("phone_number")
        String phoneNumber
) {
}
