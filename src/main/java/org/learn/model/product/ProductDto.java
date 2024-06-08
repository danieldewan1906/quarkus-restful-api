package org.learn.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record ProductDto(
        String code,

        String productName,

        Long retailPrice,

        Integer stocks,

        Boolean isActive,

        String linkProduct,

        Date createdAt
) {
}
