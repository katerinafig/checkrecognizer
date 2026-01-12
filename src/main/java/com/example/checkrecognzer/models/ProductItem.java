package com.example.checkrecognzer.models;

import lombok.Data;

@Data
public class ProductItem {
    private String name;
    private double quantity;
    private double pricePerUnit;
    private double totalPrice;
    private double discount;

}