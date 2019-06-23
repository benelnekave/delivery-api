package com.ben.test_proj.constants;

public class APIMessagesConstants {
    public static final String PHONE_VALIDATION_REGEX = "^\\s?((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?\\s?";
    public static final String ERROR_IN_BRINGG_API_CALL = "Error in bringg api call, please look at the logs for more info";
    public static final String BRINGG_AUTORIZATION_ERROR = "401 Error in bringg api call, please look at the signature process";
    public static final String CUSTOMER_ORDER_SUCCESS = "Customer order success";
    public static final String CUSTOMER_ORDER_ERROR = "Customer order error";
    public static final String TASK_CREATED = "TASK_CREATED";
    public static final String BAD_API_ARGUMENTS = "Please check api arguments";
}
