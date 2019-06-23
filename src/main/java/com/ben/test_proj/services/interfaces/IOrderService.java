package com.ben.test_proj.services.interfaces;

import com.ben.test_proj.models.my_customer.CustomerOrder;

public interface IOrderService {

    String sendOrder(CustomerOrder customerOrder);
}
