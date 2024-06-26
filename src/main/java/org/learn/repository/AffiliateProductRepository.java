package org.learn.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.learn.entity.Affiliate;
import org.learn.entity.AffiliateProducts;

import java.util.List;

@ApplicationScoped
public class AffiliateProductRepository implements PanacheRepositoryBase<AffiliateProducts, String> {

    public PanacheQuery<AffiliateProducts> getByReferralCode(String referralCode) {
        return find("referralCode", referralCode);
    }
}
