package com.thunder.pay.adapter.accountadapter.baneficiary;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thunder.pay.R;
import com.thunder.pay.greendaodb.BankDetail;

import java.util.ArrayList;

/**
 * Created by ist on 25/8/16.
 */
public class ViewBeneficiaryListAdapter extends RecyclerView.Adapter<ViewBeneficiaryListAdapter.ViewHolder> {
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
        private FloatingActionButton fab;
        private LinearLayout first;
        private TextView baneficiaryName;
        private TextView bankCode;
        private TextView accNo;
        private TextView linkedAcccNo;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            fab = (FloatingActionButton) view.findViewById(R.id.fab);
            first = (LinearLayout) view.findViewById(R.id.first);
            baneficiaryName = (TextView) view.findViewById(R.id.baneficiary_name);
            bankCode = (TextView) view.findViewById(R.id.bank_code);
            accNo = (TextView) view.findViewById(R.id.acc_no);
            linkedAcccNo = (TextView) view.findViewById(R.id.linked_accc_no);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ViewBeneficiaryListAdapter(Context mContext, ArrayList<BankDetail> bankDetails, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.listener = onItemClickListener;
        this.bankDetailArrayList = bankDetails;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewBeneficiaryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_beneficiary_row_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(bankDetailArrayList!=null && bankDetailArrayList.size()>0){
            holder.accNo.setText(bankDetailArrayList.get(position).getAccountNumber());
            holder.bankCode.setText(bankDetailArrayList.get(position).getIfscCode());
            holder.baneficiaryName.setText(bankDetailArrayList.get(position).getAccountHolderName());
            if(bankDetailArrayList.get(position).getAccountDetail()!=null)
                holder.linkedAcccNo.setText((bankDetailArrayList.get(position).getAccountDetail().getAccountNumber()));
        }

        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteBtnClicked(bankDetailArrayList.get(position),position);
            }
        });
     }




    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return bankDetailArrayList.size();
    }

    public interface OnItemClickListener {
        void onPopupBtnClicked(Object results, int position);
        void onDeleteBtnClicked(BankDetail bankDetail, int position);
    }
}
