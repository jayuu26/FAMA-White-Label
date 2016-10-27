package com.thunder.pay.adapter.banklocationadapter;

import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

public class LocationSubHeading {

    public String tvBankName;
    public String tvBankAdd;
    public String tvBankCode;
    public String tvBankIFSC;

    public LocationSubHeading() {
    }

    public LocationSubHeading(String mName, String mAdd) {
//        this.headBankName = headBankName;
//        this.headBankAdd = headBankAdd;
    }

    public String getTvBankName() {
        return tvBankName;
    }

    public void setTvBankName(String tvBankName) {
        this.tvBankName = tvBankName;
    }

    public String getTvBankAdd() {
        return tvBankAdd;
    }

    public void setTvBankAdd(String tvBankAdd) {
        this.tvBankAdd = tvBankAdd;
    }

    public String getTvBankCode() {
        return tvBankCode;
    }

    public void setTvBankCode(String tvBankCode) {
        this.tvBankCode = tvBankCode;
    }

    public String getTvBankIFSC() {
        return tvBankIFSC;
    }

    public void setTvBankIFSC(String tvBankIFSC) {
        this.tvBankIFSC = tvBankIFSC;
    }
}
