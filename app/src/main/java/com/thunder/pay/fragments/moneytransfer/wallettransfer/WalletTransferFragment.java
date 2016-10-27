package com.thunder.pay.fragments.moneytransfer.wallettransfer;

import android.content.Context;
import android.os.Bundle;
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

import com.thunder.pay.R;
import com.thunder.pay.adapter.transferadapter.wallet.WalletTransferListAdapter;
import com.thunder.pay.util.AppUtills;

public class WalletTransferFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, WalletTransferListAdapter.OnItemClickListener {

    Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView myRecyclerView;

    private LinearLayoutManager layoutManager;
    private WalletTransferListAdapter walletTransferListAdapter;

    public enum Single {
        INSTANCE;
        WalletTransferFragment s = new WalletTransferFragment();

        public WalletTransferFragment getInstance() {
            if (s == null)
                return new WalletTransferFragment();
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

//        AppUtills.setActionBarTitle("Wallet Transfer", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), true);

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
        walletTransferListAdapter = new WalletTransferListAdapter(mContext, null, this);
        myRecyclerView.setAdapter(walletTransferListAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

    }


    @Override
    public void onPopupBtnClicked(Object results, int position) {

        switch (position) {
            case 0:
                AppUtills.loadFragment(WalletToWalletTransferFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;
            case 1:
                AppUtills.loadFragment(WalletToBankTransferFragment.Single.INSTANCE.getInstance(), getActivity(), R.id.container);
                break;
            case 2:
                Toast.makeText(mContext, "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }



}
