package org.learn.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.learn.entity.UserReferral;

@ApplicationScoped
public class UserReferralRepository implements PanacheRepositoryBase<UserReferral, String> {
}
