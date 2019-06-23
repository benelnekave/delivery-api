package com.ben.test_proj.models.my_customer;

import javax.validation.constraints.Pattern;

public class PrevWeekOrderRequest {
    @Pattern(regexp = "^\\\\+([0-9\\\\-]?){9,11}[0-9]$")
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
