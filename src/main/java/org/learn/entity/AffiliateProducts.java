package org.learn.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "affiliate_products")
public class AffiliateProducts extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @ManyToOne
    @JoinColumn(columnDefinition = "affiliate_id", referencedColumnName = "id")
    private Affiliate affiliate;

    @Column(name = "is_valid_merchant", nullable = false)
    private Boolean isValidMerchant = false;

    @ManyToOne
    @JoinColumn(columnDefinition = "product_code", referencedColumnName = "code")
    private Product product;

    @Column(name = "link_affiliate", nullable = true, unique = true)
    private String linkAffiliate;

    @Column(name = "referral_code", nullable = false, unique = true)
    private String referralCode;

    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;
}
