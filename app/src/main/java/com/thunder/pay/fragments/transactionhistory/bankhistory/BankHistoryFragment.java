package com.thunder.pay.fragments.transactionhistory.bankhistory;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.thunder.pay.adapter.historyadapter.BankHistoryListAdapter;
import com.thunder.pay.constant.MessageConstant;
import com.thunder.pay.daomodel.DataHandler;
import com.thunder.pay.fragments.account.myaccount.AddBankAccountFragment;
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

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

public class BankHistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BankHistoryListAdapter.OnItemClickListener {


    Context mContext;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView myRecyclerView;

    private LinearLayoutManager layoutManager;
    private BankHistoryListAdapter bankHistoryListAdapter;

    public enum Single {
        INSTANCE;
        BankHistoryFragment s = new BankHistoryFragment();

        public BankHistoryFragment getInstance() {
            if (s == null)
                return new BankHistoryFragment();
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

//        AppUtills.setActionBarTitle("Bank History", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), true);

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
                isRefresh = true;
                loadBankList(0,offset);
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
        fab.setVisibility(View.GONE);

        myRecyclerView.addOnScrollListener(onScrollLoadList(layoutManager));
    }

    private void initList(ArrayList<BankDetail> bankDetails, int newSize) {
        swipeRefreshLayout.setRefreshing(false);
        bankHistoryListAdapter = new BankHistoryListAdapter(mContext, bankDetails, this);
        myRecyclerView.getLayoutManager().scrollToPosition(bankDetails.size()-newSize);
        myRecyclerView.setAdapter(bankHistoryListAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        loadBankList(0,(upperLimit-1));
    }


    @Override
    public void onPopupBtnClicked(Object results, int position) {

    }


    ArrayList<BankDetail> bankDetailsList = new ArrayList<>();
    boolean mIsLoading = false;
    boolean mIsLastPage = false;

    private void loadBankList(int lowerLimitCount, int upperLimitCount) {

        String userId = DataHandler.Single.INSTANCE.getInstance().getInventory().getUserid() + "";
        RestCall service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);
        final Type listType = new TypeToken<List<BankDetail>>() {
        }.getType();

        if (AppUtills.isNetworkAvailable(mContext)) {
            mIsLoading = true;
//            final ProgressDialog dialog = AppUtills.showProgressDialog(getActivity());

            String fiqlUrl = "rest/BankAccountHistory/search?_s=user.userid==" + userId + "&llimit=" + lowerLimitCount +"&ulimit=" + upperLimitCount;
            final Call<ResponseBody> repos = service.BankAccountHistory(fiqlUrl);

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
                                if(isRefresh)
                                    bankDetailsList.removeAll(bankDetailsList);
                                List<BankDetail> bankDatas = new Gson().fromJson(json, listType);
                                bankDetailsList.addAll(bankDatas);
                                upperLimit = bankDetailsList.size();
                                if (bankDetailsList.size() > PAGE_SIZE) {

                                } else {
                                    mIsLastPage = true;
                                }
                                initList(bankDetailsList,bankDatas.size());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if(isRefresh)
                            Toast.makeText(getActivity(), MessageConstant.GENERIC_ERROR, Toast.LENGTH_LONG).show();
                    }
//                    AppUtills.cancelProgressDialog(dialog);
                    mIsLoading = false;
                    isRefresh =false;
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    AppUtills.cancelProgressDialog(dialog);
                    if(isRefresh)
                        Toast.makeText(getActivity(), MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    mIsLoading = false;
                    isRefresh =false;

                }
            });


        }
    }

    boolean isRefresh = false;
    int lowerLimit = 0;
    int upperLimit = 0;
    int offset = 5;
    private RecyclerView.OnScrollListener onScrollLoadList(final LinearLayoutManager mLayoutManager) {

        RecyclerView.OnScrollListener
                mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                if (!mIsLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {

                        System.out.println("y =  " + dy);
                        if (dy > 20) {
                            swipeRefreshLayout.setRefreshing(true);
                            lowerLimit = totalItemCount;
                            upperLimit = totalItemCount;
                            loadBankList(lowerLimit,offset);
                            System.out.println(" PAGE_SIZE " + PAGE_SIZE +
                                    " \n visibleItemCount " + visibleItemCount +
                                    "\n totalItemCount " + totalItemCount +
                                    "\n firstVisibleItemPosition " + firstVisibleItemPosition);

                        }
                    }
                }
            }
        };
        return mRecyclerViewOnScrollListener;
    }

    public void onPageSelected(int Position) {
    }
}
