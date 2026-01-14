package com.example.checkrecognzer.models;

import lombok.Data;

@Data
public class ProductItem {
    private Long id;
    private Long version;
    private String name;
    private double quantity;
    private double pricePerUnit;
    private double totalPrice;
    private double discount;

}