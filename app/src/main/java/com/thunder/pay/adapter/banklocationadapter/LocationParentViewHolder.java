package com.thunder.pay.adapter.banklocationadapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.thunder.pay.R;

public class LocationParentViewHolder extends ParentViewHolder {

    public TextView headBankName;
    public TextView headBankAdd;
    public ImageView downArrow;

    public LocationParentViewHolder(View itemView) {
        super(itemView);

        headBankName = (TextView) itemView.findViewById(R.id.mBankName);
        headBankAdd = (TextView) itemView.findViewById(R.id.mBankAdd);
        downArrow = (ImageView) itemView.findViewById(R.id.expand_arrow);
    }

}