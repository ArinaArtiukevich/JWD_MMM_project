package com.jwd.controller.command;

public final class ParameterAttributeType {

    public static final String CLIENT = "client",
            USER = "user",
            USER_ID = "userId",
            FIRST_NAME = "firstName",
            LAST_NAME = "lastName",
            EMAIL = "email",
            CITY = "city",
            LOGIN = "login",
            PASSWORD = "password",
            CONFIRM_PASSWORD = "confirmPassword",
            GENDER = "gender",
            USER_ROLE = "userRole",

            ENCODING_INIT_PARAM_NAME = "encoding",
            FILTERABLE_CONTENT_TYPE = "application/x-www-form-urlencoded",
            DEFAULT_ENCODING = "UTF-8",

            CHANGE_LANGUAGE = "change_language",
            LANGUAGE = "language",

            ORDER = "order",
            ID_SERVICE = "idService",
            SERVICE_DESCRIPTION = "description",
            SERVICE_ADDRESS = "address",
            PRICE = "price",
            CREATION_DATE = "creationDate",
            COMPLETION_DATE = "completionDate",
            SERVICE_TYPE = "service_type",
            SERVICE_STATUS = "service_status",
            IN_PROCESS = "in_process",

            WORK_ACTION = "work_action",
            ID_WORKER = "idWorker",
            ID_CLIENT = "idClient",

            CURRENT_PAGE = "currentPage",
            PAGE_LIMIT = "pageLimit",
            PAGEABLE = "pageable",
            PARENT_PAGE = "parent_page",

            ERROR = "error",
            ERROR_WORK_MESSAGE = "errorWorkMessage",
            ERROR_INTERNAL = "internalError",
            ERROR_LOGIN_MESSAGE = "errorLoginMessage",
            ERROR_REGISTRATION = "errorRegistration",

            LAST_COMMAND = "last_command",
            COMMAND = "command",
            MESSAGE = "message",
            ALL_SERVICES = "services",
            RESPONSES = "responses",
            SORT_BY = "sort_by",
            SELECTED_SERVICE_TYPE = "selected_service_type",
            SELECTED_SORT_BY_PARAMETER = "selected_sort_by_parameter",
            SELECTED_DIRECTION_PARAMETER = "selected_direction_parameter",
            DIRECTION = "direction",
            PATH = "path",
            SELECTED_SERVICE_STATUS = "selected_service_status",

            SHOW_ORDERS_BY_SERVICE_TYPE = "show_orders_by_service_type",
            SHOW_SERVICE_ALL = "show_all_services",
            UPDATE_USER = "update_user",
            FIND_USER_INFORMATION = "find_user_information",
            SHOW_USER_ORDERS = "show_user_orders",
            ADD_SERVICE_ORDER = "addService",
            SHOW_USER_ORDER = "showUserOrder",
            FIND_CLIENT_RESPONSE = "find_client_response",
            FIND_WORKER_RESPONSE = "find_worker_response",
            FIND_CLIENT_ORDER_BY_STATUS = "find_client_order_by_status",

            DEFAULT_CURRENT_PAGE_PARAM = "1",
            DEFAULT_CURRENT_LIMIT_PARAM = "5";


    private ParameterAttributeType() {
    }
}
