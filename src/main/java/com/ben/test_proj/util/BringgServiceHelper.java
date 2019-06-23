package com.ben.test_proj.util;

import com.ben.test_proj.bringg_sign.SignatureManager;
import com.ben.test_proj.models.my_customer.CustomerOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class BringgServiceHelper {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(BringgServiceHelper.class);


    @Value("${company.id}")
    private String company_id;

    @Value("${access.token}")
    private String accessToken;

    public Map<String, Object> createTaskMap(CustomerOrder customerOrder, String customerId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("company_id", Integer.valueOf(company_id));
        requestMap.put("customer_id", Integer.valueOf(customerId));
        requestMap.put("title", customerOrder.getOrderDetails());
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String date = simpleDateFormat.format(now);
        requestMap.put("address", customerOrder.getAddress());
        requestMap.put("scheduled_at", date);
        return requestMap;
    }

    public String getJsonStringFromMap(Map<String, Object> requestParamMap, ObjectMapper mapper) {
        String json = "";
        try {
            json = mapper.writeValueAsString(requestParamMap);
        } catch (JsonProcessingException e) {
            log.error("error on json mapping", e);
        }
        return json;
    }

    public void insertSignature(Map<String, Object> requestParamMap, SignatureManager signatureManager) {
        String signature = signatureManager.sign(createUrlToSign(requestParamMap), false);
        requestParamMap.put("signature", signature);
    }


    public Map<String, Object> generateCreateCustomerRequestMap(CustomerOrder customerOrder) {
        Map<String, Object> map = new HashMap<>();
        map.put("address", customerOrder.getAddress());
        map.put("company_id", company_id);
        map.put("name", customerOrder.getName());
        map.put("phone", customerOrder.getCellPhoneNumber());
        return map;
    }

    public String createUrlToSign(Map<String, Object> paramMap) {

        String url = "";
        try {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                url += entry.getKey() + "=" + URLEncoder.encode(entry.getValue() + "", "UTF-8") + "&";
            }
        } catch (UnsupportedEncodingException e) {
            log.error("error on encoding url", e);
        }
        url = url.substring(0, url.length() - 1); // deleting the last &
        return url;
    }

    public void addBringgBaseParams(Map<String, Object> paramMap) {
        paramMap.put("access_token", accessToken);
        paramMap.put("timestamp", System.currentTimeMillis());
    }

    public void addGetByPhoneParams(Map<String, String> paramMap) {
        paramMap.put("company_id", company_id);
    }
}
