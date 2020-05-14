package com.github.yanzord.salesdataanalysis.constant;

import com.github.yanzord.salesdataanalysis.service.LineIdentifier;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Constants {
    public static final String HOME_PATH = System.getenv("HOME");
    public static final Path INPUT_FILE_PATH = Paths.get(HOME_PATH.concat("/data/in/"));
    public static final Path OUTPUT_FILE_PATH = Paths.get(HOME_PATH.concat("/data/out/"));

    public static final String DATA_SEPARATOR = "ç";
    public static final int NUMBER_OF_SEPARATORS = 3;
    public static final String ITEMS_SEPARATOR = ",";
    public static final String ITEMS_DATA_SEPARATOR = "-";

    public static final String INVALID_IDENTIFIER_MESSAGE = "Invalid identifier, expected one of the followings: "
            + Arrays.toString(LineIdentifier.values());
    public static final String INVALID_SEPARATOR_MESSAGE = "Invalid separator, expected separator: " + DATA_SEPARATOR
            + "; minimum quantity required: " + NUMBER_OF_SEPARATORS;
}
