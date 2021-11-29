package com.zendesk.viewer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@ControllerAdvice
public class ViewerGlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(HttpClientErrorException.class)
    public String handleUnauthorizedError() {

        return "unauthorizedError.html";
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerExceptionError() {

        return "nullError.html";
    }

    @ExceptionHandler(JsonProcessingException.class)
    public String handleJsonProcessingExceptionError() {

        return "jsonError.html";
    }


}
