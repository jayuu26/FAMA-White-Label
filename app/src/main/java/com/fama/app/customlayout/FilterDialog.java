package com.fama.app.customlayout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.fama.app.R;
import com.fama.app.constant.CollectionObject;

/**
 * Created by ist on 2/9/16.
 */
public class FilterDialog extends Dialog implements View.OnClickListener {

    public FilterDialog(Context context, DialogOnClickListener myclick) {
        super(context);
        this.myListener = myclick;
        mContext = context;
    }

    public DialogOnClickListener myListener;
    public static Spinner spinner;
    public Context mContext;
    AutoCompleteTextView mAutoCompleteTextView;
    private String countrycode;

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ok:
                setCountrycode(""+mAutoCompleteTextView.getText());
                myListener.onOkButtonClick();
                break;
            case R.id.cancel:
                myListener.onCancelButtonClick();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter);

        ImageView ok = (ImageView) findViewById(R.id.ok);
        ImageView cancel = (ImageView) findViewById(R.id.cancel);
       /* spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(dataAdapter);*/


        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        mAutoCompleteTextView.setThreshold(1);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item, CollectionObject.countryName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAutoCompleteTextView.setAdapter(dataAdapter);

        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP | Gravity.RIGHT;
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        ok.setOnClickListener(this);cancel.setOnClickListener(this);
    }

    // This is my interface //
    public interface DialogOnClickListener {
        void onOkButtonClick();
        void onCancelButtonClick();
    }

    public String getCountrycode() {
        countrycode = CollectionObject.counrtyCode.get(""+countrycode);
        return countrycode;
    }

    public void setCountrycode(String countrycode) {

        this.countrycode = countrycode;
    }
}
