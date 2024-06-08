package org.learn.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.learn.entity.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public PanacheQuery<User> findByEmail(String email) {
        return find("email", email);
    }
    public PanacheQuery<User> findByToken(String token) {
        return find("token", token);
    }
}
