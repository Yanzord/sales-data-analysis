package com.github.yanzord.salesdataanalysis;

import com.github.yanzord.salesdataanalysis.dao.SalesDataDAO;
import com.github.yanzord.salesdataanalysis.service.SalesDataAnalyzer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        SalesDataAnalyzer salesDataAnalyzer = new SalesDataAnalyzer();
        SalesDataDAO salesDataDAO = SalesDataDAO.getINSTANCE();

        Path file = Paths.get("test.dat");

        String result = salesDataAnalyzer.analyzeFile(file);
        salesDataDAO.writeFile(file, result);
    }
}
