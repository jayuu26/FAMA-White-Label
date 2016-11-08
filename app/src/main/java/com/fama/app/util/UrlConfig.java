package com.fama.app.util;

/**
 * Created by ist on 3/9/16.
 */
public interface UrlConfig {

//  String BASE_URL = "http://192.168.43.104:8080/FAMA/"; // Local
    String BASE_URL =  "http://52.201.90.251:8082/FAMA/"; // Server


    String LOGIN_URL = "rest/unauthorize/mobileAppAuth";//post, username,password,domain

    String GET_FAMA_LOCATION = "rest/BankDetail/findAll/";//GET

}
