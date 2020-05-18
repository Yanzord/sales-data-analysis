package com.github.yanzord.salesdataanalysis.service;

import com.github.yanzord.salesdataanalysis.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.yanzord.salesdataanalysis.constant.Constants.OUTPUT_TEXT;
import static org.junit.Assert.assertEquals;

public class AnalyzerTest {
    private Analyzer analyzer = new Analyzer();

    @Test
    public void shouldAnalyzeFile() {
        List<String> fileLines = new ArrayList<>();
        fileLines.add("001ç1234567891234çLourençoç40000.99");
        fileLines.add("001ç1234567891234çPauloç50000");
        fileLines.add("002ç2345675434544345çAndrei da SilvaçRural");
        fileLines.add("002ç2345675434544123çJuarez da CostaçRural");
        fileLines.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çLourenço");
        fileLines.add("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");


        String expected = String.format(OUTPUT_TEXT, 2, 2, "10", "Paulo");

        String actual = analyzer.analyzeFile(fileLines);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnDefaultValueWhenFileIsEmpty() {
        List<String> fileLines =  new ArrayList<>();

        String expected = String.format(OUTPUT_TEXT, 0, 0, "none", "none");

        String actual = analyzer.analyzeFile(fileLines);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnDefaultValueWhenSeparatorIsInvalid() {
        List<String> fileLines =  new ArrayList<>();
        fileLines.add("001ç1234567891234çLourençoç40000.99");
        fileLines.add("002ç2345675434544123çJuarez da CostaçRural");
        fileLines.add("003;08;[1-34-10,2-33-1.50,3-40-0.10];Paulo");

        String expected = String.format(OUTPUT_TEXT, 0, 0, "none", "none");

        String actual = analyzer.analyzeFile(fileLines);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnDefaultValueWhenLineIdentifierIsInvalid() {
        List<String> fileLines =  new ArrayList<>();
        fileLines.add("001ç1234567891234çLourençoç40000.99");
        fileLines.add("002ç2345675434544123çJuarez da CostaçRural");
        fileLines.add("004ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");

        String expected = String.format(OUTPUT_TEXT, 0, 0, "none", "none");

        String actual = analyzer.analyzeFile(fileLines);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnTheMostExpensiveSaleId() {
        List<Salesman> salesmen = new ArrayList<>();
        salesmen.add(new Salesman("1234567891234", "Lourenço", 40000.99));
        salesmen.add(new Salesman("1234567891123", "Paulo", 50000d));

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("2345675434544345", "Andrei da Silva", "Rural"));
        customers.add(new Customer("2345675434544123", "Maria", "Casa"));

        List<Item> bestSaleItems = new ArrayList<>();
        bestSaleItems.add(new Item("1", 10, 100d));
        bestSaleItems.add(new Item("2", 30, 2.5));
        bestSaleItems.add(new Item("3", 40, 3.1));

        List<Item> worstSaleItems = new ArrayList<>();
        worstSaleItems.add(new Item("4", 1, 3d));
        worstSaleItems.add(new Item("5", 2, 2.5));
        worstSaleItems.add(new Item("6", 3, 3.1));

        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale("10", bestSaleItems, "Lourenço"));
        sales.add(new Sale("5", worstSaleItems, "Paulo"));

        SalesData fakeSalesData = new SalesData(salesmen, customers, sales);

        String expected = "10";
        String actual = analyzer.getMostExpensiveSaleId(fakeSalesData);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnTheWorstSalesman() {
        List<Salesman> salesmen = new ArrayList<>();
        salesmen.add(new Salesman("1234567891234", "Lourenço", 40000.99));
        salesmen.add(new Salesman("1234567891123", "Paulo", 50000d));

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("2345675434544345", "Andrei da Silva", "Rural"));
        customers.add(new Customer("2345675434544123", "Maria", "Casa"));

        List<Item> bestSaleItems = new ArrayList<>();
        bestSaleItems.add(new Item("1", 10, 100d));
        bestSaleItems.add(new Item("2", 30, 2.5));
        bestSaleItems.add(new Item("3", 40, 3.1));

        List<Item> worstSaleItems = new ArrayList<>();
        worstSaleItems.add(new Item("4", 1, 3d));
        worstSaleItems.add(new Item("5", 2, 2.5));
        worstSaleItems.add(new Item("6", 3, 3.1));

        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale("10", bestSaleItems, "Lourenço"));
        sales.add(new Sale("5", worstSaleItems, "Paulo"));

        SalesData fakeSalesData = new SalesData(salesmen, customers, sales);

        String expected = "Paulo";
        String actual = analyzer.getWorstSalesman(fakeSalesData);

        assertEquals(expected, actual);
    }
}
