package com.fama.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fama.app.R;
import com.fama.app.constant.MessageConstant;
import com.fama.app.daomodel.DataHandler;
import com.fama.app.dbhelper.InventoryDBHelper;
import com.fama.app.greendaodb.Inventory;
import com.fama.app.rest.RestCall;
import com.fama.app.rest.RestClient;
import com.fama.app.util.AppUtills;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLoginActivity extends Activity implements View.OnClickListener {

    private TextView tvErrorMsgPassword;
    private Context mContext;
    private  String uName,uPassword,domain="xyz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        tvErrorMsgPassword = (TextView) findViewById(R.id.tvErrorMsgPassword);
        findViewById(R.id.btnLogin).setOnClickListener(this);
    }

    private EditText getEtUserName(){
        return (EditText) findViewById(R.id.etUserName);
    }

    private EditText getEtPassword(){
        return (EditText) findViewById(R.id.etPassword);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                //TODO implement
                if(validateLogin()) {
                  sendLoginRequest();
//                    Intent intent = new Intent(ActivityLoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
                }
                break;
        }
    }

    private boolean validateLogin(){

        uName = ""+getEtUserName().getText().toString();
        uPassword = ""+getEtPassword().getText().toString();

        if(uName!=null && !uName.equalsIgnoreCase("") &&uName.length()>0){

            if(uPassword!=null && !uPassword.equalsIgnoreCase("") &&uPassword.length()>0){

               /* if(uName.equalsIgnoreCase("Admin")){
                    if(uPassword.equals("Admin@123")){
                        return  true;
                    }else{
                        Toast.makeText(this, "Enter Correct Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Enter Correct User Name", Toast.LENGTH_SHORT).show();
                }*/
                return true;
            }else{
                Toast.makeText(this, "Please check Password", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Please check User Name", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private RestCall service;
    public void sendLoginRequest() {
        service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);
        final ProgressDialog dialog = AppUtills.showProgressDialog(this);
        Call<Inventory> response =   service.mobileAppAuth(""+uName,""+uPassword,""+domain);

        response.enqueue(new Callback<Inventory>() {
            @Override
            public void onResponse(Call<Inventory> call, Response<Inventory> response) {

                Object result = response.body();
                System.out.println("onResponse  " + result);
                if (result instanceof Inventory) {
                    Inventory inventory = (Inventory) result;

                    DataHandler.Single.INSTANCE.getInstance().setInventory(inventory);
                    Intent intent = new Intent(ActivityLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    InventoryDBHelper.single.INSTANCE.getInstnce().insertItem(inventory,ActivityLoginActivity.this);

                } else {
                    Toast.makeText(getApplicationContext(), ""+ MessageConstant.WRONG_LOGIN_MSG, Toast.LENGTH_LONG).show();
                }
                AppUtills.cancelProgressDialog(dialog);
            }

            @Override
            public void onFailure(Call<Inventory> call, Throwable t) {
                System.out.println("onFailure  " + t.toString());
                Toast.makeText(getApplicationContext(), ""+MessageConstant.GENERIC_ERROR, Toast.LENGTH_LONG).show();
                AppUtills.cancelProgressDialog(dialog);
            }

        });

    }

}
