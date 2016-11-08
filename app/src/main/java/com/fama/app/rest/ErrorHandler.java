package com.fama.app.rest;

public class ErrorHandler {

   private String error_msg;
    private okhttp3.Response response;

    public okhttp3.Response getResponse() {
        return response;
    }

    public void setResponse(okhttp3.Response response) {
        this.response = response;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}