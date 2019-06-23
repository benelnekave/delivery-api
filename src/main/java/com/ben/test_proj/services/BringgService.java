package com.ben.test_proj.services;

import com.ben.test_proj.bringg_sign.SignatureManager;
import com.ben.test_proj.constants.APIMessagesConstants;
import com.ben.test_proj.constants.UrlConstants;
import com.ben.test_proj.enums.CustomerExistence;
import com.ben.test_proj.models.my_customer.CustomerOrder;
import com.ben.test_proj.models.to_bringg.CreateCustomerResponse;
import com.ben.test_proj.models.to_bringg.CreateTaskResponse;
import com.ben.test_proj.models.to_bringg.Customer;
import com.ben.test_proj.models.to_bringg.Task;
import com.ben.test_proj.services.interfaces.IBringgService;
import com.ben.test_proj.util.BringgServiceHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class BringgService implements IBringgService {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(BringgService.class);

    @Value("${secret.key}")
    private String secretKey;
    @Value("${access.token}")
    private String accessToken;
    @Value("${company.id}")
    private String company_id;

    @Autowired
    private BringgServiceHelper serviceHelper;

    private ObjectMapper mapper;
    private SignatureManager signatureManager;

    @PostConstruct
    public void init() {
        signatureManager = new SignatureManager(secretKey);
        mapper = new ObjectMapper();
    }

    @Override
    public String sendCreateCustomerRequest(CustomerOrder request) {
        Map<String, Object> requestParamMap = serviceHelper.generateCreateCustomerRequestMap(request);
        serviceHelper.addBringgBaseParams(requestParamMap);
        serviceHelper.insertSignature(requestParamMap, signatureManager);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String json = serviceHelper.getJsonStringFromMap(requestParamMap, mapper);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CreateCustomerResponse> response = null;
        try {
            log.info("about to send {} with headers {}", json, headers);
            response = restTemplate.exchange(UrlConstants.BRINGG_DEVELOPER_HOSTNAME +
                    UrlConstants.POST_CREATE_CUSTOMER_URL, HttpMethod.POST, entity, CreateCustomerResponse.class);
            log.debug("response from create customer post request: {}", mapper.writeValueAsString(response.getBody()));
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("bad response form bringg on createCustomer call");
                return APIMessagesConstants.ERROR_IN_BRINGG_API_CALL;
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("REST request error on customer creation: ", e);
            return APIMessagesConstants.ERROR_IN_BRINGG_API_CALL;
        } catch (JsonProcessingException e) {
            log.error("error on json mapping", e);
        }

        Customer customer = response.getBody().getCustomer();
        if (customer != null) {
            log.info("customer was created internal id: {} bringgId: {}", customer.getPhone(), customer.getId());
            return String.valueOf(customer.getId());
        } else {
            log.error("customer returned null from response. res");
            return APIMessagesConstants.ERROR_IN_BRINGG_API_CALL;
        }

    }

    public String sendCreateTaskRequest(CustomerOrder customerOrder, String customerId) {
        Map<String, Object> requestParamMap = serviceHelper.createTaskMap(customerOrder, customerId);
        serviceHelper.addBringgBaseParams(requestParamMap);
        serviceHelper.insertSignature(requestParamMap, signatureManager);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String json = serviceHelper.getJsonStringFromMap(requestParamMap, mapper);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CreateTaskResponse> response = null;
        try {
            log.info("about to send {} with headers {}", json, headers);
            response = restTemplate.exchange(UrlConstants.BRINGG_DEVELOPER_HOSTNAME +
                    UrlConstants.TASKS_URL, HttpMethod.POST, entity, CreateTaskResponse.class);
            log.debug("response from create task post request: {}", mapper.writeValueAsString(response.getBody()));
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("bad response form bringg on createTask call");
                return APIMessagesConstants.ERROR_IN_BRINGG_API_CALL;
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("REST request error on customer creation: ", e);
            return APIMessagesConstants.ERROR_IN_BRINGG_API_CALL;
        } catch (JsonProcessingException e) {
            log.error("error on json mapping", e);
        }
        log.info("Task {} for customer {} was created successfully ", customerOrder.getOrderDetails(), customerOrder.getCellPhoneNumber());
        return APIMessagesConstants.TASK_CREATED;
    }


    public Task[] getTasks(int pageNumber) {
        String baseParams = "timestamp=" + System.currentTimeMillis() + "&access_token=" + accessToken + "&company_id=" + company_id + "&page=" + pageNumber;
        String urlParams = "?" + baseParams + "&signature=" + signatureManager.sign(baseParams, false);

        String url = UrlConstants.BRINGG_DEVELOPER_HOSTNAME + UrlConstants.TASKS_URL + urlParams;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity headersEntity = new HttpEntity(headers);
        ResponseEntity<Task[]> response = null;
        try {
            log.info("About to make a get request to bringg: url: {} headers: {}", url, headers);
            response = restTemplate.exchange(url, HttpMethod.GET, headersEntity, Task[].class);
            log.debug("response from getCustomerByPhone: {}", response.getBody());
            log.debug("response from get tasks " +
                    "get request: {}", mapper.writeValueAsString(response.getBody()));

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("REST request error: ", e);
            if (e.getRawStatusCode() != 200) {
                log.error("Error on bringg api");
            }

        } catch (JsonProcessingException e) {
            log.error("error on json mapping", e);
        }

        return response.getBody();
    }


    @Override
    public CustomerExistence checkCustomerByPhoneExist(String phoneNumber) {
        String baseParams = "timestamp=" + System.currentTimeMillis() + "&access_token=" + accessToken + "&company_id=" + company_id;
        String urlParams = "?" + baseParams + "&signature=" + signatureManager.sign(baseParams, false);
        String url = UrlConstants.BRINGG_DEVELOPER_HOSTNAME + UrlConstants.GET_BY_PHONE_URL + phoneNumber + urlParams;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "authKey");
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity headersEntity = new HttpEntity(headers);

        try {
            log.info("About to make a get request to bringg: url: {} headers: {}", url, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, headersEntity, String.class);
            log.debug("response from getCustomerByPhone: {}", response.getBody());
            log.info("Customer {} exists in bringg", phoneNumber);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("REST request error: ", e);
            if (e.getRawStatusCode() == 404) {
                log.error("Customer {} doesn't exist", phoneNumber);
                return CustomerExistence.CUSTOMER_DOESNT_EXIST;
            } else if (e.getRawStatusCode() == 401) {
                log.error("customer {} {}", phoneNumber, APIMessagesConstants.BRINGG_AUTORIZATION_ERROR);
                return CustomerExistence.BRINGG_AUTORIZATION_ERROR;
            } else {
                log.error("customer {} {}", phoneNumber, APIMessagesConstants.ERROR_IN_BRINGG_API_CALL);
                return CustomerExistence.ERROR_IN_BRINGG_API_CALL;
            }
        }

        return CustomerExistence.CUSTOMER_EXIST;
    }


}
    /* generic bringg api call
    private ResponseEntity<?> callBringgAPI(String url, HttpMethod httpMethod, HttpEntity<String> httpEntity, Class<?> clazz){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = null;
        try {
            log.info("about to send {} with headers {}", httpEntity.getBody(), httpEntity.getHeaders());
            response = restTemplate.exchange(UrlConstants.BRINGG_DEVELOPER_HOSTNAME +
                    UrlConstants.TASKS_URL, HttpMethod.POST, httpEntity, clazz);
            log.debug("response from create task post request: {}", mapper.writeValueAsString(response.getBody()));
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("bad response form bringg on createTask call");
                return response;
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("REST request error on customer creation: ", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        } catch (JsonProcessingException e) {
            log.error("error on json mapping", e);
        }
        return response;

    }
    */
