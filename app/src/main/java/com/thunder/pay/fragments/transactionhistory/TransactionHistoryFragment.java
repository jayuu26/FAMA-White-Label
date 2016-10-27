package com.thunder.pay.fragments.transactionhistory;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.thunder.pay.Listener.PageSelectedListener;
import com.thunder.pay.R;
import com.thunder.pay.adapter.commonadaptter.ViewPagerAdapter;
import com.thunder.pay.customlayout.SlidingTabLayout;
import com.thunder.pay.fragments.account.managebeneficiary.ManageBeneficaryFragment;
import com.thunder.pay.fragments.account.viewaccount.ViewAccountFragment;
import com.thunder.pay.fragments.transactionhistory.bankhistory.BankHistoryFragment;
import com.thunder.pay.fragments.transactionhistory.wallethistory.WalletHistoryFragment;
import com.thunder.pay.util.AppUtills;


public class TransactionHistoryFragment extends Fragment implements ViewPager.OnPageChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager viewPager;
    private SlidingTabLayout tabLayout;
    private AdView mAdView;


    private PageSelectedListener listener;
    public TransactionHistoryFragment() {
        // Required empty public constructor
    }

    public enum Single {
        INSTANCE;
        TransactionHistoryFragment s = new TransactionHistoryFragment();

        public TransactionHistoryFragment getInstance() {
            if (s == null)
                return new TransactionHistoryFragment();
            else return s;
        }
    }

    // TODO: Rename and change types and number of parameters
    public static TransactionHistoryFragment newInstance(String param1, String param2) {
        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View mainView = inflater.inflate(R.layout.home_base, container, false);
        if (mainView != null) {
            mainView.setFocusableInTouchMode(true);
            mainView.requestFocus();
            mainView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        getActivity().onBackPressed();
                    }
                    return false;
                }
            });
        }
        AppUtills.setActionBarTitle("Transaction History", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), true);

        onPageSelected(mainView);
        return  mainView;

//        return inflater.inflate(R.layout.home_base, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);
        tabLayout = (SlidingTabLayout) view.findViewById(R.id.tabanim_tabs);
        tabLayout.setDistributeEvenly(true);
        tabLayout.setViewPager(viewPager);
        int[] myColorArray = {Color.parseColor("#FFA72A")};
        tabLayout.setSelectedIndicatorColors(myColorArray);

        tabLayout.setOnPageChangeListener(this);

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                BankHistoryFragment.Single.INSTANCE.getInstance().onPageSelected(position);
                break;
            case 1:
//                WalletHistoryFragment.Single.INSTANCE.getInstance().onPageSelected(position);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(BankHistoryFragment.Single.INSTANCE.getInstance(), "Bank History");
        adapter.addFrag(WalletHistoryFragment.Single.INSTANCE.getInstance(), "Wallet History");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public void onPageSelected(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);
        tabLayout = (SlidingTabLayout) view.findViewById(R.id.tabanim_tabs);
        tabLayout.setViewPager(viewPager);
    }


}
