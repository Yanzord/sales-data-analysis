package com.github.yanzord.salesdataanalysis.model;

import java.util.List;

public class Sale {
    private int saleId;
    private List<Item> items;
    private Salesman salesman;

    public Sale(int saleId, List<Item> items, Salesman salesman) {
        this.saleId = saleId;
        this.items = items;
        this.salesman = salesman;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }
}
