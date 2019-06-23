package com.ben.test_proj.services.interfaces;

import com.ben.test_proj.models.my_customer.PrevWeekOrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ITasksService {
    List<PrevWeekOrderResponse> handleLatestTasks(String customerId) throws JsonProcessingException;
}
