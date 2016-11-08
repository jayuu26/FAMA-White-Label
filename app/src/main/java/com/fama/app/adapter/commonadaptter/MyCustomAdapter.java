package com.fama.app.adapter.commonadaptter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fama.app.R;
import com.fama.app.greendaodb.BankDetail;

import java.util.ArrayList;

public class MyCustomAdapter extends ArrayAdapter<BankDetail> {

    Context mContext;
    Activity activity;
    ArrayList<BankDetail>  bankDetails;
    String LOV_TYPE;



    public MyCustomAdapter(Activity activity, ArrayList<BankDetail>  items,String type) {
        super(activity, android.R.layout.simple_list_item_1, items);
        this.bankDetails = items;
        this.activity = activity;
        this.LOV_TYPE = type;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        String value;
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.spinner_layout, null);

        TextView textView = (TextView)row.findViewById(R.id.spin_row);
        if(LOV_TYPE.equalsIgnoreCase("Name"))
            textView.setText(""+bankDetails.get(position).getBankName());
        else if(LOV_TYPE.equalsIgnoreCase("Branch")) {
            textView.setText("" + bankDetails.get(position).getDescription());
        } else if(LOV_TYPE.equalsIgnoreCase("Account")) {
            textView.setText("" + bankDetails.get(position).getAccountNumber());
        }else if(LOV_TYPE.equalsIgnoreCase("AccountHolder")) {
            if(position>0)
                textView.setText("" + bankDetails.get(position).getAccountHolderName() +"\n("+ bankDetails.get(position).getAccountNumber() +" )");
            else
                textView.setText("" + bankDetails.get(position).getAccountHolderName());
        }
        return row;
    }

}
