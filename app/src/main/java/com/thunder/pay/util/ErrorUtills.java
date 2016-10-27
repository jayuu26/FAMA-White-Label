package com.thunder.pay.util;

import android.text.InputFilter;
import android.widget.EditText;
import android.widget.Spinner;

import org.jetbrains.annotations.Contract;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ist on 15/10/16.
 */

public class ErrorUtills {


    public static boolean checkTextNull(String text) {

        if (text != null && !text.equalsIgnoreCase("")) {
                return true;
        }

        return false;
    }

    public static boolean checkTextMinLength(String text, int charLength) {

        if (text != null && !text.equalsIgnoreCase("")) {
            if (text.toString().length() >= charLength)
                return true;
        }

        return false;
    }

    public static boolean checkTextMinMaxLength(String text, int charLength) {

        if (text != null && !text.equalsIgnoreCase("")) {
            if (text.toString().length() >= charLength)
                return true;
        }

        return false;
    }

    public static boolean isSpinnerValid(Spinner spinner) {
        if (spinner != null)
        if(spinner.getSelectedItem()!=null &&!spinner.getSelectedItem().toString().contains("Please Select")) {
            return true;
        }
        return false;
    }

    public static String checkNullValue(String item) {
        if (item == null || "null".equalsIgnoreCase(item) || "".equals(item.trim()) ||
                "Please Select".equalsIgnoreCase(item.trim())) {
            item = " ---";
        }
        return item.trim();
    }


    public static String checkNullLatLong(String item) {
        if (item == null || "null".equalsIgnoreCase(item) || "".equals(item.trim())) {
            return null;
        }
        return item.trim();
    }


    public static boolean isValidDecimalNumber(String input) {
        if (input.matches("^[0-9]+(\\.[0-9]*)?$"))
            return true;
        return false;
    }


    public static boolean isValidMobileNumber(String mobile) {
        boolean isValid = true;
        try {
            int seriesNum = Integer.parseInt(mobile.substring(0, 4));
            if (seriesNum >= 7000 && seriesNum <= 9999) {
                isValid = true;
            } else {
                isValid = false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return isValid;
    }


    public static boolean pincodeValidation(EditText codeText, String numberErrorMessage) {
        Pattern mobNO = Pattern.compile("\\d{6}");
        Matcher matcher = mobNO.matcher(codeText.getText().toString());
        if (matcher.find()) {
            return true;
        } else if (!"".equalsIgnoreCase(codeText.getText().toString().trim())) {
            codeText.setFocusableInTouchMode(true);
            codeText.requestFocus();
            codeText.setError(numberErrorMessage);
            return false;
        }
        return true;
    }

    public static boolean emailValidation(EditText emailText, String emailErrorMessage) {

        Pattern email = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
        Matcher matcher = email.matcher(emailText.getText().toString());
        if (matcher.find()) {
            return true;
        } else if (!"".equalsIgnoreCase(emailText.getText().toString().trim())) {
            emailText.setFocusableInTouchMode(true);
            emailText.requestFocus();
            emailText.setError(emailErrorMessage);
            return false;
        }
        return true;
    }

    public static boolean emailValidation( String emailAdd) {

        if(emailAdd!=null) {
            Pattern email = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
            Matcher matcher = email.matcher(emailAdd.trim());
            if (matcher.find()) {
                return true;
            } else if (!"".equalsIgnoreCase(emailAdd.trim())) {
                return false;
            }
            return false;
        }
        return false;
    }


    public static void setEditTextLimit(EditText editText,int maxLength){
        if(editText!=null){
            editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        }
    }

}
