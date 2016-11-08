package com.fama.app.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceHelper {
    public static String LOGIN_STATUS_KEY = "loginstatus";
    public static String INVENTORY_ID_KEY = "inventoryId";
    public static String INVENTORY_NAME_KEY = "inventoryName";//R
    public static String INVENTORY_DETAIL_KEY = "inventoryDetail";//R

    public static String CURRENT_APK_VERSION = "currentApkVersion";
    public static String IS_SQL_SCRIPT_EXECUTED = "isSqlScriptExecuted";

    private SharedPreferences sharedPreferences;
    private Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    public PreferenceHelper(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mEditor = sharedPreferences.edit();
    }

    public void saveStringPreference(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public String getStringPreference(String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean getBooleanPreference(String key) {
        return sharedPreferences.getBoolean(key, false);
    }


    public boolean getLoginPreferences(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public long getINVENTORYIdPreferences(String key) {
        return sharedPreferences.getLong(key, 0);
    }


    public void savePreferences(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public void savePreferences(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public String getLoginDetailPreferences(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void saveBooleanPreference(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }
}
