package org.learn.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.learn.entity.Affiliate;
import org.learn.entity.User;

@ApplicationScoped
public class AffiliateRepository implements PanacheRepositoryBase<Affiliate, String> {

    public PanacheQuery<Affiliate> findByUserId(User user) {
        return find("user", user);
    }
}
