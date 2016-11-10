package com.fama.app.fragments.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fama.app.greendaodb.GraphHistory;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.fama.app.R;
import com.fama.app.adapter.commonadaptter.HomeListGridAdapter;
import com.fama.app.constant.MessageConstant;
import com.fama.app.daomodel.DataHandler;
import com.fama.app.fragments.account.AccountsFragment;
import com.fama.app.fragments.banklocation.LocationFragment;
import com.fama.app.fragments.moneytransfer.MoneyTransferFragment;
import com.fama.app.fragments.moneytransfer.wallettransfer.AddAmountToWalletFragment;
import com.fama.app.greendaodb.ErrorModel;
import com.fama.app.greendaodb.FAMA;
import com.fama.app.greendaodb.Inventory;
import com.fama.app.rest.RestCall;
import com.fama.app.rest.RestClient;
import com.fama.app.util.AppUtills;
import com.fama.app.util.DateUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnTouchListener, View.OnClickListener, HomeListGridAdapter.OnClickListner {

    final int AMOUNT_REFRESH_REQUEST = 1;
    final int GRAPH_REFRESH_REQUEST = 2;
    private RecyclerView myRecyclerView;
//    private GridLayoutManager lLayout;
    private TextView fama_current_amount;
    private Inventory inventory;
    private LineChart mChart;
    private ViewGroup mainView;
    private Typeface tf;
    private Context mContext;
    private TextView currentDate;

    private ImageView locationImg;
    private ImageView rechargeImg;
    private ImageView transferImg;
    private ImageView accountImg;

    private LinearLayout location;
    private LinearLayout transfer;
    private LinearLayout myAccount;
    private LinearLayout rechargeWallet;


    public enum Single {
        INSTANCE;
        HomeFragment s = new HomeFragment();

        public HomeFragment getInstance() {
            if (s == null)
                return new HomeFragment();
            else return s;
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {

            requestToServer(AMOUNT_REFRESH_REQUEST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mainView = (ViewGroup) inflater.inflate(R.layout.home, container, false);
        if (mainView != null) {
            mainView.setFocusableInTouchMode(true);
            mainView.requestFocus();
            mainView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        getActivity().onBackPressed();
                        AppUtills.showExitPopUp(getActivity(), mContext.getResources().getString(R.string.exit_app));

                    }
                    return false;
                }
            });
        }

        inventory = DataHandler.Single.INSTANCE.getInstance().getInventory();
        AppUtills.setActionBarTitle("FAMA", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), false);

        return mainView;
//        return inflater.inflate(R.layout.home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

//        lLayout = new GridLayoutManager(getActivity(), 2);

//        myRecyclerView.setHasFixedSize(true);
//        myRecyclerView.setLayoutManager(lLayout);

        fama_current_amount = (TextView) view.findViewById(R.id.fama_current_amount);

        currentDate = (TextView) view.findViewById(R.id.currentDate);
        currentDate.setText(DateUtils.getMonthNameYear());

        myAccount = (LinearLayout) view.findViewById(R.id.my_account);
        location = (LinearLayout) view.findViewById(R.id.location);
        transfer = (LinearLayout) view.findViewById(R.id.transfer);
        rechargeWallet = (LinearLayout) view.findViewById(R.id.recharge_wallet);

        myAccount.setOnClickListener(this);
        location.setOnClickListener(this);
        transfer.setOnClickListener(this);
        rechargeWallet.setOnClickListener(this);

        myAccount.setOnTouchListener(this);
        location.setOnTouchListener(this);
        transfer.setOnTouchListener(this);
        rechargeWallet.setOnTouchListener(this);

        accountImg = (ImageView) view.findViewById(R.id.account_img);
        rechargeImg = (ImageView) view.findViewById(R.id.recharge_img);
        locationImg = (ImageView) view.findViewById(R.id.location_img);
        transferImg = (ImageView) view.findViewById(R.id.transfer_img);




        requestToServer(AMOUNT_REFRESH_REQUEST);
        requestToServer(GRAPH_REFRESH_REQUEST);
//        HomeListGridAdapter rcAdapter = new HomeListGridAdapter(mContext, null, this);
//        myRecyclerView.setAdapter(rcAdapter);


    }

    private void initGraph(List<GraphHistory> graphHistoryArrayList, View view) {

        mChart = (LineChart) view.findViewById(R.id.chart1);
        mChart.setViewPortOffsets(50, 25, 5, -15);
        mChart.setViewPortOffsets(0, 0,0, 0);
        mChart.setBackgroundColor(getResources().getColor(R.color.themecolor));
        mChart.setDescription("");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setPinchZoom(true);
        mChart.setScaleXEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.setMaxVisibleValueCount(graphHistoryArrayList.size());
        mChart.getLegend().setEnabled(false);
        mChart.animateXY(2000, 2000);
        mChart.setScaleXEnabled(true);
//        mChart.setScaleYEnabled(true);
        mChart.fitScreen();
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);


        XAxis x = mChart.getXAxis();
        x.setEnabled(false);
        x.setYOffset(-10f);
        x.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        x.setTextColor(Color.WHITE);
        x.setAvoidFirstLastClipping(true);

        YAxis y = mChart.getAxisLeft();
        y.setEnabled(false);
        y.removeAllLimitLines();
        y.setDrawTopYLabelEntry(true);
        y.setDrawLabels(true);
//        y.setYOffset(10f);
        y.setXOffset(-20f);
        y.setLabelCount(graphHistoryArrayList.size(), false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawZeroLine(false);




        setData(graphHistoryArrayList);
        mChart.invalidate();
    }


    ArrayList<GraphHistory> graphHistoryArrayList = new ArrayList<>();

    private void setData(List<GraphHistory> graphHistoryArrayList) {

        ArrayList<String> graphXVals = new ArrayList<>();
        ArrayList<Entry> graphYVals = new ArrayList<>();

        int i = 0;
        for (GraphHistory count : graphHistoryArrayList) {
            System.out.println("onResponse " + new Gson().toJson(count));
            graphXVals.add(DateUtils.getDate(count.getDate()));
            float amount = Float.parseFloat(count.getAmount());
            graphYVals.add(new Entry(amount, i));
            i++;
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            mChart.getData().setXVals(graphXVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(graphYVals, "Amount");

            set1.setDrawCubic(true);
            set1.setCubicIntensity(0.3f);


            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setDrawCircles(true);
            set1.setCircleRadius(4f);
            set1.setCircleColor(getResources().getColor(R.color.status_orange_color));
            set1.setCircleColorHole(getResources().getColor(R.color.themecolor));

            set1.setColor(Color.WHITE);
            set1.setLineWidth(1.8f);

            set1.setDrawFilled(true);
            set1.setFillAlpha(70);

            set1.setHighLightColor(getResources().getColor(R.color.status_orange_color));
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setDrawVerticalHighlightIndicator(false);
//            set1.setFillFormatter(new FillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                    return -10;
//                }
//            });
            set1.setFillFormatter(null);

            LineData data = new LineData(graphXVals, set1);
            data.setValueTextSize(12f);
            data.setValueTextColor(Color.WHITE);
            data.setDrawValues(true);
            mChart.setData(data);
            mChart.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_account:
                AppUtills.loadFragment(AccountsFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;
            case R.id.recharge_wallet:
                AppUtills.loadFragment(AddAmountToWalletFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;
            case R.id.location:
                AppUtills.loadFragment(LocationFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;
            case R.id.transfer:
                AppUtills.loadFragment(MoneyTransferFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        accountImg.setImageResource(R.drawable.airtime);
        rechargeImg.setImageResource(R.drawable.deals);
        locationImg.setImageResource(R.drawable.locations);
        transferImg.setImageResource(R.drawable.funds_transfer);
        switch (v.getId()) {
            case R.id.my_account:
                accountImg.setImageResource(R.drawable.airtime_selected);
                break;
            case R.id.recharge_wallet:
                rechargeImg.setImageResource(R.drawable.deals_selected);
                break;
            case R.id.location:
                locationImg.setImageResource(R.drawable.locations_selected);
                break;
            case R.id.transfer:
                transferImg.setImageResource(R.drawable.funds_selected);
                break;
        }
        return false;
    }

    @Override
    public void onClick(int position) {

        switch (position) {
            case 0:
                AppUtills.loadFragment(AccountsFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;
            case 1:
                AppUtills.loadFragment(AddAmountToWalletFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;
            case 2:
                AppUtills.loadFragment(LocationFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;
            case 3:
                AppUtills.loadFragment(MoneyTransferFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;
        }
    }

    public void refresh() {
        AppUtills.setActionBarTitle("FAMA", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), false);
        FAMA fama = DataHandler.Single.INSTANCE.getInstance().getFamaWallet();


        if (fama != null) {
//            Locale locale=new Locale(fama.getCurrencyCode().toUpperCase());
//            Locale locale=new Locale("en", "IN");
//            Currency currency= Currency.getInstance(locale);
            String symbol = fama.getCurrencyCode();//currency.getSymbol();
            fama_current_amount.setText(symbol +" "+ fama.getCurrentAmount());
        }
        else if (inventory.getFamaWallet() != null){
//            Locale locale=new Locale("en",inventory.getFamaWallet().getCurrencyCode().toUpperCase());
//            Locale locale=new Locale("en", "IN");
//            Currency currency= Currency.getInstance(locale);

            String symbol = inventory.getFamaWallet().getCurrencyCode();//currency.getSymbol();
            fama_current_amount.setText(symbol +" "+ inventory.getFamaWallet().getCurrentAmount() );
        }

    }

    private void requestToServer(int requestType) {

        RestCall service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);

        if (AppUtills.isNetworkAvailable(mContext)) {
            final ProgressDialog dialog = AppUtills.showProgressDialog(getActivity());

            switch (requestType) {

                case AMOUNT_REFRESH_REQUEST:
                    final Call<ResponseBody> response = service.findWalletByUserEmail(inventory.getEmail());
                    response.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if (response.code() == HttpURLConnection.HTTP_OK) {
                                    String json = "";
                                    if (response != null)
                                        json = response.body().string();
                                    System.out.println("sendDataToServer onResponse " + json);

                                    if (json.contains("errorMsg")) {
                                        try {
                                            ErrorModel errorModel = new ErrorModel();
                                            errorModel = new Gson().fromJson(json, ErrorModel.class);
                                            AppUtills.showErrorPopUpBack(getActivity(), "" + errorModel.getErrorMsg());
                                        } catch (JsonSyntaxException e) {
                                            e.printStackTrace();
                                            Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        try {
                                            FAMA famaWallet = new Gson().fromJson(json, FAMA.class);
                                            DataHandler.Single.INSTANCE.getInstance().setFamaWallet(famaWallet);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                            }
                            AppUtills.cancelProgressDialog(dialog);

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            AppUtills.cancelProgressDialog(dialog);
                            Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
                case GRAPH_REFRESH_REQUEST:
                    final Call<List<GraphHistory>> graphResponse = service.getFamaWalletUsedAmountList("" + inventory.getUserid(), 10);

                    graphResponse.enqueue(new Callback<List<GraphHistory>>() {
                        @Override
                        public void onResponse(Call<List<GraphHistory>> call, Response<List<GraphHistory>> response) {
                            try {
                                if (response.code() == HttpURLConnection.HTTP_OK) {
                                    if (response != null) {
                                        if (response.isSuccessful()) {
                                            if (graphHistoryArrayList != null)

                                                graphHistoryArrayList.removeAll(graphHistoryArrayList);

                                            graphHistoryArrayList.addAll(response.body());

                                            initGraph(graphHistoryArrayList, mainView);
                                        } else {
                                            if (response.body().contains("errorMsg")) {
                                                try {
                                                    AppUtills.showErrorPopUpBack(getActivity(), "errorModel.getErrorMsg()");
                                                } catch (JsonSyntaxException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                            }
                            AppUtills.cancelProgressDialog(dialog);

                        }

                        @Override
                        public void onFailure(Call<List<GraphHistory>> call, Throwable t) {
                            Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                            AppUtills.cancelProgressDialog(dialog);
                        }
                    });
                    break;
            }

        }

        refresh();
    }

    ArrayList<GraphHistory> sorList;

    private ArrayList<GraphHistory> sortedList(List<GraphHistory> graphHistoryArrayList) {
        Collections.sort(graphHistoryArrayList, new Comparator<GraphHistory>() {
            @Override
            public int compare(GraphHistory fruit2, GraphHistory fruit1) {

                return 0;
                // sorList =    fruit2.getDate().compareTo(fruit1.getDate());
            }
        });

        return null;
    }
}
