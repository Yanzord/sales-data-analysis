package com.github.yanzord.salesdataanalysis.constant;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constant {
    private static final String HOME_PATH = System.getenv("HOME");
    public static final Path INPUT_FILE_PATH = Paths.get(HOME_PATH.concat("/data/in/"));
    public static final Path OUTPUT_FILE_PATH = Paths.get(HOME_PATH.concat("/data/out/"));
}
