package com.fama.app.util;

import android.util.Log;

/**
 * Created by ist on 27/4/16.
 */
public class AppLog {
    private static final boolean DEBUG = true;

    public static void d(String tag, String string) {
        if (DEBUG) {
            Log.d(tag, string);
        }
    }

    public static void v(String tag, String string) {
        if (DEBUG) {
            Log.v(tag, string);
        }
    }

    public static void i(String tag, String string) {
        if (DEBUG) {
            Log.i(tag, string);
        }
    }

    public static void e(String tag, String string) {
        if (DEBUG) {
            Log.e(tag, string);
        }
    }

    public static void w(String tag, String string) {
        if (DEBUG) {
            Log.w(tag, string);
        }
    }

}