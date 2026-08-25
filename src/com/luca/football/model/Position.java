package com.luca.football.model;

public enum Position {
    GOALKEEPER("GK"), DEFENDER("DF"), MIDFIELDER("MF"), FORWARD("FW");
    private final String shortCode;

    Position(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }
    public static Position fromShortCode(String shortCode) {
        for (Position pos : values()) {
            if (pos.shortCode.equalsIgnoreCase(shortCode)) {
                return pos;
            }
        }
        throw new IllegalArgumentException("Unknown position short code: " + shortCode);
    }
}
