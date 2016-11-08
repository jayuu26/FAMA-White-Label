package com.fama.app.adapter.transferadapter.wallet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fama.app.R;

import java.util.ArrayList;

/**
 * Created by ist on 25/8/16.
 */
public class WalletTransferListAdapter extends RecyclerView.Adapter<WalletTransferListAdapter.ViewHolder> {
    private Context mContext;
    private OnItemClickListener listener;

    String sub_title[] = {"Wallet to wallet Transfer", "Wallet to bank Transfer", "Wallet to BKash"};
    int icons[] = {R.drawable.bkash,R.drawable.newaccount,R.drawable.existingaccount, R.drawable.newaccount, R.drawable.bkash};

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imgIcon;
        public TextView txtSubTitle;

        public RelativeLayout relativeLayout;

        public ViewHolder(View v) {
            super(v);
            imgIcon = (ImageView) v.findViewById(R.id.trans_img_id);
            txtSubTitle = (TextView) v.findViewById(R.id.trans_subtitle);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.transfer_row);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public WalletTransferListAdapter(Context mContext, ArrayList<Object> myDataset, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.listener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WalletTransferListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtSubTitle.setText("" + sub_title[position]);
        holder.imgIcon.setImageResource(icons[position]);


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPopupBtnClicked(sub_title[position],position);
            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return sub_title.length;
//        return mDataset.size();
    }

    public interface OnItemClickListener {
        void onPopupBtnClicked(Object results, int position);
    }
}
