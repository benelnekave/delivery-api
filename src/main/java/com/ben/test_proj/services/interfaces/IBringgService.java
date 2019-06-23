package com.ben.test_proj.services.interfaces;

import com.ben.test_proj.enums.CustomerExistence;
import com.ben.test_proj.models.my_customer.CustomerOrder;
import com.ben.test_proj.models.to_bringg.Task;

public interface IBringgService {

    String sendCreateCustomerRequest(CustomerOrder request);

    CustomerExistence checkCustomerByPhoneExist(String phoneNumber);

    Task[] getTasks(int pageNumber);

    String sendCreateTaskRequest(CustomerOrder customerOrder, String customerId);
}
