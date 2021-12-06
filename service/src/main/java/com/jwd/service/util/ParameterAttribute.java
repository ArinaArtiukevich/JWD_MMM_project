package com.jwd.service.util;

import java.util.Arrays;
import java.util.List;

public class ParameterAttribute {
    public static final String PATTERN_EMAIL = "^[a-z0-9._-]{1,25}@[a-z0-9.-]{1,14}\\.[a-z]{2,6}$",
            PATTERN_LOGIN = "([A-Za-z#!^&_]{1,14}$)",
            PATTERN_STRING = "([A-Za-z]{1,14}$)",
            PATTERN_ORDER_STRING = "([A-Za-z/\\s]{1,20}$)",
            ALL_ORDERS  = "ALL";

    public static final List<String> availableSortByParameters = Arrays.asList("order_creation_date", "address", "service_type",
            "service_status", "description"),
            availableDirectionParameters = Arrays.asList("ASC", "DESC"),
            availableServiceTypeString = Arrays.asList("ALL", "ELECTRICAL", "GAS", "ROOFING", "PAINTING", "PLUMBING");
}
