package com.github.yanzord.salesdataanalysis.service;

import com.github.yanzord.salesdataanalysis.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ProcessorTest {
    private Processor processor = new Processor();
    private Double DELTA = 0.01;

    @Test
    public void shouldProcessFile() {
        List<String> fileLines = new ArrayList<>();
        fileLines.add("001ç1234567891234çLourençoç40000.99");
        fileLines.add("002ç2345675434544345çAndrei da SilvaçRural");
        fileLines.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çLourenço");

        List<Salesman> salesmen = new ArrayList<>();
        salesmen.add(new Salesman("1234567891234", "Lourenço", 40000.99));

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("2345675434544345", "Andrei da Silva", "Rural"));

        List<Sale> sales = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        items.add(new Item("1", 10, 100d));
        items.add(new Item("2", 30, 2.5));
        items.add(new Item("3", 40, 3.1));
        sales.add(new Sale("10", items, "Lourenço"));

        SalesData expected = new SalesData(salesmen, customers, sales);

        Optional<SalesData> salesDataOptional = processor.process(fileLines);
        SalesData actual = salesDataOptional.get();

        assertEquals(expected.getCustomers().get(0).getName(), actual.getCustomers().get(0).getName());
        assertEquals(expected.getCustomers().get(0).getCnpj(), actual.getCustomers().get(0).getCnpj());
        assertEquals(expected.getSales().get(0).getSaleId(), actual.getSales().get(0).getSaleId());
        assertEquals(expected.getSales().get(0).getSalesmanName(), actual.getSales().get(0).getSalesmanName());
        assertEquals(expected.getSalesmen().get(0).getName(), actual.getSalesmen().get(0).getName());
        assertEquals(expected.getSalesmen().get(0).getCpf(), actual.getSalesmen().get(0).getCpf());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenFileIsEmpty() {
        List<String> fileLines = new ArrayList<>();

        Optional<SalesData> salesDataOptional = processor.process(fileLines);

        assertFalse(salesDataOptional.isPresent());
    }

    @Test
    public void shouldProcessSalesman() {
        String salesmanLine = "001ç3245678865434çLourençoç40000.99";
        Salesman expected = new Salesman("3245678865434", "Lourenço", 40000.99);
        Salesman actual = processor.processSalesman(salesmanLine);

        assertEquals(expected.getCpf(), actual.getCpf());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getSalary(), actual.getSalary(), DELTA);
    }

    @Test
    public void shouldProcessCustomer() {
        String customerLine = "002ç2345675434544345çAndrei da SilvaçRural";
        Customer expected = new Customer("2345675434544345", "Andrei da Silva", "Rural");
        Customer actual = processor.processCustomer(customerLine);

        assertEquals(expected.getCnpj(), actual.getCnpj());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getBusinessArea(), actual.getBusinessArea());
    }

    @Test
    public void shouldProcessSale() {
        String saleLine = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çLourenço";
        List<Item> items = new ArrayList<>();
        items.add(new Item("1", 10, 100d));
        items.add(new Item("2", 30, 2.5));
        items.add(new Item("3", 40, 3.1));
        Sale expected =  new Sale("10", items, "Lourenço");
        Sale actual = processor.processSale(saleLine);

        assertEquals(expected.getSaleId(), actual.getSaleId());
        assertEquals(expected.getSalesmanName(), actual.getSalesmanName());
        assertEquals(expected.getItems().get(0).getId(), actual.getItems().get(0).getId());
        assertEquals(expected.getItems().get(1).getId(), actual.getItems().get(1).getId());
        assertEquals(expected.getItems().get(2).getId(), actual.getItems().get(2).getId());
    }

    @Test
    public void shouldProcessItem() {
        List<String> itemsText = new ArrayList<>();
        itemsText.add("1-10-100");
        itemsText.add("2-30-2.50");
        itemsText.add("3-40-3.10");

        List<Item> expected = new ArrayList<>();
        expected.add(new Item("1", 10, 100d));
        expected.add(new Item("2", 30, 2.5));
        expected.add(new Item("3", 40, 3.1));

        List<Item> actual = processor.processItems(itemsText);

        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(1).getId(), actual.get(1).getId());
        assertEquals(expected.get(2).getId(), actual.get(2).getId());
    }

    @Test
    public void shouldValidateFileOk() {
        List<String> fileLines = new ArrayList<>();
        fileLines.add("001ç1234567891234çLourençoç50000");
        fileLines.add("002ç2345675434544345çAndrei da SilvaçRural");
        fileLines.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çLourenço");

        boolean actual = processor.isValidFile(fileLines);

        assertTrue(actual);
    }

    @Test
    public void shouldValidateFileNotOkWhenSeparatorIsInvalid() {
        List<String> fileLines = new ArrayList<>();
        fileLines.add("001ç1234567891234çLourençoç50000");
        fileLines.add("002ç2345675434544345çAndrei da SilvaçRural");
        fileLines.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çLourenço");
        fileLines.add("003;08;[1-34-10,2-33-1.50,3-40-0.10];Paulo");

        boolean actual = processor.isValidFile(fileLines);

        assertFalse(actual);
    }

    @Test
    public void shouldValidateFileNotOkWhenIdentifierIsInvalid() {
        List<String> fileLines = new ArrayList<>();
        fileLines.add("001ç1234567891234çLourençoç50000");
        fileLines.add("002ç2345675434544345çAndrei da SilvaçRural");
        fileLines.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çLourenço");
        fileLines.add("004ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");

        boolean actual = processor.isValidFile(fileLines);

        assertFalse(actual);
    }
}
