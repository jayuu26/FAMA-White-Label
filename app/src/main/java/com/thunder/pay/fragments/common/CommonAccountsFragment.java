package com.thunder.pay.fragments.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.thunder.pay.R;
import com.thunder.pay.adapter.commonadaptter.ViewPagerAdapter;
import com.thunder.pay.customlayout.SlidingTabLayout;
import com.thunder.pay.util.AppUtills;


public class CommonAccountsFragment extends Fragment {
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


    public CommonAccountsFragment() {
        // Required empty public constructor
    }

    public enum Single {
        INSTANCE;
        CommonAccountsFragment s = new CommonAccountsFragment();

        public CommonAccountsFragment getInstance() {
            if (s == null)
                return new CommonAccountsFragment();
            else return s;
        }
    }

    // TODO: Rename and change types and number of parameters
    public static CommonAccountsFragment newInstance(String param1, String param2) {
        CommonAccountsFragment fragment = new CommonAccountsFragment();
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
        AppUtills.setActionBarTitle("Account", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), true);

        return inflater.inflate(R.layout.account_base, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);
        tabLayout = (SlidingTabLayout) view.findViewById(R.id.tabanim_tabs);
        tabLayout.setViewPager(viewPager);

        System.out.println("  getString(banner_ad_unit_id) " + getString(R.string.banner_ad_unit_id));

        MobileAds.initialize(getActivity(), "" + getString(R.string.banner_ad_unit_id));

        mAdView = (AdView) view.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
        mAdView.setVisibility(View.GONE);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(ActivityFragment.Single.INSTANCE.getInstance(), "Activity");
        adapter.addFrag(SummaryFragment.Single.INSTANCE.getInstance(), "Summary");
        viewPager.setAdapter(adapter);
    }

}
