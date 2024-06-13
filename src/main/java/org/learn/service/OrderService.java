package org.learn.service;

import org.learn.model.orders.OrderDto;
import org.learn.model.orders.OrderRequestDto;

public interface OrderService {

    OrderDto checkout(String email, OrderRequestDto request);
}
