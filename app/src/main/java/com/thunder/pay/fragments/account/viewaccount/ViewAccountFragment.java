package com.thunder.pay.fragments.account.viewaccount;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.thunder.pay.R;
import com.thunder.pay.adapter.accountadapter.bank.ViewAccountListAdapter;
import com.thunder.pay.constant.MessageConstant;
import com.thunder.pay.daomodel.DataHandler;
import com.thunder.pay.greendaodb.BankDetail;
import com.thunder.pay.greendaodb.ErrorModel;
import com.thunder.pay.rest.RestCall;
import com.thunder.pay.rest.RestClient;
import com.thunder.pay.util.AppUtills;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAccountFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ViewAccountListAdapter.OnItemClickListener {


    Context mContext;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView myRecyclerView;

    private LinearLayoutManager layoutManager;
    private ViewAccountListAdapter viewAccountListAdapter;

    public enum Single {
        INSTANCE;
        ViewAccountFragment s = new ViewAccountFragment();

        public ViewAccountFragment getInstance() {
            if (s == null)
                return new ViewAccountFragment();
            else return s;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        ViewGroup mainView = (ViewGroup) inflater.inflate(R.layout.view_account_layout, container, false);
        if (mainView != null) {
            mainView.setFocusableInTouchMode(true);
            mainView.requestFocus();
            mainView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //getActivity().onBackPressed();
                    }
                    return false;
                }
            });
        }

//        AppUtills.setActionBarTitle("View Account Details", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), true);

        return mainView;
//        return inflater.inflate(R.layout.about, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadBankList();
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                AppUtills.loadFragment(AddBankAccountFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
            }
        });


        myRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && fab.isShown())
                    fab.hide();
//                if (dy > 0)
//                    fab.hide();
//                else if (dy < 0)
//                    fab.show();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void initList(ArrayList<BankDetail> bankDetails) {
        swipeRefreshLayout.setRefreshing(false);
        viewAccountListAdapter = new ViewAccountListAdapter(mContext, bankDetails, this);
        myRecyclerView.setAdapter(viewAccountListAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        loadBankList();
    }


    @Override
    public void onPopupBtnClicked(Object results, int position) {

    }


    ArrayList<BankDetail> bankDetailsList = new ArrayList<>();

    private void loadBankList() {

        String userId = DataHandler.Single.INSTANCE.getInstance().getInventory().getUserid() + "";
        RestCall service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);
        final Type listType = new TypeToken<List<BankDetail>>() {
        }.getType();

        if (AppUtills.isNetworkAvailable(mContext)) {
//            final ProgressDialog dialog = AppUtills.showProgressDialog(getActivity());

            final Call<ResponseBody> repos = service.getAccountDetailByUserId(userId);

            repos.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        try {
                            String json = "";
                            if (response != null) json = response.body().string();
                            System.out.println("onResponse " + json);

                            if (json.contains("errorMsg")) {
                                try {
                                    ErrorModel errorModel = new ErrorModel();
                                    errorModel = new Gson().fromJson(json, ErrorModel.class);
                                    Toast.makeText(mContext, "" + errorModel.getErrorMsg(), Toast.LENGTH_SHORT).show();
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                bankDetailsList.removeAll(bankDetailsList);
                                List<BankDetail> bankDatas = new Gson().fromJson(json, listType);
                                bankDetailsList.addAll(bankDatas);
                                initList(bankDetailsList);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), MessageConstant.GENERIC_ERROR, Toast.LENGTH_LONG).show();
                    }
//                    AppUtills.cancelProgressDialog(dialog);
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    AppUtills.cancelProgressDialog(dialog);
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


}
