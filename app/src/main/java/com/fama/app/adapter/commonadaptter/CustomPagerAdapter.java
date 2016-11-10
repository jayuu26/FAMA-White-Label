package com.fama.app.adapter.commonadaptter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fama.app.R;
import com.fama.app.greendaodb.BankDetail;

import java.util.ArrayList;

// Custom pager adapter not using fragments
public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    Activity activity;
    LayoutInflater mLayoutInflater;
    ArrayList<BankDetail> pages = new ArrayList<>();

    public CustomPagerAdapter(Context context, ArrayList<BankDetail> pages) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.pages = pages;
    }

    // Returns the number of pages to be displayed in the ViewPager.
    @Override
    public int getCount() {
        return pages.size();
    }

    // Returns true if a particular object (page) is from a particular page
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // This method should create the page for the given position passed to it as an argument. 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Inflate the layout for the page
//        View itemView = mLayoutInflater.inflate(R.layout.view_account_row_layout, container, false);
        // Find and populate data into the page (i.e set the image)
//        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        // ...
        // Add the page to the container
//        container.addView(itemView);
        // Return the page
        View mainView =   mLayoutInflater.inflate(R.layout.view_account_row_layout, container, false);
        // Create an initial view to display; must be a subclass of FrameLayout.
//        FrameLayout itemView = (FrameLayout) mLayoutInflater.inflate (R.layout.view_account_row_layout, null);
        container.addView(mainView, position);


        return mainView;
    }

    // Removes the page from the container for the given position.
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // container.removeView((View) object);
    }
}