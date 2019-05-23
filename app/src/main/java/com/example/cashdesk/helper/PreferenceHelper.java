package com.example.cashdesk.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class PreferenceHelper {

    private static final String SETTINGS_NAME = "default_settings";
    private static PreferenceHelper sSharedPreferencesHelper;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;



    public enum Key {
        FIRST_LOADING, IS_LOGIN, GENDER, KEY_USER, AUTH_KEY, NAME, EMAIL, STORE, STORE_NAME,LOCALE,CURRENCY, SHOP_TYPE, PRODUCT_ID,
        RULES_TEXT, PHOTO_LINK,WITH_SALE,ID_ORDER,PHONE,DISCOUNT_VALUE,DISCOUNT_IMAGE,DISCOUNT_MILES,DISCOUNT_MESSAGE,BIRTHDATE,SECOND_NAME,FLAG_NEWS,FLIGHT, SOCIAL_LOGIN, USER_URL_LINK, PROMOCODE, TIME_STAMP, IS_RATING, IS_SHOW_BANNER,IS_RATING_READY
    }

    private PreferenceHelper(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }



    public static PreferenceHelper getInstance(Context context) {
        if (sSharedPreferencesHelper == null) {
            sSharedPreferencesHelper = new PreferenceHelper(context);
        }
        return sSharedPreferencesHelper;
    }

    public static PreferenceHelper getInstance() {
        if (sSharedPreferencesHelper != null) {
            return sSharedPreferencesHelper;
        }
        throw new IllegalArgumentException("Should use initialize(Context) at least once before using this method.");
    }

    public void put(Key key, String val) {
        doEdit();
        mEditor.putString(key.name(), val);
        doCommit();
    }

    public void put(Key key, JsonObject val) {
        doEdit();
        mEditor.putString(key.name(), val.toString());
        doCommit();
    }

    public void put(Key key, int val) {
        doEdit();
        mEditor.putInt(key.name(), val);
        doCommit();
    }

    public void put(Key key, boolean val) {
        doEdit();
        mEditor.putBoolean(key.name(), val);
        doCommit();
    }

    public void put(Key key, float val) {
        doEdit();
        mEditor.putFloat(key.name(), val);
        doCommit();
    }

    public void put(Key key, double val) {
        doEdit();
        mEditor.putString(key.name(), String.valueOf(val));
        doCommit();
    }

    public void put(Key key, long val) {
        doEdit();
        mEditor.putLong(key.name(), val);
        doCommit();
    }

    public String getString(Key key, String defaultValue) {
        return mPref.getString(key.name(), defaultValue);
    }

    public String getString(Key key) {
        return mPref.getString(key.name(), null);
    }

    public int getInt(Key key) {
        return mPref.getInt(key.name(), 0);
    }

    public int getInt(Key key, int defaultValue) {
        return mPref.getInt(key.name(), defaultValue);
    }

    public long getLong(Key key) {
        return mPref.getLong(key.name(), 0);
    }

    public long getLong(Key key, long defaultValue) {
        return mPref.getLong(key.name(), defaultValue);
    }

    public float getFloat(Key key) {
        return mPref.getFloat(key.name(), 0);
    }

    public float getFloat(Key key, float defaultValue) {
        return mPref.getFloat(key.name(), defaultValue);
    }

    public double getDouble(Key key) {
        return getDouble(key, 0);
    }

    public double getDouble(Key key, double defaultValue) {
        try {
            return Double.valueOf(mPref.getString(key.name(), String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public JsonObject getJsonObject(Key key) {
        Gson gson = new Gson();
        return gson.fromJson(mPref.getString(key.name(),null),JsonObject.class);
    }



    public boolean getBoolean(Key key, boolean defaultValue) {
        return mPref.getBoolean(key.name(), defaultValue);
    }

    public boolean getBoolean(Key key) {
        return mPref.getBoolean(key.name(), false);
    }



    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }


    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The enum of the key(s) to be removed.
     */
    public void remove(Key... keys) {
        doEdit();
        for (Key key : keys) {
            mEditor.remove(key.name());
        }
        doCommit();
    }


    public void clear() {
        doEdit();
        mEditor.clear();
        doCommit();
    }

    public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        mEditor.commit();
        mEditor = null;
    }

    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }
}
