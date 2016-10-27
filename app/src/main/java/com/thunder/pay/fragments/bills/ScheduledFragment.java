package com.thunder.pay.fragments.bills;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thunder.pay.R;
import com.thunder.pay.adapter.commonadaptter.AboutListAdapter;

public class ScheduledFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AboutListAdapter.OnItemClickListener {


    Context mContext;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView myRecyclerView;

    private LinearLayoutManager layoutManager;
    private AboutListAdapter aboutListAdapter;

    public enum Single {
        INSTANCE;
        ScheduledFragment s = new ScheduledFragment();

        public ScheduledFragment getInstance() {
            if (s == null)
                return new ScheduledFragment();
            else return s;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        ViewGroup mainView = (ViewGroup) inflater.inflate(R.layout.scheduled, container, false);
        onPageSelected(mainView);
        return  mainView;
//        return inflater.inflate(R.layout.scheduled, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println(" inside onViewCreated Sceduledh ");
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

    public void onPageSelected(View view){

        System.out.println(" inside onViewCreated Sceduledh ");
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);
        initList();
    }
}
