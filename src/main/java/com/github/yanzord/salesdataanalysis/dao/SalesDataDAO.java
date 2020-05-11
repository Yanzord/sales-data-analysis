package com.github.yanzord.salesdataanalysis.dao;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static com.github.yanzord.salesdataanalysis.constant.Constant.*;

public class SalesDataDAO {
    private static final String FILE_EXTENSION = ".dat";
    private Logger logger = Logger.getLogger(SalesDataDAO.class);

    public List<String> readFile(Path file) {
        logger.info("Reading File: " + file.toString());

        if (!Files.exists(INPUT_FILE_PATH)) {
            logger.error("Input directory doesn't exist.");
            throw new IllegalArgumentException();
        }

        if (!file.getFileName().toString().endsWith(FILE_EXTENSION)) {
            logger.error("Not a valid .dat file.");
            throw new IllegalArgumentException();
        }

        try {
            return Files.readAllLines(file);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void writeFile(String fileName, String content) {
        logger.info("Starting to write file: " + fileName);

        if (!Files.exists(OUTPUT_FILE_PATH)) {
            logger.error("Output directory doesn't exist.");
            throw new IllegalArgumentException();
        }

        try {
            Files.write(OUTPUT_FILE_PATH.resolve(Paths.get(fileName)), content.getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
