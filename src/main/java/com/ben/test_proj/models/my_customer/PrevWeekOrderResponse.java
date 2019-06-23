package com.ben.test_proj.models.my_customer;

import com.ben.test_proj.models.to_bringg.Customer;
import com.ben.test_proj.models.to_bringg.Task;

public class PrevWeekOrderResponse extends CustomerOrder {
    private String orderTime;

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public PrevWeekOrderResponse() {
    }

    public PrevWeekOrderResponse(Task task) {
        Customer customer = task.getCustomer();
        this.orderTime = task.getCreatedAt();
        this.address = customer.getAddress();
        this.cellPhoneNumber = customer.getPhone();
        this.name = customer.getName();
        this.orderDetails = task.getTitle();
    }

}
