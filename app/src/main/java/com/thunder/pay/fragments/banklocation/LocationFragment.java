package com.thunder.pay.fragments.banklocation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.google.gson.Gson;
import com.thunder.pay.R;
import com.thunder.pay.adapter.commonadaptter.AboutListAdapter;
import com.thunder.pay.adapter.banklocationadapter.LocationExpandableAdapter;
import com.thunder.pay.adapter.banklocationadapter.LocationHeading;
import com.thunder.pay.adapter.banklocationadapter.LocationSubHeading;
import com.thunder.pay.greendaodb.BankDetail;
import com.thunder.pay.location.GoogleMapFragment;
import com.thunder.pay.rest.RestCall;
import com.thunder.pay.rest.RestClient;
import com.thunder.pay.util.AppUtills;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment implements GoogleMapFragment.UiUpdate, SwipeRefreshLayout.OnRefreshListener, AboutListAdapter.OnItemClickListener {


    Context mContext;
    private ArrayList<BankDetail> bankLocationDetails;
    ViewGroup mainView;

    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView myRecyclerView;

    private LinearLayoutManager layoutManager;

    public enum Single {
        INSTANCE;
        LocationFragment s = new LocationFragment();

        public LocationFragment getInstance() {
            if (s == null)
                return new LocationFragment();
            else return s;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        mainView = (ViewGroup) inflater.inflate(R.layout.location, null);
        AppUtills.setActionBarTitle("Locations", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), true);

        return mainView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadList();
            }
        });

//        AppUtills.loadChildFragment(Single.INSTANCE.getInstance(), GoogleMapFragment.Single.INSTANCE.getInstance(),getActivity(),R.id.view_map_layout);
//        loadList();
    }

    private void initList() {
        swipeRefreshLayout.setRefreshing(false);
        LocationExpandableAdapter locationExpandableAdapter = new LocationExpandableAdapter(getActivity(), generateLocationList());
        locationExpandableAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onParentExpanded(int parentPosition) {
                Toast.makeText(mContext, "" + parentPosition, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onParentCollapsed(int parentPosition) {
                Toast.makeText(mContext, "" + parentPosition, Toast.LENGTH_SHORT).show();
            }
        });
        myRecyclerView.setAdapter(locationExpandableAdapter);
        AppUtills.loadChildFragment(Single.INSTANCE.getInstance(), GoogleMapFragment.Single.INSTANCE.getInstance(this,bankLocationDetails), getActivity(), R.id.view_map_layout);
    }

    @Override
    public void onRefresh() {
        loadList();
    }

    @Override
    public void onPopupBtnClicked(Object results) {

    }

    private ArrayList<LocationHeading> generateLocationList() {
        ArrayList<LocationHeading> parentObjects = new ArrayList<>();

        if (bankLocationDetails != null) {
            for (int i = 0; i < bankLocationDetails.size(); i++) {
                LocationHeading heading = new LocationHeading();
                heading.setBankName(bankLocationDetails.get(i).getBankName());
                heading.setBankAdd(bankLocationDetails.get(i).getAddress());

                ArrayList<LocationSubHeading> childList = new ArrayList<>();
                LocationSubHeading locationSubHeading = new LocationSubHeading();
                locationSubHeading.setTvBankName("" + bankLocationDetails.get(i).getBankName());
                locationSubHeading.setTvBankAdd("" + bankLocationDetails.get(i).getAddress());
                locationSubHeading.setTvBankCode("" + bankLocationDetails.get(i).getBankCode());
                locationSubHeading.setTvBankIFSC("" + bankLocationDetails.get(i).getCity());
                childList.add(locationSubHeading);
                heading.setLocationDetails(childList);
                parentObjects.add(heading);
            }
        }
        return parentObjects;
    }


    private void loadList() {

        RestCall service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);

        if (AppUtills.isNetworkAvailable(mContext)) {
            final Call<List<BankDetail>> repos = service.bankDetailFindAll();

            repos.enqueue(new Callback<List<BankDetail>>() {
                @Override
                public void onResponse(Call<List<BankDetail>> call, Response<List<BankDetail>> response) {

                    int code = response.code();
                    if (code == 200) {
                        List<BankDetail> bankDatas = response.body();
                        bankLocationDetails = (ArrayList<BankDetail>) bankDatas;
                        System.out.println("onResponse " + new Gson().toJson(bankLocationDetails.get(0)));
                        initList();

                    } else {
                        Toast.makeText(getActivity(), "Did not work: " + String.valueOf(code), Toast.LENGTH_LONG).show();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<BankDetail>> call, Throwable t) {
                    System.out.println("onFailure ");
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    boolean isFullScreen = false;

    @Override
    public void onUiUpdate() {

        Toast.makeText(mContext, "onUiUpdate " + isFullScreen, Toast.LENGTH_SHORT).show();

        FrameLayout frameLayout = (FrameLayout) mainView.findViewById(R.id.view_map_layout);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 2.0f);

        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0, 1.0f);

        if (!isFullScreen) {
            swipeRefreshLayout.setVisibility(View.GONE);
            frameLayout.setLayoutParams(param);
            isFullScreen = true;
        } else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            frameLayout.setLayoutParams(param1);
            isFullScreen = false;
        }

    }
}
