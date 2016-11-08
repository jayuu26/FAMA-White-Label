package com.fama.app.fragments.bills;

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
import com.fama.app.R;
import com.fama.app.adapter.commonadaptter.ViewPagerAdapter;
import com.fama.app.customlayout.SlidingTabLayout;
import com.fama.app.util.AppUtills;


public class BillsFragment extends Fragment {
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


    public BillsFragment() {
        // Required empty public constructor
    }

    public enum Single {
        INSTANCE;
        BillsFragment s = new BillsFragment();

        public BillsFragment getInstance() {
            if (s == null)
                return new BillsFragment();
            else return s;
        }
    }

    // TODO: Rename and change types and number of parameters
    public static BillsFragment newInstance(String param1, String param2) {
        BillsFragment fragment = new BillsFragment();
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
        AppUtills.setActionBarTitle("Bills", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), true);

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
        adapter.addFrag(RecentFragment.Single.INSTANCE.getInstance(), "Recent");
        adapter.addFrag(ScheduledFragment.Single.INSTANCE.getInstance(), "Scheduled");
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

        System.out.println("  getString(banner_ad_unit_id) " + getString(R.string.banner_ad_unit_id));

        MobileAds.initialize(getActivity(), "" + getString(R.string.banner_ad_unit_id));

        mAdView = (AdView) view.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
        mAdView.setVisibility(View.GONE);
    }
}
