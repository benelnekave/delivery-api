package com.ben.test_proj.exceptions;

import com.ben.test_proj.constants.APIMessagesConstants;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleArgumentNotValid(MethodArgumentNotValidException e) {
        log.info("Received a request with missing parameters {}", e);
        ResponseEntity responseEntity = new ResponseEntity(APIMessagesConstants.BAD_API_ARGUMENTS, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handleNullPointerException(NullPointerException e) {
        log.error("NullPointerException for a request {}", e);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info("Received a request with unsupported method", e);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("Received a request with missing parameters.", e);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(ClientAbortException.class)
    public void handleClientAbortException(ClientAbortException e) {
        log.info("Client aborted");
        log.info("Exception: {}", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleDefaultException(Exception e) {
        log.error("{} for a request ", e.getClass().getSimpleName(), e);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
}
