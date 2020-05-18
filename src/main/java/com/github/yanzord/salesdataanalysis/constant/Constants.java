package com.github.yanzord.salesdataanalysis.constant;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final String HOME_PATH = System.getenv("HOME");
    public static final Path INPUT_FILE_PATH = Paths.get(HOME_PATH.concat("/data/in/"));
    public static final Path OUTPUT_FILE_PATH = Paths.get(HOME_PATH.concat("/data/out/"));
    public static final String DATA_SEPARATOR = "ç";
    public static final int NUMBER_OF_SEPARATORS = 3;
    public static final String ITEMS_SEPARATOR = ",";
    public static final String ITEMS_DATA_SEPARATOR = "-";
}
