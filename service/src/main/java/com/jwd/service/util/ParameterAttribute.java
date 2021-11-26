package com.jwd.service.util;

import java.util.Arrays;
import java.util.List;

public class ParameterAttribute {
    public static final String PATTERN_EMAIL = "^[a-z0-9._-]{1,25}@[a-z0-9.-]{1,14}\\.[a-z]{2,6}$";
    public static final String PATTERN_LOGIN = "([A-Za-z#!^&_]{1,14}$)";
    public static final String PATTERN_CITY = "([A-Za-z]{1,14}$)";

    public static final List<String> availableSortByParameters = Arrays.asList("order_creation_date", "address", "service_type",
            "service_status", "description"),
            availableDirectionParameters = Arrays.asList("ASC", "DESC");
}
