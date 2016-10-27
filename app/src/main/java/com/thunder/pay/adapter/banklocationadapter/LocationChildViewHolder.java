package com.thunder.pay.adapter.banklocationadapter;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.thunder.pay.R;

public class LocationChildViewHolder extends ChildViewHolder {

    public TextView tvBankName;
    public TextView tvBankAdd;
    public TextView tvBankCode;
    public TextView tvBankIFSC;


    public LocationChildViewHolder(View itemView) {
        super(itemView);

        tvBankName = (TextView) itemView.findViewById(R.id.mBankName);
        tvBankAdd = (TextView) itemView.findViewById(R.id.mBankAdd);
        tvBankCode = (TextView) itemView.findViewById(R.id.mBankCode);
        tvBankIFSC = (TextView) itemView.findViewById(R.id.mBankCity);

    }

}