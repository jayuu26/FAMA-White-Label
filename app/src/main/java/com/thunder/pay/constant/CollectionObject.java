package com.thunder.pay.constant;

import java.util.ArrayList;
import java.util.Hashtable;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ist on 2/9/16.
 */
public class CollectionObject {

    public static ArrayList<String> countryName = new ArrayList<>();
    public static Hashtable<String, String> counrtyCode = new Hashtable<>();


    public static Hashtable<Request,Response> requestResponseMap = new Hashtable<>();
    public static ArrayList<Response> requestResponse = new ArrayList<>();

}
