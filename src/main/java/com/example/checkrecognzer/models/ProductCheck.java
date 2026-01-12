package com.example.checkrecognzer.models;

import lombok.Data;

import java.util.List;

@Data
public class ProductCheck {
    private List<ProductItem> productItems;
    private String storeName;
    private double totalPrice;
    private double discount;
}