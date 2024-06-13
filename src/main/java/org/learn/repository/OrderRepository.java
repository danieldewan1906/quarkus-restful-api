package org.learn.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.learn.entity.Order;

@ApplicationScoped
public class OrderRepository implements PanacheRepositoryBase<Order, String> {
}
