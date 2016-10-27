package com.thunder.pay.adapter.historyadapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thunder.pay.R;
import com.thunder.pay.greendaodb.BankDetail;
import com.thunder.pay.util.DateUtils;

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
        private TextView currentAmount;
        private TextView lastUsedTime;
        private LinearLayout second;
        private TextView lastAmount;
        private TextView updatedAmount;
        private TextView transferType;
        private TextView transactioType;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            first = (LinearLayout) view.findViewById(R.id.first);
            currentAmount = (TextView) view.findViewById(R.id.current_amount);
            lastUsedTime = (TextView) view.findViewById(R.id.last_used_time);
            second = (LinearLayout) view.findViewById(R.id.second);
            lastAmount = (TextView) view.findViewById(R.id.last_amount);
            updatedAmount = (TextView) view.findViewById(R.id.updated_amount);
            transferType = (TextView) view.findViewById(R.id.transfer_type);
            transactioType = (TextView) view.findViewById(R.id.transactio_type);
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

            holder.currentAmount.setText(""+bankDetailArrayList.get(position).getAmount());
            holder.lastUsedTime.setText(""+ DateUtils.getDate(bankDetailArrayList.get(position).getUsedOn()));
            holder.lastAmount.setText(""+bankDetailArrayList.get(position).getPreviousWalletAmount());
            holder.updatedAmount.setText(""+bankDetailArrayList.get(position).getUpdatedWalletAmount());
            holder.transferType.setText(""+bankDetailArrayList.get(position).getTransferType());
            holder.transactioType.setText(""+bankDetailArrayList.get(position).getTransactionType());
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
