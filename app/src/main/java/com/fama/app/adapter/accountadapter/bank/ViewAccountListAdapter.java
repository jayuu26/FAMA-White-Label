package com.fama.app.adapter.accountadapter.bank;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fama.app.R;
import com.fama.app.greendaodb.BankDetail;
import com.fama.app.util.DateUtils;

import java.util.ArrayList;

/**
 * Created by ist on 25/8/16.
 */
public class ViewAccountListAdapter extends RecyclerView.Adapter<ViewAccountListAdapter.ViewHolder> {
    private Context mContext;
    private OnItemClickListener listener;
    private ArrayList<BankDetail> bankDetailArrayList;

//    String sub_title[] = {"Wallet to wallet all_transfer_base_layout", "Wallet to bank all_transfer_base_layout", "Wallet to BKash"};
//    int icons[] = {R.drawable.bkash,R.drawable.newaccount,R.drawable.existingaccount, R.drawable.newaccount, R.drawable.bkash};

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private CardView cardView;
        private LinearLayout first;
        private TextView accNo;
        private TextView customerNo;
        private TextView balance;
        private TextView lastUseDate;
        public RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            first = (LinearLayout) view.findViewById(R.id.first);
            accNo = (TextView) view.findViewById(R.id.acc_no);
            customerNo = (TextView) view.findViewById(R.id.customer_no);
            balance = (TextView) view.findViewById(R.id.balance);
            lastUseDate = (TextView) view.findViewById(R.id.last_use_date);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.transfer_row);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ViewAccountListAdapter(Context mContext, ArrayList<BankDetail> bankDetails, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.listener = onItemClickListener;
        this.bankDetailArrayList = bankDetails;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewAccountListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_account_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(bankDetailArrayList!=null && bankDetailArrayList.size()>0){
            holder.accNo.setText(bankDetailArrayList.get(position).getAccountNumber());
            holder.customerNo.setText(bankDetailArrayList.get(position).getCustomerId());
            holder.balance.setText(bankDetailArrayList.get(position).getBalance()+ " ("+bankDetailArrayList.get(position).getCurrencyCode()+")");
            holder.lastUseDate.setText(DateUtils.getDateTime(bankDetailArrayList.get(position).getLastUsedOn()));
        }
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onPopupBtnClicked(sub_title[position],position);
//            }
//        });
    }




    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return bankDetailArrayList.size();
    }

    public interface OnItemClickListener {
        void onPopupBtnClicked(Object results, int position);
    }
}
