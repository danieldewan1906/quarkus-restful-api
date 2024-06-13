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
@Entity(name = "history_user_affiliate")
public class HistoryUserAffiliate extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @ManyToOne
    @JoinColumn(columnDefinition = "order_no", referencedColumnName = "order_no")
    private Order order;

    @ManyToOne
    @JoinColumn(columnDefinition = "affiliate_id", referencedColumnName = "id")
    private Affiliate affiliate;

    @Column(name = "total_fee")
    private Double totalFee;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
