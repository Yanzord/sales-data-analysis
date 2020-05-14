package com.github.yanzord.salesdataanalysis.service;

import com.github.yanzord.salesdataanalysis.model.Sale;
import com.github.yanzord.salesdataanalysis.model.SalesData;
import com.github.yanzord.salesdataanalysis.model.Salesman;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesDataAnalyzer {
    private SalesDataProcessor salesDataProcessor;

    public SalesDataAnalyzer() {
        this.salesDataProcessor = new SalesDataProcessor();
    }

    public String analyzeFile(Path file) {
        SalesData salesData = salesDataProcessor.processFile(file);

        Integer customerQuantity = salesData.getCustomers().size();
        Integer salesmenQuantity = salesData.getSalesmen().size();
        String mostExpensiveSaleId = getMostExpensiveSaleId(salesData);
        String worstSalesman = getWorstSalesman(salesData);


        return "Customer quantity: " + customerQuantity + "\n" +
                "Salesmen quantity: " + salesmenQuantity + "\n" +
                "Most expensive sale id: " + mostExpensiveSaleId + "\n" +
                "Worst salesman: " + worstSalesman;
    }

    private String getMostExpensiveSaleId(SalesData salesData) {
        List<Sale> sales = salesData.getSales();
        Map<String, Double> salePriceRank = new HashMap<>();

        sales.forEach(sale -> {
            String saleId = sale.getSaleId();

            Double totalSalePrice = sale.getItems()
                    .stream()
                    .map(item -> item.getPrice() * item.getQuantity())
                    .reduce(0.0, Double::sum);

            salePriceRank.put(saleId, totalSalePrice);
        });

        return salePriceRank.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .iterator()
                .next()
                .getKey();
    }

    private String getWorstSalesman(SalesData salesData) {
        List<Sale> sales = salesData.getSales();
        List<Salesman> salesmen = salesData.getSalesmen();

        Map<String, Double> salesmanSalesPriceRank = new HashMap<>();

        salesmen.forEach(salesman -> {
            Double saleTotalPrice = sales.stream()
                    .filter(sale -> sale.getSalesmanName().equals(salesman.getName()))
                    .map(sale -> sale.getItems().stream()
                            .map(item -> item.getPrice() * item.getQuantity())
                            .reduce(0.0, Double::sum))
                    .findFirst()
                    .orElse(0.0);

            salesmanSalesPriceRank.put(salesman.getName(), saleTotalPrice);
        });

        return salesmanSalesPriceRank.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .iterator()
                .next()
                .getKey();
    }

}
