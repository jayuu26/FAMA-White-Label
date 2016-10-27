package com.thunder.pay.customlayout;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.thunder.pay.R;

/**
 * Created by ist-153 on 30/6/16.
 */
public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    Typeface roboBold;
    private Context mContext;
    private View infoWindowView;

    public CustomInfoWindow(){
    }

    public CustomInfoWindow(Context mContext){
        this.mContext = mContext;
        infoWindowView = ((AppCompatActivity)mContext).getLayoutInflater().inflate(R.layout.layout_custom_info_window, null);
//        AppCompatTextView tvSiteNameText = (AppCompatTextView) infoWindowView.findViewById(R.id.tvSiteNameText);
//        AppCompatTextView tvSiteAdressName = (AppCompatTextView) infoWindowView.findViewById(R.id.tvSiteAdressName);
        roboBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface roboRegular = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");
//        tvSiteNameText.setTypeface(roboRegular);
    }

    @Override
    public View getInfoWindow(Marker marker) {


//                AppCompatTextView tvInfoWindowSiteName = ((AppCompatTextView) infoWindowView.findViewById(R.id.tvInfoWindowSiteName));
//                tvInfoWindowSiteName.setText(Html.fromHtml("Name"));
//                tvInfoWindowSiteName.setTypeface(roboBold);

//                AppCompatTextView tvInfoWindowSiteAddress = ((AppCompatTextView) infoWindowView.findViewById(R.id.tvInfoWindowSiteAddress));
//                tvInfoWindowSiteAddress.setText(Html.fromHtml("Address"));
//                tvInfoWindowSiteAddress.setTypeface(roboBold);

            return infoWindowView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
//    private void fetchInfoWindowSiteImage(String imagePath){
//        String imageName = imagePath.lastIndexOf("/");
//    }


}
