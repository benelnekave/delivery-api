package com.ben.test_proj.services;

import com.ben.test_proj.constants.APIMessagesConstants;
import com.ben.test_proj.models.my_customer.CustomerOrder;
import com.ben.test_proj.services.interfaces.IBringgService;
import com.ben.test_proj.services.interfaces.IOrderService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private IBringgService bringgService;

    @Override
    public String sendOrder(CustomerOrder customerOrder) {

//if (isCustomerExistsInBringg(customerOrder)) //don't need to create a new one, we can get he's bringg id from the response

        String customerId = bringgService.sendCreateCustomerRequest(customerOrder);
        if (APIMessagesConstants.ERROR_IN_BRINGG_API_CALL.equals(customerId)) {
            return APIMessagesConstants.CUSTOMER_ORDER_ERROR;
        }
        String taskCreateSuccess = bringgService.sendCreateTaskRequest(customerOrder, customerId);
        if (APIMessagesConstants.ERROR_IN_BRINGG_API_CALL.equals(taskCreateSuccess)) {
            return APIMessagesConstants.CUSTOMER_ORDER_ERROR;
        }

        return APIMessagesConstants.CUSTOMER_ORDER_SUCCESS;

    }
//The following block of code is an extra code I added and it's out of the assignment scope. I thought it's good to have :)
/*
    private boolean isCustomerExistsInBringg(CustomerOrder customerOrder) {
        CustomerExistence customerExistence = bringgService.checkCustomerByPhoneExist(customerOrder.getCellPhoneNumber());
        if(customerExistence.equals(CustomerExistence.CUSTOMER_DOESNT_EXIST)) {
            CustomerCreation customerCreation = bringgService.sendCreateCustomerRequest(customerOrder);
            if(CustomerCreation.FAIL.equals(customerCreation)){
                return true;
            }
        }
        else if(!customerExistence.equals(CustomerExistence.CUSTOMER_EXIST)){
            return true;

        }
        return false;
    }
*/

}
