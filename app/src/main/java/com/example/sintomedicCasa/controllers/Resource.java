package com.example.sintomedicCasa.controllers;

import com.example.sintomedicCasa.rest.RestError;

public class Resource<T> {

    private Status status;
    private T data;
    private String message;
    private RestError restError;
    private int httpCode = -1;

    public Resource() {

    }

    public Resource(Status status, T data, String message, RestError restError, int httpCode) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.restError = restError;
        this.httpCode = httpCode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RestError getRestError() {
        return restError;
    }

    public void setRestError(RestError restError) {
        this.restError = restError;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public boolean isLoading() {
        return status == null || status == Status.LOADING;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }

    public boolean isError() {
        return status == Status.ERROR;
    }

}
