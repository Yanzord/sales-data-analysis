package com.github.yanzord.salesdataanalysis.service;

import com.github.yanzord.salesdataanalysis.dao.FileDAO;
import com.github.yanzord.salesdataanalysis.exception.DirectoryNotFoundException;
import com.github.yanzord.salesdataanalysis.exception.InvalidFileExtensionException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.yanzord.salesdataanalysis.constant.Constants.INPUT_FILE_PATH;
import static com.github.yanzord.salesdataanalysis.constant.Constants.THREAD_EXECUTION_TIME;

public class Worker implements Runnable {
    private FileDAO fileDAO;
    private Analyzer analyzer;
    private static final Logger logger = Logger.getLogger(Worker.class);

    public Worker() {
        this.fileDAO = new FileDAO();
        this.analyzer = new Analyzer();
    }

    @Override
    public void run() {
        initialize();
        while(true) {
            update();

            try {
                Thread.sleep(THREAD_EXECUTION_TIME);
            } catch (InterruptedException e) {
                logger.error("An error has occurred: " + e.getMessage());
            }
        }
    }

    public void initialize() {
        try {
            List<Path> files = Files.walk(INPUT_FILE_PATH).filter(Files::isRegularFile).collect(Collectors.toList());

            for (Path file : files) {
                if (Files.isRegularFile(file)) {
                    Path fileName = file.getFileName();
                    processFile(fileName);
                }
            }
        } catch (IOException | IllegalArgumentException | InvalidFileExtensionException | DirectoryNotFoundException e) {
            logger.error("An error has occurred: " + e.getMessage());
        }
    }

    public void update() {
        try {
            logger.info("Waiting for files...");
            WatchService watchService = FileSystems.getDefault().newWatchService();

            INPUT_FILE_PATH.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    logger.info("Processing file found: " + event.context().toString());
                    Path fileName = Paths.get(event.context().toString());
                    processFile(fileName);
                }

                key.reset();
            }
        } catch (IOException | InterruptedException | InvalidFileExtensionException | DirectoryNotFoundException e) {
            logger.error("An error has occurred: " + e.getMessage());
        }
    }

    public void processFile(Path fileName) throws InvalidFileExtensionException, DirectoryNotFoundException {
        List<String> fileLines = fileDAO.readFile(fileName);
        String result = analyzer.analyzeFile(fileLines);
        fileDAO.writeFile(fileName, result);
    }
}
