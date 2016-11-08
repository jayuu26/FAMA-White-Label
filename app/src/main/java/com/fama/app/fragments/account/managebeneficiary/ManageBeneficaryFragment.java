package com.fama.app.fragments.account.managebeneficiary;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fama.app.R;
import com.fama.app.adapter.accountadapter.baneficiary.ManageBeneficiaryListAdapter;
import com.fama.app.util.AppUtills;

public class ManageBeneficaryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ManageBeneficiaryListAdapter.OnItemClickListener {


    Context mContext;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView myRecyclerView;

    private LinearLayoutManager layoutManager;
    private ManageBeneficiaryListAdapter manageBeneficiaryListAdapter;

    public enum Single {
        INSTANCE;
        ManageBeneficaryFragment s = new ManageBeneficaryFragment();

        public ManageBeneficaryFragment getInstance() {
            if (s == null)
                return new ManageBeneficaryFragment();
            else return s;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        ViewGroup mainView = (ViewGroup) inflater.inflate(R.layout.all_transfer_base_layout, container, false);
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

//        AppUtills.setActionBarTitle("Manage Beneficiary", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), true);

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
        initList();
    }

    private void initList() {
        manageBeneficiaryListAdapter = new ManageBeneficiaryListAdapter(mContext, null, this);
        myRecyclerView.setAdapter(manageBeneficiaryListAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

    }


    @Override
    public void onPopupBtnClicked(Object results, int position) {

        switch (position) {
            case 0:
                AppUtills.loadFragment(AddBeneficiaryFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;
            case 1:
                AppUtills.loadFragment(ViewBeneficiaryFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;

        }
    }



}
