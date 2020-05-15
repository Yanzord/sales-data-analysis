package com.github.yanzord.salesdataanalysis.service;

import com.github.yanzord.salesdataanalysis.exception.InvalidSeparatorException;
import com.github.yanzord.salesdataanalysis.model.Customer;
import com.github.yanzord.salesdataanalysis.model.Salesman;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProcessorTest {
    private Processor processor = new Processor();
    private Double DELTA = 0.01;


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

    @Test
    public void shouldProcessSalesman() {
        String salesmanLine = "001ç3245678865434çLourençoç40000.99";
        Salesman expected = new Salesman("3245678865434", "Lourenço", 40000.99);
        Salesman actual = processor.processSalesman(salesmanLine);

        assertEquals(expected.getCpf(), actual.getCpf());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getSalary(), actual.getSalary(), DELTA);
    }

    @Test(expected = InvalidSeparatorException.class)
    public void shouldNotProcessSalesmanWhenTheNumberOfSeparatorsIsInvalid() {
        String salesmanLine = "001ç3245678865434çPaulo";

        processor.processSalesman(salesmanLine);
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

    @Test(expected = InvalidSeparatorException.class)
    public void shouldNotProcessCustomerhenTheNumberOfSeparatorsIsInvalid() {
        String customerLine = "002ç2345675434544345çAndrei da Silva";

        processor.processCustomer(customerLine);
    }
}
