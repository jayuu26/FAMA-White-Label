package com.thunder.pay.location;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.thunder.pay.R;
import com.thunder.pay.fragments.account.viewaccount.AddBankAccountFragment;
import com.thunder.pay.fragments.banklocation.LocationFragment;
import com.thunder.pay.greendaodb.BankDetail;
import com.thunder.pay.util.AppUtills;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {
    private ProgressDialog _progress = null;
    private Context mContext;
    private static int LatLngBoundPadding = 70;
    private GoogleMap googleMap;
    private ToggleButton map_type_toggle_btn;
    ImageView large_map, close_map;
    boolean isFullScreen =false;


//	private TextView mTextViewDistance;
//	private LinearLayout mLayoutDistance;

    private Marker mMyLocationMarker;
    private String building_id = "", min_x, min_y;

    private static final int BUFFER_SIZE = 1024;

    private static GoogleMapFragment gridRouteActivity;

    private ViewGroup mainView;
    static UiUpdate listner;
    static ArrayList<BankDetail> bankDetail;



    @SuppressLint("ValidFragment")
    private GoogleMapFragment() {
        // TODO Auto-generated constructor stub
    }



    public enum Single {
        INSTANCE;
        GoogleMapFragment s = new GoogleMapFragment();

        public GoogleMapFragment getInstance(UiUpdate listner,ArrayList<BankDetail> bankDetail) {
            initListner(listner,bankDetail);
            if (s == null) {

                return new GoogleMapFragment();
            } else {

                return s;
            }

        }
    }

    public static void initListner(UiUpdate uiUpdatelistner,ArrayList<BankDetail> detail){
       listner = uiUpdatelistner;
        bankDetail = detail;
    }


    private TextView bankName;
    private TextView bankCode;
    private TextView bankAdd;
    private TextView bankContact;

    private void setInfoWindow(){


        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.layout_custom_info_window, null);

                bankName = (AppCompatTextView) view.findViewById(R.id.bankName);
                bankCode = (AppCompatTextView) view.findViewById(R.id.bankCode);
                bankAdd = (AppCompatTextView) view.findViewById(R.id.bankAdd);
                bankContact = (AppCompatTextView) view.findViewById(R.id.bankContact);

                bankName.setText(""+bankLocationDetails.get(marker.getId()).getBankName());
                bankCode.setText(""+bankLocationDetails.get(marker.getId()).getBankCode());
                bankAdd.setText(""+bankLocationDetails.get(marker.getId()).getAddress());
                bankContact.setText(""+bankLocationDetails.get(marker.getId()).getBranchContactNumber());

                return view;
            }
        });

        // Adding and showing marker while touching the GoogleMap
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mMyLocationMarker.hideInfoWindow();
                // Clears any existing markers from the GoogleMap
//                googleMap.clear();



                // Creating an instance of MarkerOptions to set position
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Setting position on the MarkerOptions
//                markerOptions.position(bankLocationLatLngGrid);
//
//                // Animating to the currently touched position
//                googleMap.animateCamera(CameraUpdateFactory.newLatLng(bankLocationLatLngGrid));
//
//                // Adding marker on the GoogleMap
//                Marker marker = googleMap.addMarker(markerOptions);

                // Showing InfoWindow on the GoogleMap
//                marker.showInfoWindow();


            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mainView != null) {
            ViewGroup parent = (ViewGroup) mainView.getParent();
            if (parent != null)
                parent.removeView(mainView);
        }
        try {
            mainView = (ViewGroup) inflater.inflate(
                    R.layout.googlemap_view_layout, container, false);
            mContext = getActivity();

            SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);
            mapFrag.getMapAsync(this);
            myLocationButton();

            final FloatingActionButton fab = (FloatingActionButton) mainView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isFullScreen){
                        isFullScreen = true;
                        fab.setImageResource(R.drawable.ic_zoom_out_black_24dp);
                    }else{
                        fab.setImageResource(R.drawable.ic_zoom_in_black_24dp);
                        isFullScreen = false;
                    }

                    listner.onUiUpdate();
                }
            });

        } catch (Exception exception) {

        }
        return mainView;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

         this.googleMap = googleMap;
        addMarker();

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        Fragment fragment = (getChildFragmentManager().findFragmentById(R.id.googleMap));
        FragmentManager childFragmentManager = getChildFragmentManager();
        childFragmentManager.beginTransaction().remove(fragment);
        super.onDestroyView();
    }


    private void showErrorDialog(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title).setMessage(msg).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    Hashtable<String, BankDetail> bankLocationDetails = new Hashtable<String, BankDetail>();
    private void addMarker() {
        if(bankDetail!=null) {
            markers = new Marker[bankDetail.size()];
            for (int i = 0; i < bankDetail.size(); i++) {
                LatLng mLatLngMy = new LatLng(bankDetail.get(i).getLatitude(),bankDetail.get(i).getLongitude());
                mMyLocationMarker = googleMap.addMarker(new MarkerOptions().
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .position(mLatLngMy));
                mMyLocationMarker.showInfoWindow();
                bankLocationDetails.put(mMyLocationMarker.getId(),bankDetail.get(i));
                markers[i] = mMyLocationMarker;
            }
        }
        mMyLocationMarker.showInfoWindow();
        fixZoom();
        setInfoWindow();
    }

    private void myLocationButton() {

        // Get the button view
        View locationButton = ((View) mainView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));

        // and next place it, for exemple, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 30, 250);
    }

    public interface UiUpdate {
        public void onUiUpdate();
    }

    Marker markers[];

    private void fixZoom() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.animateCamera(cu);
    }


}
