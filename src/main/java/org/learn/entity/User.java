package org.learn.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "role_user", length = 255, nullable = false)
    private String role;

    @Column(name = "email", length = 255, unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "is_affiliate")
    private Boolean isAffiliate = false;

    @Column(name = "is_merchant")
    private Boolean isMerchant = false;

    @Column(name = "token", length = 1000, nullable = true)
    private String token;
}
