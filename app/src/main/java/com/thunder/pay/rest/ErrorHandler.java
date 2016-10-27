package com.thunder.pay.rest;

import android.content.Context;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

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