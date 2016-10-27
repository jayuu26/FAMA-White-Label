package com.thunder.pay.util;

import android.content.Context;
import android.location.LocationManager;
import android.widget.EditText;

/**
 * Created by ist on 14/10/16.
 */

public class LocationUtills {

    /**
     * Checks if GPS is enabled or not
     *
     * @param context
     * @return
     */
    public static boolean isGPSEnabled(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean latitudeValidation(EditText latText) {
        if (isValidDecimalNumber(latText.getText().toString())) {
            Double latValue = Double.parseDouble(latText.getText().toString());
            if (latValue >= 6 && latValue <= 37) {
                latText.setError(null);
                return true;
            } else {
                latText.setFocusableInTouchMode(true);
                latText.requestFocus();
                latText.setError("Latitude is out of Indian territory");
            }
        } else {
            latText.setFocusableInTouchMode(true);
            latText.requestFocus();
            latText.setError("Invalid latitude");
        }
        return false;
    }

    public static boolean longitudeValidation(EditText longText) {
        if (isValidDecimalNumber(longText.getText().toString())) {
            Double latValue = Double.parseDouble(longText.getText().toString());
            if (latValue >= 68 && latValue <= 98) {
                longText.setError(null);
                return true;
            } else {
                longText.setFocusableInTouchMode(true);
                longText.requestFocus();
                longText.setError("Longitude is out of Indian territory");
            }
        } else {
            longText.setFocusableInTouchMode(true);
            longText.requestFocus();
            longText.setError("Invalid longitude");
        }
        return false;
    }

    public static boolean isValidDecimalNumber(String input){
        if(input.matches("^[0-9]+(\\.[0-9]*)?$"))
            return true;
        return false;
    }
}
