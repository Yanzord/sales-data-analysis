package com.github.yanzord.salesdataanalysis.service;

public enum LineIdentifier {
    SALESMAN("001"),
    CUSTOMER("002"),
    SALE("003");

    private String identifier;

    public String getIdentifier() {
        return this.identifier;
    }

    LineIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
