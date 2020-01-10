package com.beeboxes.ot.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.beeboxes.ot.BuildConfig;

public class SpUtil {

    public static String readString(Context ctx, String key) {
        return getSharedPreferences(ctx).getString(key, "");
    }

    public static void writeString(Context ctx, String key, String value) {
        getSharedPreferences(ctx).edit().putString(key, value).apply();
    }

    public static boolean readBoolean(Context ctx, String key) {
        return getSharedPreferences(ctx).getBoolean(key, false);
    }

    public static void writeBoolean(Context ctx, String key, boolean value) {
        getSharedPreferences(ctx).edit().putBoolean(key, value).apply();
    }

    public static int readInt(Context ctx, String key) {
        return getSharedPreferences(ctx).getInt(key, 0);
    }

    public static int readInt(Context ctx, String key, int value) {
        return getSharedPreferences(ctx).getInt(key, value);
    }

    public static void writeInt(Context ctx, String key, int value) {
        getSharedPreferences(ctx).edit().putInt(key, value).apply();
    }

    public static long readLong(Context ctx, String key) {
        return getSharedPreferences(ctx).getLong(key, 0);
    }

    public static void writeLong(Context ctx, String key, long value) {
        getSharedPreferences(ctx).edit().putLong(key, value).apply();
    }

    public static void remove(Context ctx, String key) {
        getSharedPreferences(ctx).edit().remove(key).apply();
    }

    public static void removeAll(Context ctx) {
        getSharedPreferences(ctx).edit().clear().apply();
    }

    private static SharedPreferences getSharedPreferences(Context ctx) {
        return ctx.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }
}
