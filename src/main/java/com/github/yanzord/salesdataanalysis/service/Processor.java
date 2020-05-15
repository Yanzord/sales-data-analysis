package com.github.yanzord.salesdataanalysis.service;

import com.github.yanzord.salesdataanalysis.exception.InvalidFileException;
import com.github.yanzord.salesdataanalysis.exception.InvalidIdentifierException;
import com.github.yanzord.salesdataanalysis.exception.InvalidSeparatorException;
import com.github.yanzord.salesdataanalysis.model.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.yanzord.salesdataanalysis.constant.Constants.*;

public class Processor {
    private Logger logger = Logger.getLogger(Processor.class);

    public SalesData processFile(List<String> fileLines) throws InvalidFileException {
        if (!isValidFile(fileLines)) {
            throw new InvalidFileException();
        }

        List<Salesman> salesmen = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        List<Sale> sales = new ArrayList<>();

        fileLines.forEach(line -> {
            String lineIdentifier = line.substring(0, 3);

            switch (LineIdentifier.valueOfIdentifier(lineIdentifier)) {
                case SALESMAN: {
                    salesmen.add(processSalesman(line));
                    break;
                }
                case CUSTOMER: {
                    customers.add(processCustomer(line));
                    break;
                }
                case SALE: {
                    sales.add(processSale(line));
                    break;
                }
                default: {
                    throw new InvalidIdentifierException(INVALID_IDENTIFIER_MESSAGE);
                }
            }
        });

        return new SalesData(salesmen, customers, sales);
    }

    public Salesman processSalesman(String line) {
        logger.info("Processing salesman from line: " + line);

        List<String> splittedLine = Arrays.asList(line.split(DATA_SEPARATOR));

        if (splittedLine.size() > NUMBER_OF_SEPARATORS + 1) {
            int indexOfLastElement = splittedLine.size() - 1;

            String cpf = splittedLine.get(1);
            Double salary = Double.valueOf(splittedLine.get(indexOfLastElement));

            List<String> splittedName = splittedLine.subList(2, indexOfLastElement);
            String name = String.join(DATA_SEPARATOR, splittedName);

            return new Salesman(cpf, name, salary);
        }

        if (splittedLine.size() == NUMBER_OF_SEPARATORS + 1) {
            String cpf = splittedLine.get(1);
            String name = splittedLine.get(2);
            Double salary = Double.valueOf(splittedLine.get(3));

            return new Salesman(cpf, name, salary);
        }

        throw new InvalidSeparatorException(INVALID_SEPARATOR_MESSAGE);
    }

    public Customer processCustomer(String line) {
        logger.info("Processing customer from line: " + line);

        List<String> splittedLine = Arrays.asList(line.split(DATA_SEPARATOR));

        if (splittedLine.size() > NUMBER_OF_SEPARATORS + 1) {
            int indexOfLastElement = splittedLine.size() - 1;

            String cnpj = splittedLine.get(1);
            String businessArea = splittedLine.get(indexOfLastElement);

            List<String> splittedName = splittedLine.subList(2, indexOfLastElement);
            String name = String.join(DATA_SEPARATOR, splittedName);

            return new Customer(cnpj, name, businessArea);
        }

        if (splittedLine.size() == NUMBER_OF_SEPARATORS + 1) {
            String cnpj = splittedLine.get(1);
            String name = splittedLine.get(2);
            String businessArea = splittedLine.get(3);

            return new Customer(cnpj, name, businessArea);
        }

        throw new InvalidSeparatorException(INVALID_SEPARATOR_MESSAGE);
    }

    public Sale processSale(String line) {
        logger.info("Processing sale from line: " + line);

        List<String> splittedLine = Arrays.asList(line.split(DATA_SEPARATOR));

        if (splittedLine.size() > NUMBER_OF_SEPARATORS + 1) {
            String saleId = splittedLine.get(1);
            String itemsData = splittedLine.get(2);

            List<String> itemsText = Arrays.asList(itemsData.substring(1, itemsData.length() - 1).split(ITEMS_SEPARATOR));
            List<Item> items = processItems(itemsText);

            List<String> splittedName = splittedLine.subList(3, splittedLine.size());
            String name = String.join(DATA_SEPARATOR, splittedName);

            return new Sale(saleId, items, name);
        }

        if (splittedLine.size() == NUMBER_OF_SEPARATORS + 1) {
            String saleId = splittedLine.get(1);
            String itemsData = splittedLine.get(2);

            List<String> itemsText = Arrays.asList(itemsData.substring(1, itemsData.length() - 1).split(ITEMS_SEPARATOR));
            List<Item> items = processItems(itemsText);

            String name = splittedLine.get(3);

            return new Sale(saleId, items, name);
        }

        throw new InvalidSeparatorException(INVALID_SEPARATOR_MESSAGE);
    }

    public List<Item> processItems(List<String> itemsText) {
        List<Item> items = new ArrayList<>();

        itemsText.forEach(itemText -> {
            String[] splittedText = itemText.split(ITEMS_DATA_SEPARATOR);

            String id = splittedText[0];
            Integer quantity = Integer.valueOf(splittedText[1]);
            Double price = Double.valueOf(splittedText[2]);

            items.add(new Item(id, quantity, price));
        });

        return items;
    }

    //TO-DO skipar a linha com problema e processar as demais
    public boolean isValidFile(List<String> fileLines) {
        logger.info("Beginning to validate file.");
        List<LineIdentifier> identifiers = Arrays.asList(LineIdentifier.values());

        for (String line : fileLines) {
            logger.info("Line " + line);
            String lineIdentifier = line.substring(0, 3);

            if (identifiers.stream().noneMatch(identifier -> identifier.getIdentifier().equals(lineIdentifier))) {
                logger.warn("Invalid file, reason: line identifier.");
                return false;
            }

            if (line.split(DATA_SEPARATOR).length < NUMBER_OF_SEPARATORS || !line.startsWith(DATA_SEPARATOR, 3)) {
                logger.warn("Invalid file, reason: separator.");
                return false;
            }
        }

        logger.info("Validated file.");
        return true;
    }
}