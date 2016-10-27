package com.thunder.pay.adapter.commonadaptter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thunder.pay.R;

import java.util.List;

/**
 * Created by ist on 9/10/16.
 */

public class HomeListGridAdapter extends RecyclerView.Adapter<HomeListGridAdapter.RecyclerViewHolders> {

    private OnClickListner listner;
    private String[] itemList = {"Accounts", "FAMA Recharge", "Location", "Money Transfer"};
    //    private int icons[] = {R.drawable.coupons, R.drawable.deals, R.drawable.locations, R.drawable.home_search};
//    private int selected_icons[] = {R.drawable.coupon_selected, R.drawable.deals_selected, R.drawable.locations_selected, R.drawable.home_search_selected};
    TypedArray icons, selected_icons;
    private Context mContext;

    public HomeListGridAdapter(Context context, List<String> itemList, OnClickListner onClickListner) {
        this.mContext = context;
        listner = onClickListner;
        icons = mContext.getResources().obtainTypedArray(R.array.home_grid_icon_opt1);
        selected_icons = mContext.getResources().obtainTypedArray(R.array.home_grid_sel_icon_opt1);
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);


        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.optName.setText("" + itemList[position]);
        if(previousPosition == position){
            holder.optPhoto.setImageResource(selected_icons.getResourceId(position, -1));
        }else{
            holder.optPhoto.setImageResource(icons.getResourceId(position, -1));
        }

    }

    @Override
    public int getItemCount() {
        return itemList.length;
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {

        public TextView optName;
        public ImageView optPhoto;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
           // itemView.setOnTouchListener(this);

            optName = (TextView) itemView.findViewById(R.id.opt_name);
            optPhoto = (ImageView) itemView.findViewById(R.id.opt_photo);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            optPhoto.setImageResource(selected_icons.getResourceId(getPosition(), -1));
            return false;
        }

        @Override
        public void onClick(View view) {
            previousPosition = getPosition();
            notifyDataSetChanged();
            listner.onClick(getPosition());
        }
    }

    public interface OnClickListner {
        void onClick(int position);
    }

    int previousPosition = -1;

}