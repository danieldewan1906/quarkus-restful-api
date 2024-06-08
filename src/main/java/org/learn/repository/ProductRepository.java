package org.learn.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.learn.entity.Merchant;
import org.learn.entity.Product;

@ApplicationScoped
public class ProductRepository implements PanacheRepositoryBase<Product, String> {

    public PanacheQuery<Product> findByMerchantId(Merchant merchant) {
        return find("merchant", merchant);
    }
}
