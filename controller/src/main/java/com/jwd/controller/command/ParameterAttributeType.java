package com.jwd.controller.command;

public final class ParameterAttributeType {

    public static final String CLIENT = "client";
    public static final String USER_ID = "userId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String CITY = "city";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirmPassword";
    public static final String GENDER = "gender";
    public static final String USER_ROLE = "userRole";

    public static final String ENCODING_INIT_PARAM_NAME = "encoding";
    public static final String FILTERABLE_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String DEFAULT_ENCODING = "UTF-8";

    public static final String CHANGE_LANGUAGE = "change_language";
    public static final String LANGUAGE = "language";

    public static final String ORDER = "order";
    public static final String ID_SERVICE = "idService";
    public static final String SERVICE_DESCRIPTION = "description";
    public static final String SERVICE_ADDRESS = "address";
    public static final String PRICE = "price";
    public static final String CREATION_DATE = "creationDate";
    public static final String COMPLETION_DATE = "completionDate";
    public static final String SERVICE_TYPE = "service_type";
    public static final String SERVICE_STATUS = "status";
    public static final String IN_PROCESS = "in_process";

    public static final String WORK_ACTION = "work_action";
    public static final String ID_WORKER = "idWorker";
    public static final String ID_CLIENT = "idClient";

    public static final String CURRENT_PAGE = "currentPage";
    public static final String PAGE_LIMIT = "pageLimit";
    public static final String PAGEABLE = "pageable";

    public static final String ERROR = "error";
    public static final String LAST_COMMAND = "last_command";
    public static final String COMMAND = "command";
    public static final String MESSAGE="message";
    public static final String ALL_SERVICES = "services";
    public static final String RESPONSES = "responses";
    public static final String SORT_BY = "sort_by";
    public static final String SELECTED_SERVICE_TYPE = "selected_service_type";
    public static final String SELECTED_SORT_BY_PARAMETER= "selected_sort_by_parameter";
    public static final String SELECTED_DIRECTION_PARAMETER= "selected_direction_parameter";
    public static final String DIRECTION = "direction";

    public static final String SHOW_ORDERS_BY_SERVICE_TYPE  = "show_orders_by_service_type";
    public static final String SHOW_SERVICE_ALL  = "show_all_services";
    public static final String UPDATE_USER = "update_user";
    public static final String FIND_USER_INFORMATION =  "find_user_information";
    public static final String SHOW_USER_ORDERS =  "show_user_orders";
    public final static String ADD_SERVICE_ORDER = "addService";
    public final static String SHOW_USER_ORDER = "showUserOrder";
    public final static String FIND_CLIENT_RESPONSE = "find_client_response";
    public final static String FIND_WORKER_RESPONSE = "find_worker_response";

    private ParameterAttributeType() {
    }
}
