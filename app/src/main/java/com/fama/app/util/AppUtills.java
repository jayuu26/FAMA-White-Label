package com.fama.app.util;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.fama.app.R;
import com.fama.app.constant.AppConstants;
import com.fama.app.constant.CollectionObject;
import com.fama.app.daomodel.CountryCode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ist on 25/8/16.
 */


public class AppUtills {

    private  String TAG = getClass().getName();
    private static char[] chunkBuffer = new char[1024];

    public synchronized static String readData(InputStreamReader rd) {
        try {
            StringBuffer sb = new StringBuffer();
            while (true) {
                int read = rd.read(chunkBuffer, 0, chunkBuffer.length);
                if (read == -1) {
                    break;
                }
                sb.append(chunkBuffer, 0, read);
            }
            return sb.toString();
        } catch (IOException e) {
        } finally {
            try {
                rd.close();
            } catch (IOException e) {
            }
        }
        return "";
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void loadFragment(Fragment loadFragment, FragmentActivity activity, int id) {
        if (loadFragment != null) {
            activity.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if (!loadFragment.isAdded()) {
                activity.getSupportFragmentManager().beginTransaction().addToBackStack("tag")
                        .replace(id, loadFragment)
                        .commitAllowingStateLoss();
            }
        }
    }

    public static void loadRefreshViewFragment(Fragment loadFragment, FragmentActivity activity, int id) {
        if (loadFragment != null) {
            if (!loadFragment.isAdded()) {
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(id, loadFragment)
                        .detach(loadFragment)
                        .attach(loadFragment)
                        .commit();
            }
            // Reload current fragment
        }
    }

    public static void removeFragment(Fragment fragment, FragmentActivity activity) {

        if (fragment != null && fragment.isAdded())
            activity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public static void hideShowFrag(Fragment hideFrag, Fragment loadFrag, FragmentActivity activity, int container_id) {

        if (!loadFrag.isAdded()) {
            activity.getSupportFragmentManager().beginTransaction().hide(hideFrag)
                    .add(container_id, loadFrag)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }

    }

    public static void loadChildFragment(Fragment parentFrag, Fragment childFrag, FragmentActivity activity, int container_id) {

        if(!childFrag.isAdded()) {
            FragmentManager childFragMan = parentFrag.getChildFragmentManager();
            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
            childFragTrans.add(container_id, childFrag);
            childFragTrans.addToBackStack("" + childFrag.getTag());
            childFragTrans.commit();
        }
    }

    public static void loadImage(Context mContext, ImageView view, String url) {
        Glide.with(mContext).load(url).into(view);
    }


    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    public static void hideKeybord(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static ProgressDialog showProgressDialog(Context context, String title, String msg) {
        ProgressDialog _progress = ProgressDialog.show(context, title, msg);
        return _progress;
    }

    /**
     * method for dismissing progress dialog
     *
     * @param _progress
     */
    public static void hideProgressDialog(ProgressDialog _progress) {
        if (_progress != null && _progress.isShowing())
            _progress.dismiss();

    }

    public static ProgressDialog showProgressDialog(Activity activity) {
        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        return mProgressDialog;
    }

    public static void cancelProgressDialog(ProgressDialog mProgressDialog) {

        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }

        mProgressDialog = null;
    }

    public static void loadTextOnWebView(String text, TextView textView, WebView webView) {

        String summary = "<html><FONT style='font-size:14px; font-weight:700; color:#FFFFFF;'>" +
                "<marquee behavior='scroll' direction='left' scrollamount='3'>"
                + text + "</marquee></FONT></html>";
        textView.setVisibility(View.GONE);
        textView.setText(text);
        webView.setBackgroundColor(Color.GRAY);
        webView.loadData(summary, "text/html", "utf-8");
    }


    public static String loadJsonFromAssets(Activity activity, String fileName) {
        String json = null;
        try {
//            InputStream is = activity.getAssets().open(fileName);
            InputStream is = activity.getResources().openRawResource(
                    activity.getResources().getIdentifier(fileName,
                            "raw", activity.getPackageName()));
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<CountryCode> initCountyList(String json) {

        Type listType = new TypeToken<List<CountryCode>>() {
        }.getType();
        ArrayList<CountryCode> parseList = new Gson().fromJson(json, listType);
        for (int i = 0; i < parseList.size(); i++) {
            CollectionObject.countryName.add(i, parseList.get(i).getName());
            CollectionObject.counrtyCode.put(parseList.get(i).getName(), parseList.get(i).getCode());
        }
        return parseList;
    }

    private static final int REQUEST_WRITE_STORAGE = 112;

    public static void exportDatabase(Context context, Activity activity) {
        try {
            boolean hasPermission = (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_STORAGE);
            }
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + context.getPackageName() + "//databases//" + "iTune-db";
                String backupDBPath = "ituneBckup.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);


                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            } else {
                System.out.println("Can not write in SD card");
            }

        } catch (Exception e) {

        }
    }

    public static int toIntExact(long value) {
        if ((int) value != value) {
            throw new ArithmeticException("integer overflow");
        }
        return (int) value;
    }

    public static void showInformativePopup(final Context context, String message, final FragmentActivity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(""/*R.string.info*/);
        builder.setMessage(message)

                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                        activity.onBackPressed();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        final Button neutral = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) neutral.getLayoutParams();
        positiveButtonLL.gravity = Gravity.CENTER;
        neutral.setLayoutParams(positiveButtonLL);
    }

    public static void setUnderLine(TextView textView, String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);

        textView.setText(content);
    }

    public String getAppVersion(Context ctx) {
        PackageInfo pInfo;
        try {
            pInfo = ctx.getPackageManager().getPackageInfo(
                    ctx.getPackageName(), 0);

            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void setActionBarTitle(String title,/*String subTitle,*/ final ActionBar actionBar, final Activity activity, boolean isBackEnable) {
        if(isBackEnable) {
            try {

                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDefaultDisplayHomeAsUpEnabled(true);

                Toolbar toolbar = (Toolbar) activity.findViewById(R.id.action_toolbar);
                toolbar.setNavigationIcon(null);
                toolbar.bringToFront();

                ImageView icon = (ImageView) toolbar.findViewById(R.id.icon);
                icon.setVisibility(View.VISIBLE);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.onBackPressed();
                    }
                });
                TextView heading = (TextView) toolbar.findViewById(R.id.action_heading);
                TextView sub_heading = (TextView) toolbar.findViewById(R.id.sub_heading);
                heading.setText("  " + title.trim());
                //sub_heading.setText("  " + CollectionObject.LOCATION_ADDRESS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                actionBar.setIcon(R.drawable.ic_menu_black_24dp);
                actionBar.setDisplayUseLogoEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
                actionBar.setDisplayShowTitleEnabled(false);

                Toolbar toolbar = (Toolbar) activity.findViewById(R.id.action_toolbar);
                toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

                toolbar.bringToFront();

                ImageView icon = (ImageView) toolbar.findViewById(R.id.icon);
                icon.setVisibility(View.GONE);
                TextView heading = (TextView) toolbar.findViewById(R.id.action_heading);
                TextView sub_heading = (TextView) toolbar.findViewById(R.id.sub_heading);
                heading.setText("  " + title.trim());
                //sub_heading.setText("  " + CollectionObject.LOCATION_ADDRESS);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setActionBarTitle1(String title, ActionBar actionBar, Activity activity, boolean isBackEnable) {
        if (isBackEnable) {
            actionBar.setIcon(null);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.action_toolbar);
            toolbar.setTitle("" + title.trim());

        } else {
            actionBar.setIcon(null);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.action_toolbar);
            if (title != null)
                toolbar.setTitle("" + title.trim());
        }
    }

    public static void openDocFolder(Activity activity, int REQUEST_ID) {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(Intent.createChooser(intent, "Choose File"), REQUEST_ID);
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
            intent.setDataAndType(uri, "*/*");
            activity.startActivityForResult(intent, REQUEST_ID);
        }
    }

    public static void sendSms(Context context) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:"
                + AppConstants.MOBILE_NO));
        smsIntent.putExtra("sms_body", "Feedback @ Jio Fiber+ :-");
        context.startActivity(smsIntent);

    }

    public static void sendEmail(Context context) {
        final Intent emailIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{AppConstants.EMAIL_ID});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Jio Fiber+ Feedback");
        context.startActivity(Intent.createChooser(emailIntent,
                "Jio Fiber+ Feedback"));

    }

    /**
     * @param activity use to hide keyboard on the device.
     */
    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static byte[] readBytes(File file) {

        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return bytes;
    }

    public static void bytesToFile(File file, byte[] byteArray) {
        FileOutputStream fileOuputStream = null;
        try {
            fileOuputStream = new FileOutputStream(file);
            fileOuputStream.write(byteArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOuputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void showErrorPopUp(Activity mContext, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
        builder.setTitle(R.string.error);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        builder.create();
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showErrorPopUpBack(final Activity activity, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
        builder.setTitle(R.string.error);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.onBackPressed();
                    }
                });
        builder.create();
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showExitPopUp(final Activity activity, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
        builder.setTitle(R.string.confirmation);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finish();
                    }
                });
        builder.create();
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showLowBalance(final Activity activity, String msg, final boolean isExit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
        builder.setTitle(R.string.error);
        builder.setCancelable(false);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(isExit)
                           activity.onBackPressed();
                    }
                });
        builder.create();
        AlertDialog dialog = builder.create();

        dialog.show();

    }

    public static String capitalizeName(String name) {
        String fullName = "";
        String names[] = name.split(" ");
        for (String n: names) {
            fullName = fullName + n.substring(0, 1).toUpperCase() + n.toLowerCase().substring(1, n.length()) + " ";
        }
        return fullName;
    }

    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    public static boolean checkPermission(Activity activity,Context mContext, int PERMISSION_REQUEST_CODE, String permission){
        int result = ContextCompat.checkSelfPermission(mContext, permission);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return requestPermission(activity,mContext,PERMISSION_REQUEST_CODE,permission);
        }
    }

    public static boolean requestPermission(Activity activity,Context mContext, int PERMISSION_REQUEST_CODE,String permission){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
            Toast.makeText(mContext,permission+" Allowed.",Toast.LENGTH_LONG).show();
            return true;
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{""+permission/*Manifest.permission.ACCESS_FINE_LOCATION*/},PERMISSION_REQUEST_CODE);
            return false;
        }
    }


}



