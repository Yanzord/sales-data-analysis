package com.github.yanzord.salesdataanalysis.service;

import java.util.HashMap;
import java.util.Map;

public enum LineIdentifier {
    SALESMAN("001"),
    CUSTOMER("002"),
    SALE("003");

    private String identifier;

    private static final Map<String, LineIdentifier> BY_IDENTIFIER = new HashMap<>();

    static {
        for (LineIdentifier i: values()) {
            BY_IDENTIFIER.put(i.identifier, i);
        }
    }

    LineIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public static LineIdentifier valueOfIdentifier(String identifier) {
        return BY_IDENTIFIER.get(identifier);
    }
}
