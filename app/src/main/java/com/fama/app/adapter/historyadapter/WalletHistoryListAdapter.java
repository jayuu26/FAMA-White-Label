package com.fama.app.adapter.historyadapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.CaseFormat;
import com.fama.app.R;
import com.fama.app.constant.AppConstants;
import com.fama.app.greendaodb.BankDetail;
import com.fama.app.util.AppUtills;
import com.fama.app.util.DateUtils;

import java.util.ArrayList;

/**
 * Created by ist on 25/8/16.
 */
public class WalletHistoryListAdapter extends RecyclerView.Adapter<WalletHistoryListAdapter.ViewHolder> {
    private Context mContext;
    private OnItemClickListener listener;
    private ArrayList<BankDetail> bankDetailArrayList;

//    String sub_title[] = {"Wallet to wallet all_transfer_base_layout", "Wallet to bank all_transfer_base_layout", "Wallet to BKash"};
//    int icons[] = {R.drawable.bkash,R.drawable.newaccount,R.drawable.existingaccount, R.drawable.newaccount, R.drawable.bkash};

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private LinearLayout first;
        private TextView transactionType;
        private TextView lastAmount;
        private TextView updatedAmount;
        private TextView transactionBy;
        private TextView transactionTime;
        private TextView currentAmount;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            first = (LinearLayout) view.findViewById(R.id.first);
            transactionType = (TextView) view.findViewById(R.id.transaction_type);
            lastAmount = (TextView) view.findViewById(R.id.last_amount);
            updatedAmount = (TextView) view.findViewById(R.id.updated_amount);
            transactionBy = (TextView) view.findViewById(R.id.transaction_by);
            transactionTime = (TextView) view.findViewById(R.id.transaction_time);
            currentAmount = (TextView) view.findViewById(R.id.current_amount);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public WalletHistoryListAdapter(Context mContext, ArrayList<BankDetail> bankDetails, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.listener = onItemClickListener;
        this.bankDetailArrayList = bankDetails;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WalletHistoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_history_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (bankDetailArrayList != null && bankDetailArrayList.size() > 0) {
            String type = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, ""+bankDetailArrayList.get(position).getTransactionType());

            type = AppUtills.splitCamelCase(type)+ "("+bankDetailArrayList.get(position).getCurrency()+")";
            holder.transactionType.setText( type);
            holder.lastAmount.setText("Previous Amount : " + bankDetailArrayList.get(position).getPreviousWalletAmount());
            holder.updatedAmount.setText("Updated Amount : " + bankDetailArrayList.get(position).getUpdatedWalletAmount());

            if(bankDetailArrayList.get(position).getProcessedBy()!=null) {
                String name =  bankDetailArrayList.get(position).getProcessedBy().getFirstname()+
                        " "+bankDetailArrayList.get(position).getProcessedBy().getLastname();
                name = AppUtills.capitalizeName(name);
                holder.transactionBy.setText("Transaction By : " +name);
            }

            holder.transactionTime.setText("" + DateUtils.getDateTime(bankDetailArrayList.get(position).getUsedOn()));

            holder.currentAmount.setText("" + bankDetailArrayList.get(position).getAmount());

            if (bankDetailArrayList.get(position).getTransferType().equalsIgnoreCase("" + AppConstants.ADD_MONEY)) {
                holder.currentAmount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_black_24dp,0,0,0);
            } else if (bankDetailArrayList.get(position).getTransferType().equalsIgnoreCase("" + AppConstants.DEDUCT_MONEY)) {
                holder.currentAmount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove_black_24dp,0,0,0);
            }
        }
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
