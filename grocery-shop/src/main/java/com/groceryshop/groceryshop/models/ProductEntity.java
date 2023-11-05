package com.groceryshop.groceryshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "price")
    private int price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "deals_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "deal_id")
    )
    private List<DealEntity> dealEntity;
}
