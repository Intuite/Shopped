package com.intuite.shopped.domain.enumeration;

/**
 * The Status enumeration.
 */
public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    BLOCKED("Blocked"),
    PENDING("Pending");

    private final String value;


    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
