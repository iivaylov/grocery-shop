package com.groceryshop.groceryshop.models;

import com.groceryshop.groceryshop.models.enums.DealEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deals")
public class DealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_id")
    private int id;

    @Column(name = "deal_enum", unique = true)
    @Enumerated(EnumType.STRING)
    private DealEnum dealEnum;
}
