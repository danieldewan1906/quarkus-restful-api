package org.learn.model.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.learn.model.product.ProductDto;
import org.learn.model.user.UserDto;

public record OrderDto(
        @JsonProperty("user_id")
        UserDto userId,
        ProductDto product,
        @JsonProperty("quantity")
        Integer qty,
        Long amount
) {
}
