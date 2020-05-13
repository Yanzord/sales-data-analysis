package com.github.yanzord.salesdataanalysis.service;

import com.github.yanzord.salesdataanalysis.dao.SalesDataDAO;
import com.github.yanzord.salesdataanalysis.exception.InvalidIdentifierException;
import com.github.yanzord.salesdataanalysis.exception.InvalidSeparatorException;
import com.github.yanzord.salesdataanalysis.model.Customer;
import com.github.yanzord.salesdataanalysis.model.Sale;
import com.github.yanzord.salesdataanalysis.model.SalesData;
import com.github.yanzord.salesdataanalysis.model.Salesman;
import org.apache.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.yanzord.salesdataanalysis.constant.Constants.SEPARATOR;

public class SalesDataProcessor {
    private static final int NUMBER_OF_SEPARATORS = 3;
    private SalesDataDAO salesDataDAO;
    private Logger logger = Logger.getLogger(SalesDataProcessor.class);

    public SalesDataProcessor() {
        this.salesDataDAO = new SalesDataDAO();
    }

    public SalesData processFile(Path file) {
        List<String> fileLines = salesDataDAO.readFile(file);

        validateFile(fileLines);

        List<Salesman> salesmen = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        List<Sale> sales = new ArrayList<>();

        fileLines.forEach(line -> {
            String lineIdentifier = line.substring(0, 3);

            if (lineIdentifier.equals(LineIdentifier.SALESMAN.getIdentifier())) {
                salesmen.add(processSalesmen(line));
            }

            if (lineIdentifier.equals(LineIdentifier.CUSTOMER.getIdentifier())) {
                customers.add(processCustomer(line));
            }

            if (lineIdentifier.equals(LineIdentifier.SALE.getIdentifier())) {
                sales.add(processSale(line));
            }
        });

        return new SalesData(salesmen, customers, sales);
    }

    private Sale processSale(String line) {
        return null;
    }

    private Customer processCustomer(String line) {
        return null;
    }

    private Salesman processSalesmen(String line) {
        return null;
    }

    //TO-DO skipar a linha com problema e processar as demais
    private void validateFile(List<String> fileLines) {
        logger.info("Beginning to validade file.");
        List<LineIdentifier> identifiers = Arrays.asList(LineIdentifier.values());

        fileLines.forEach(line -> {
            logger.info("Line " + line);
            String lineIdentifier = line.substring(0, 3);

            if (identifiers.stream().noneMatch(identifier -> identifier.getIdentifier().equals(lineIdentifier))) {
                throw new InvalidIdentifierException();
            }

            if (line.split(SEPARATOR).length < NUMBER_OF_SEPARATORS || line.startsWith(SEPARATOR, 3)) {
                throw new InvalidSeparatorException();
            }
        });

        logger.info("lines validated.");
    }
}
