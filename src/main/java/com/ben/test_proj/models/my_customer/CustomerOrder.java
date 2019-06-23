package com.ben.test_proj.models.my_customer;

import com.ben.test_proj.constants.APIMessagesConstants;

import javax.validation.constraints.Pattern;

public class CustomerOrder {

    @Pattern(regexp = APIMessagesConstants.PHONE_VALIDATION_REGEX)
    protected String cellPhoneNumber;

    protected String name;
    protected String address;
    protected String orderDetails;

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }
}
