package org.learn.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends PanacheEntityBase implements Serializable {

    @Id
    @Column(name = "order_no", nullable = false, unique = true)
    private String orderNo;

    @ManyToOne
    @JoinColumn(columnDefinition = "product_code", referencedColumnName = "code")
    private Product product;

    @ManyToOne
    @JoinColumn(columnDefinition = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "order_amount", nullable = false)
    private Long amount;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
