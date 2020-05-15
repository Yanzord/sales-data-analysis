package com.github.yanzord.salesdataanalysis.dao;

import com.github.yanzord.salesdataanalysis.exception.DirectoryNotFoundException;
import com.github.yanzord.salesdataanalysis.exception.InvalidFileExtensionException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static com.github.yanzord.salesdataanalysis.constant.Constants.*;

public class FileDAO {
    private static final String FILE_EXTENSION = ".dat";
    private Logger logger = Logger.getLogger(FileDAO.class);

    public List<String> readFile(Path file) {
        logger.info("Reading File: " + file.toString());

        if (!Files.exists(INPUT_FILE_PATH)) {
            throw new DirectoryNotFoundException("Input directory doesn't exist.");
        }

        if (!file.getFileName().toString().endsWith(FILE_EXTENSION)) {
            throw new InvalidFileExtensionException("Not a valid .dat file.");
        }

        try {
            return Files.readAllLines(INPUT_FILE_PATH.resolve(file));
        } catch (IOException e) {
            logger.error("An error has occurred while reading the file: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void writeFile(Path file, String content) {
        logger.info("Starting to write file: " + file.toString());

        if (!Files.exists(OUTPUT_FILE_PATH)) {
            throw new DirectoryNotFoundException("Output directory doesn't exist.");
        }

        try {
            Files.write(OUTPUT_FILE_PATH.resolve(file), content.getBytes());
        } catch (IOException e) {
            logger.error("An error has occurred while writing the file: " + e.getMessage());
        }
    }
}
