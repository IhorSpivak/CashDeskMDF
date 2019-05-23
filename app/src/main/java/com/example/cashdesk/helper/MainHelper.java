package com.example.cashdesk.helper;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

public class MainHelper {
    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }catch (Exception ignored){}
    }

    public static void setLocale(Context context, Locale locale) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(locale);
        res.updateConfiguration(conf, dm);
    }

    public static void showSoftKeyboard(Activity activity, View view) {
        try {
            InputMethodManager keyboard = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.showSoftInput(view, 0);
        }catch (Exception ignored){}
    }
}
