package com.thunder.pay.rest;

import android.content.Context;
import android.util.Log;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

public class CustomErrorHandler implements ErrorHandler {
    private final Context ctx;
 
    public CustomErrorHandler(Context ctx) {
        this.ctx = ctx;
    } 
 
    @Override 
    public Throwable handleError(RetrofitError cause) {
        String errorDescription; 
 
        if (cause.isNetworkError()) { 
            errorDescription = "error_network";//ctx.getString(R.string.error_network);
        } else { 
            if (cause.getResponse() == null) { 
                errorDescription = "error_no_response";//ctx.getString(R.string.error_no_response);
            } else { 
 
                // Error message handling - return a simple error to Retrofit handlers.. 
                try { 
                    ErrorResponse errorResponse = (ErrorResponse) cause.getBodyAs(ErrorResponse.class); 
                    errorDescription = errorResponse.error.data.message; 
                } catch (Exception ex) { 
                    try { 
                        errorDescription = "error_network_http_error";//ctx.getString(R.string.error_network_http_error, cause.getResponse().getStatus());
                    } catch (Exception ex2) { 
                        Log.e("Error", "handleError: " + ex2.getLocalizedMessage());
                        errorDescription = "error_unknown";//ctx.getString(R.string.error_unknown);
                    } 
                } 
            } 
        } 
 
        return new Exception(errorDescription); 
    }

    public class ErrorResponse {
        Error error;

        public  class Error {
            Data data;

            public  class Data {
                String message;
            }
        }
    }
} 