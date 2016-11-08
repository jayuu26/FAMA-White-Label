package com.fama.app.rest;

import android.content.Context;
import android.util.Log;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ist on 21/9/16.
 */
public class FileUploader {

    RestCall service;
    boolean isuploded = false;

    public enum Single {
        INSTANCE;
        FileUploader s = new FileUploader();

        public FileUploader getInstance() {
            if (s == null)
                return new FileUploader();
            else return s;
        }
    }


    private boolean uploadFile(File file, Context mcontext) {
        // create upload service client

        service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mcontext);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.upload(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
                isuploded = true;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                isuploded = false;
            }
        });
        return isuploded;
    }
}
