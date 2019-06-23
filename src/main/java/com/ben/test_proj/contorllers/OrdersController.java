package com.ben.test_proj.contorllers;

import com.ben.test_proj.constants.APIMessagesConstants;
import com.ben.test_proj.models.my_customer.CustomerOrder;
import com.ben.test_proj.models.my_customer.CustomerOrderResponse;
import com.ben.test_proj.models.my_customer.PrevWeekOrderResponse;
import com.ben.test_proj.services.interfaces.IOrderService;
import com.ben.test_proj.services.interfaces.ITasksService;
import com.ben.test_proj.services.interfaces.IValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrdersController {

    private static Logger log = org.slf4j.LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IValidationService validationService;

    @Autowired
    private ITasksService tasksService;

    @PostMapping("/order")
    public ResponseEntity<CustomerOrderResponse> newOrder(@Valid @RequestBody CustomerOrder customerOrder) {
        String customerOrderResponse = orderService.sendOrder(customerOrder);

        CustomerOrderResponse response = new CustomerOrderResponse();
        response.setCustomer(customerOrder);
        if (!customerOrderResponse.equals(APIMessagesConstants.CUSTOMER_ORDER_SUCCESS)) {
            response.setSuccess(false);
            response.setMessage(APIMessagesConstants.CUSTOMER_ORDER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.setSuccess(true);
        response.setMessage(APIMessagesConstants.CUSTOMER_ORDER_SUCCESS);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/orders")
    public ResponseEntity<String> getLastWeekTasks(@RequestParam @Valid String customer_phone) throws JsonProcessingException {
        if (!validationService.validatePhoneNumber(customer_phone)) {
            return ResponseEntity.badRequest().body(APIMessagesConstants.BAD_API_ARGUMENTS);
        }
        List<PrevWeekOrderResponse> prevWeekOrderResponses = tasksService.handleLatestTasks(customer_phone);
        return ResponseEntity.ok(new ObjectMapper().writeValueAsString(prevWeekOrderResponses));
    }

}
