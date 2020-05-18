package com.github.yanzord.salesdataanalysis.model;

import java.util.List;

public class Sale {
    private String saleId;
    private List<Item> items;
    private String salesmanName;

    public Sale(String saleId, List<Item> items, String salesmanName) {
        this.saleId = saleId;
        this.items = items;
        this.salesmanName = salesmanName;
    }

    public String getSaleId() {
        return saleId;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getSalesmanName() {
        return salesmanName;
    }
}
