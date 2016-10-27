package com.thunder.pay.fragments.common;

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

import com.thunder.pay.R;
import com.thunder.pay.adapter.commonadaptter.AboutListAdapter;

public class ActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AboutListAdapter.OnItemClickListener {


    Context mContext;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView myRecyclerView;

    private LinearLayoutManager layoutManager;
    private AboutListAdapter aboutListAdapter;

    public enum Single {
        INSTANCE;
        ActivityFragment s = new ActivityFragment();

        public ActivityFragment getInstance() {
            if (s == null)
                return new ActivityFragment();
            else return s;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        ViewGroup mainView = (ViewGroup) inflater.inflate(R.layout.activity, container, false);
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
        return  mainView;
//        return inflater.inflate(R.layout.recent, null);
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
        aboutListAdapter = new AboutListAdapter(mContext, null, this);
        myRecyclerView.setAdapter(aboutListAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onPopupBtnClicked(Object results) {

    }
}
