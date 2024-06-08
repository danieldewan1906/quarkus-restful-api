package org.learn.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.learn.entity.Merchant;
import org.learn.entity.User;

@ApplicationScoped
public class MerchantRepository implements PanacheRepositoryBase<Merchant, String> {

    public PanacheQuery<Merchant> findByUserId(User user) {
        return find("user", user);
    }
}
