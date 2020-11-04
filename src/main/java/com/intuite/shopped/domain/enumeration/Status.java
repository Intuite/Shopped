package com.intuite.shopped.domain.enumeration;

/**
 * The Status enumeration.
 */
public enum Status {
    ACTIVE("active"),
    INACTIVE("inactive"),
    BLOCKED("blocked"),
    PENDING("pending");

    private final String value;


    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
