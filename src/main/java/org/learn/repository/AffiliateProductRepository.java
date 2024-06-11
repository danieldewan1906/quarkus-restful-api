package org.learn.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.learn.entity.AffiliateProducts;

@ApplicationScoped
public class AffiliateProductRepository implements PanacheRepositoryBase<AffiliateProducts, String> {
}
