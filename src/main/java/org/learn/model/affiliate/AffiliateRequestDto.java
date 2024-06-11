package org.learn.model.affiliate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record AffiliateRequestDto(
        @NotBlank
        @JsonProperty("phone_number")
        String phoneNumber,
        String address
) {
}
