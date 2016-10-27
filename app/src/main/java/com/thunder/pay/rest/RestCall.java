package com.thunder.pay.rest;


import com.thunder.pay.greendaodb.AdBeneficiaryDetails;
import com.thunder.pay.greendaodb.BankDetail;
import com.thunder.pay.greendaodb.Inventory;
import com.thunder.pay.greendaodb.TransferDetails;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit.http.EncodedPath;
import retrofit.http.EncodedQuery;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by ist on 3/9/16.
 */
public interface RestCall {

/************* unauthorize *************/

    /**
     * Login POST CALL
     */
    @FormUrlEncoded
    @POST("rest/unauthorize/mobileAppAuth")
    Call<Inventory> mobileAppAuth(@Field("username") String uname, @Field("password") String password, @Field("domain") String domain);

/************* Bank *************/

    /**
     * GET FIND BANK LIST
     */
    @GET("rest/Bank/findAll")
    Call<List<BankDetail>> findAll();

/************* BankDetail *************/
    /**
     * GET FIND BANK DETAIL BY BANK ID
     */
    @GET("rest/BankDetail/getAllBankDetailsByBankId/{BankId}")
    Call<List<BankDetail>> getAllBankDetailsByBankId(@Path("BankId") String BankId);

    /**
     * GET CHECK VERSION CALL
     */
    @GET("rest/BankDetail/findAll/")
    Call<List<BankDetail>> bankDetailFindAll();

/************* AssociateBankDetailSearch *************/
    @GET
    Call<ResponseBody> AssociateBankDetailSearch(@Url String url);

    /**
     * GET FIND BANK LIST
     */
    @GET("rest/AssociateBankDetail/getAllAssociatedAccountByAccountNumber/{accountNumber}")
    Call<ResponseBody> getAllAssociatedAccountByAccountNumber(@Path("accountNumber") String accountNumber);

    /**
     * POST UPDATE Add Account CALL
     */
    @POST("rest/AssociateBankDetail/associateAccount/")
    Call<ResponseBody> associateAccount(@Body AdBeneficiaryDetails bankDetail);


    /**
     * PUT UPDATE Add Account CALL
     */
    @PUT("rest/AssociateBankDetail/softDeleteAssociatedAccount/{accountId}")
    Call<ResponseBody> softDeleteAssociatedAccount(@Path("accountId") String accountId);


/************* AccountDetail *************/

    /**
     * GET FIND BANK LIST
     */
    @GET("rest/AccountDetail/getAccountDetailByUserId/{userID}")
    Call<ResponseBody> getAccountDetailByUserId(@Path("userID") String userID);

    /**
     * POST UPDATE Add Account CALL
     */
    @POST("rest/AccountDetail/addAccountDetail/")
    Call<ResponseBody> addAccount(@Body AdBeneficiaryDetails bankDetail);


    /**
     * PUT UPDATE Add Account CALL
     */
    @PUT("rest/AccountDetail/walletToBankMoneyTransfer/")
    Call<ResponseBody> walletToBankMoneyTransfer(@Body TransferDetails transferDetails);


    /**
     * PUT UPDATE Add Account CALL
     */
    @PUT("rest/AccountDetail/bankToBankMoneyTransfer/")
    Call<ResponseBody> bankToBankMoneyTransfer(@Body TransferDetails transferDetails);


/************* FAMA Wallet *************/


    /**
     * PUT UPDATE Add Account CALL
     */
    @PUT("rest/FamaWallet/bankToWalletMoneyTransfer/")
    Call<ResponseBody> bankToWalletMoneyTransfer(@Body TransferDetails transferDetails);


    /**
     * PUT UPDATE Add Account CALL
     */
    @PUT("rest/FamaWallet/walletToWalletMoneyTransfer/")
    Call<ResponseBody> walletToWalletMoneyTransfer(@Body TransferDetails transferDetails);

    /**
     * PUT UPDATE Add Account CALL
     */
    @PUT("rest/FamaWallet/addMoneyToWallet/")
    Call<ResponseBody> addMoneyToWallet(@Body TransferDetails transferDetails);

/************* Transaction History *************/
    @GET
    Call<ResponseBody> FamaWalletHistory(@Url String url);

    @GET
    Call<ResponseBody> BankAccountHistory(@Url String url);



/************* Other Call *************/

    /**
     * POST UPLOAD IMAGE
     */
    @Multipart
    @POST("upload")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);


//    @GET("search")
//    Call<ItuneDataModel> listReposNew(@Query("term") String term, @Query("entity") String entity);
//
//    @GET("search")
//    Call<TrackData> listRepos(@Query("term") String term, @Query("entity") String entity);
//
//    @GET("search")
//    Call<TrackData> listRepos(@QueryMap Map<String, String> search_query);


//
//    /**
//     * GET LOGOUT CALL
//     */
//    @GET("rest/Jiomitrafeedback/logoutJiomitra")
//    Call<ResponseBody> sendLogoutRequest();
//
//    /**
//     * GET USER POINTS CALL
//     */
//    @GET("rest/Jiomitrafeedback/getJioMitraUserByMobileNumber/{MobileNumber}")
//    Call<Inventory> getUserPoints(@Path("MobileNumber") String MobileNumber);
//
//    /**
//     * GET FEDDBACK LIST CALL
//     */
//    @GET("rest/Jiomitrafeedback/getFeedbackByUserId/{UserId}")
//    Call<List<FeedbackData>> getFedddbackList(@Path("UserId") String UserId);
//
//    /**
//     * POST DELETE IMAGE CALL
//     */
//    @POST("rest/Jiomitrafeedbackimage/deleteFeedbackImage/{ImageId}")
//    Call<String> deleteImage(@Path("ImageId") String ImageId);
//
//    /**
//    *POST UPDATE FEEDBACK CALL
//    */
//    @POST("rest/Jiomitrafeedback/updateFeedbackPatroller/")
//    Call<String> updateFeedback(@Body FeedbackData feedbackData);
//
//    /**
//     *POST LATLONG VALIDATION CALL
//     */
//    @POST("rest/Jiomitrafeedback/updateFeedbackPatroller/")
//    Call<FeedbackData> checkLatLong(@Body FeedbackData feedbackData);
//
//    /**
//     *GET CHECK VERSION CALL
//     */
//    @GET("rest/unauthorize/getAppVersionByModuleName")
//    Call<CheckVersionBean> getAppVersion(@Query("module") String module);
//



}
