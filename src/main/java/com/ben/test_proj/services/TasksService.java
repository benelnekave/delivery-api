package com.ben.test_proj.services;

import com.ben.test_proj.models.my_customer.PrevWeekOrderResponse;
import com.ben.test_proj.models.to_bringg.Task;
import com.ben.test_proj.services.interfaces.IBringgService;
import com.ben.test_proj.services.interfaces.ITasksService;
import com.ben.test_proj.services.interfaces.IValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TasksService implements ITasksService {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(TasksService.class);

    @Autowired
    private IValidationService validationService;

    @Autowired
    private IBringgService bringgService;

    private ObjectMapper jsonMapper;
    @PostConstruct
    public void init(){
        jsonMapper = new ObjectMapper();
    }
    public List<PrevWeekOrderResponse> handleLatestTasks(String customerPhone) throws JsonProcessingException {
        int pageNumber = 1;
        Task[] tasks = bringgService.getTasks(pageNumber);
        log.info("{} tasks were found in Bringg in the number {} page", tasks.length, pageNumber);
        log.debug("tasks: {}", jsonMapper.writeValueAsString(tasks));
        List<PrevWeekOrderResponse> orderResponseList = getPrevWeekOrderResponses(customerPhone, tasks);
        while (tasks.length == 50 && validationService.isDateWithinLastWeek(tasks[tasks.length - 1].getCreatedAt())) {
            tasks = bringgService.getTasks(pageNumber);
            log.info("{} tasks were found in Bringg in the number {} page", tasks.length, pageNumber);
            log.debug("tasks: {}", jsonMapper.writeValueAsString(tasks));
            pageNumber++;
            orderResponseList.addAll(getPrevWeekOrderResponses(customerPhone, tasks));
        }
        log.info("We found the prevWeekOrder: {} in {} pages for customerPhone: {}",
        jsonMapper.writeValueAsString(orderResponseList), pageNumber - 1, customerPhone);

        return orderResponseList;
    }

    private List<PrevWeekOrderResponse> getPrevWeekOrderResponses(String customerPhone, Task[] tasks) {
        List<Task> relevantTasks = Arrays.stream(tasks).filter(isMaxWeekOldCustomer(customerPhone))
                .collect(Collectors.toList());
        return relevantTasks.stream()
                .map(PrevWeekOrderResponse::new)
                .collect(Collectors.toList());
    }


    private Predicate<Task> isMaxWeekOldCustomer(String phoneID) {
        return p -> phoneID.equals(p.getCustomer().getPhone()) && validationService.isDateWithinLastWeek(p.getCreatedAt());
    }
}
