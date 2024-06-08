package org.learn.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends PanacheEntityBase implements Serializable {

    @Id
    @Column(name = "code", length = 6, nullable = false, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(columnDefinition = "merchant_id", referencedColumnName = "id")
    private Merchant merchant;

    @Column(name = "product_name", length = 255, nullable = false, unique = true)
    private String productName;

    @Column(name = "retail_price", nullable = false)
    private Long retailPrice = 0L;

    @Column(name = "stocks", nullable = false)
    private Integer stocks = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "link_product", nullable = false, unique = true)
    private String linkProduct;

    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;
}
