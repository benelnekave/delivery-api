package com.ben.test_proj.models.to_bringg;

import java.util.HashMap;
import java.util.Map;

public class CreateTaskResponse {
    private boolean success;

    private Map<String, Object> task = new HashMap<>();

    private String message;

    public Map<String, Object> getTask() {
        return task;
    }

    public void setTask(Map<String, Object> task) {
        this.task = task;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}
