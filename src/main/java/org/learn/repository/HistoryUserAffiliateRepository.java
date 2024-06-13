package org.learn.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.learn.entity.Affiliate;
import org.learn.entity.HistoryUserAffiliate;

import java.util.List;

@ApplicationScoped
public class HistoryUserAffiliateRepository implements PanacheRepositoryBase<HistoryUserAffiliate, String> {

    public List<HistoryUserAffiliate> getByAffiliate(Affiliate affiliate) {
        return list("affiliate", affiliate);
    }
}
