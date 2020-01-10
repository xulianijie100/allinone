package com.beeboxes.ot;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SpUtils {
    public static final String SP_KEY_PROVIDER_RET = "ret_provider_ret";
    public static final String SP_KEY_PROVIDER_RET_MSG = "ret_provider_message";
    public static final String SP_KEY_ACTIVITY_AUTO_RET = "ret_activity_auto";
    public static final String SP_KEY_ACTIVITY_RET = "ret_activity_boolean";
    public static final String SP_KEY_ACTIVITY_RET_MSG = "ret_activity_message";
    private Context mContext;

    public SpUtils(Context context) {
        mContext = context;
    }

    public void setSpBoolean(String key, boolean value) {
        SharedPreferences mSp = mContext.getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = mSp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getSpBoolean(String key, boolean def) {
        SharedPreferences mSp = mContext.getSharedPreferences("test", MODE_PRIVATE);
        return mSp.getBoolean(key, def);
    }

    public void setSpString(String key, String value) {
        SharedPreferences mSp = mContext.getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = mSp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getSpString(String key, String def) {
        SharedPreferences mSp = mContext.getSharedPreferences("test", MODE_PRIVATE);
        return mSp.getString(key, def);
    }

    public void setSpInt(String key, int value) {
        SharedPreferences mSp = mContext.getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = mSp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getSpInt(String key, int def) {
        SharedPreferences mSp = mContext.getSharedPreferences("test", MODE_PRIVATE);
        return mSp.getInt(key, def);
    }
}
