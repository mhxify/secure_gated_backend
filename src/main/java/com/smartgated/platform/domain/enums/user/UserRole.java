package com.smartgated.platform.domain.enums.user;

public enum UserRole {
    ADMIN("ADMIN"),
    GUEST("GUEST"),
    RESIDENT("RESIDENT"),
    SECURITY_GUARD("SECURITY_GUARD"),
    STAFF("STAFF");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
