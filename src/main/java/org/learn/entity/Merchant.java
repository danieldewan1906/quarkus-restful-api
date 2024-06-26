package org.learn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @OneToOne
    @JoinColumn(columnDefinition = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "phone_number", length = 13, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "is_request_admin", nullable = false)
    private Boolean isRequestAdmin = true;

    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;
}
