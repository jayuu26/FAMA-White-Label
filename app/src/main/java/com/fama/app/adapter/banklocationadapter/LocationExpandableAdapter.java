package com.fama.app.adapter.banklocationadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.fama.app.R;

import java.util.List;

/**
 * Created by ist on 13/10/16.
 */

public class LocationExpandableAdapter extends ExpandableRecyclerAdapter<LocationHeading,LocationSubHeading,LocationParentViewHolder,LocationChildViewHolder> {


    LayoutInflater mInflater;

    public LocationExpandableAdapter(Context mContext,@NonNull List<LocationHeading> parentList) {
        super(parentList);
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public LocationChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.expandable_child_row_layout, childViewGroup, false);
        return new LocationChildViewHolder(view);
    }

    @NonNull
    @Override
    public LocationParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.expandable_parent_row_layout, parentViewGroup, false);

        return new LocationParentViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull LocationParentViewHolder parentViewHolder, int parentPosition, @NonNull LocationHeading parent) {

        parentViewHolder.headBankName.setText(""+parent.getBankName());
        parentViewHolder.headBankAdd.setText(""+parent.getBankAdd());

    }

    @Override
    public void onBindChildViewHolder(@NonNull LocationChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull LocationSubHeading child) {

        childViewHolder.tvBankName.setText(child.getTvBankName());
        childViewHolder.tvBankAdd.setText(child.getTvBankAdd());
        childViewHolder.tvBankCode.setText(child.getTvBankCode());
        childViewHolder.tvBankIFSC.setText(child.getTvBankIFSC());
    }
}
