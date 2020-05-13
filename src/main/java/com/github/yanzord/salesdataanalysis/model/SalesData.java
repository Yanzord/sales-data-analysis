package com.github.yanzord.salesdataanalysis.model;

import java.util.List;

public class SalesData {
    private List<Customer> customers;
    private List<Sale> sales;
    private List<Salesman> salesmen;

    public SalesData(List<Salesman> salesmen, List<Customer> customers, List<Sale> sales) {
        this.salesmen = salesmen;
        this.customers = customers;
        this.sales = sales;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public List<Salesman> getSalesmen() {
        return salesmen;
    }
}
