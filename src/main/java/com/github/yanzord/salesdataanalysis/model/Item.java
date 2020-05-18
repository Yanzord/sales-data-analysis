package com.github.yanzord.salesdataanalysis.model;

public class Item {
    private String id;
    private Integer quantity;
    private Double price;

    public Item(String id, Integer quantity, Double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }
}
