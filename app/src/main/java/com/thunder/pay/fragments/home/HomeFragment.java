package com.thunder.pay.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thunder.pay.R;
import com.thunder.pay.adapter.commonadaptter.HomeListGridAdapter;
import com.thunder.pay.daomodel.DataHandler;
import com.thunder.pay.fragments.account.AccountsFragment;
import com.thunder.pay.fragments.banklocation.LocationFragment;
import com.thunder.pay.fragments.common.CommonAccountsFragment;
import com.thunder.pay.fragments.moneytransfer.MoneyTransferFragment;
import com.thunder.pay.fragments.moneytransfer.wallettransfer.AddAmountToWalletFragment;
import com.thunder.pay.greendaodb.FAMA;
import com.thunder.pay.greendaodb.Inventory;
import com.thunder.pay.util.AppUtills;
import com.thunder.pay.util.DateUtils;

public class HomeFragment extends Fragment implements HomeListGridAdapter.OnClickListner {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView myRecyclerView;
    private GridLayoutManager lLayout;
    private TextView fama_current_amount,currentDate;
    private Inventory inventory;

    Context mContext;

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
        if(menuVisible){

            //refresh();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        ViewGroup mainView = (ViewGroup) inflater.inflate(R.layout.home, container, false);
        if (mainView != null) {
            mainView.setFocusableInTouchMode(true);
            mainView.requestFocus();
            mainView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        getActivity().onBackPressed();
                        AppUtills.showExitPopUp(getActivity(),mContext.getResources().getString(R.string.exit_app));

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

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        lLayout = new GridLayoutManager(getActivity(), 2);

        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(lLayout);

        fama_current_amount = (TextView) view.findViewById(R.id.fama_current_amount);

        currentDate = (TextView) view.findViewById(R.id.currentDate);

        currentDate.setText(DateUtils.getMonthNameYear());

        if(inventory.getFamaWallet()!=null)
            fama_current_amount.setText(""+inventory.getFamaWallet().getCurrentAmount()+""+inventory.getFamaWallet().getCurrencyCode());

        refresh();
        HomeListGridAdapter rcAdapter = new HomeListGridAdapter(mContext, null, this);
        myRecyclerView.setAdapter(rcAdapter);
    }



    @Override
    public void onClick(int position) {

        switch (position){
            case 0:
                AppUtills.loadFragment(AccountsFragment.Single.INSTANCE.getInstance(),getActivity(),R.id.container);
                break;
            case 1:
                AppUtills.loadFragment(AddAmountToWalletFragment.Single.INSTANCE.getInstance(),getActivity(),R.id.container);
                break;
            case 2:
                AppUtills.loadFragment(LocationFragment.Single.INSTANCE.getInstance(),getActivity(),R.id.container);
                break;
            case 3:
                AppUtills.loadFragment(MoneyTransferFragment.Single.INSTANCE.getInstance(),getActivity(),R.id.container);
                break;
        }
    }

    public void refresh(){

        AppUtills.setActionBarTitle("FAMA", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), false);
        FAMA fama = DataHandler.Single.INSTANCE.getInstance().getFamaWallet();

        if(fama!=null)
            fama_current_amount.setText(""+fama.getCurrentAmount()+""+fama.getCurrencyCode());

    }
}
