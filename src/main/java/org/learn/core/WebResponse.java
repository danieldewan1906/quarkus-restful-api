package org.learn.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebResponse<T> {

    private Integer status;
    private String message;
    private T data;
}
