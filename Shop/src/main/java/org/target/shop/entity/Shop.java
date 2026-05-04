package org.target.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "shop")
@Data
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerUserId; // seller user id

    private String name;

    private String description;

    private String logoUrl;

    private String status;
    // PENDING, ACTIVE, DISABLED

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}