package com.example.checkrecognzer.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product_items")
public class ProductItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Column(name = "quantity", nullable = false)
    private double quantity;
    @Column(name = "price_per_unit", nullable = false)
    private double pricePerUnit;
    @Column(name = "total_price", nullable = false)
    private double totalPrice;
    @Column(name = "discount")
    private double discount;
}