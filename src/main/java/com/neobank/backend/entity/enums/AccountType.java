package com.neobank.backend.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
public enum AccountType {
	
	CHECKING,
    SAVINGS;

    @JsonCreator
    public static AccountType fromString(String value) {
        if (value == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown AccountType: " + value));
    }

}
