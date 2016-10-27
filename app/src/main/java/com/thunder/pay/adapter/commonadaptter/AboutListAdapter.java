package com.thunder.pay.adapter.commonadaptter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thunder.pay.R;

import java.util.ArrayList;

/**
 * Created by ist on 25/8/16.
 */
public class AboutListAdapter extends RecyclerView.Adapter<AboutListAdapter.ViewHolder> {
    private Context mContext;
    private OnItemClickListener listener;

    String title[] = {"Phone","Build","App configuration","Server","Configuration","Privacy Policy"};
    String sub_title[] = {"1-888-4400899","2.0.3","3.4.2","3.4.102","2.4.102",">"};

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtTitle;
        public TextView txtSubTitle;

        public RelativeLayout relativeLayout;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.title);
            txtSubTitle = (TextView) v.findViewById(R.id.subtitle);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.about_row);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AboutListAdapter(Context mContext, ArrayList<Object> myDataset, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.listener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AboutListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtTitle.setText(""+title[position]);
        holder.txtSubTitle.setText(""+sub_title[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return title.length;
//        return mDataset.size();
    }

    public interface OnItemClickListener {
        void onPopupBtnClicked(Object results);
    }
}
