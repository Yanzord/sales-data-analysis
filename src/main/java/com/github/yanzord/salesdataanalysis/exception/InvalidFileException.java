package com.github.yanzord.salesdataanalysis.exception;

    public class InvalidFileException extends Exception {
        public InvalidFileException() {
            super("File is invalid, please check your file structure.");
        }
}
