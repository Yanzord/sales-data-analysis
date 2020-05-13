package com.github.yanzord.salesdataanalysis.exception;


import static com.github.yanzord.salesdataanalysis.constant.Constants.SEPARATOR;

public class InvalidSeparatorException extends RuntimeException {
    public InvalidSeparatorException() {
        super("Invalid separator, expected: " + SEPARATOR);
    }
}
