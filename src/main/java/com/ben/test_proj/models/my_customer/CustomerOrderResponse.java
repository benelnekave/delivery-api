package com.ben.test_proj.models.my_customer;

public class CustomerOrderResponse {
    private boolean success;
    private CustomerOrder customer;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CustomerOrder getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerOrder customer) {
        this.customer = customer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
