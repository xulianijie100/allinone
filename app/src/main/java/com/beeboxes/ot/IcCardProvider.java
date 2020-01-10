package com.beeboxes.ot;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.beeboxes.device.Device;
import com.beeboxes.device.input.devices.CpuCardA;
import com.beeboxes.device.input.devices.CpuCardB;
import com.beeboxes.device.input.devices.M1Card;
import com.beeboxes.odsl.ODSLCardConverertInfo;
import com.google.gson.Gson;

public class IcCardProvider extends ContentProvider {
    private static final String TAG = "##IcCardProvider##";

    final static Gson sGson = new Gson();

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
            @Nullable String sortOrder) {
        try {
            ODSLCardConverertInfo info = null;
            if (selectionArgs == null) {
                Log.e(TAG, "selectionArgs is null");
            } else if (selectionArgs.length >= 1) {
                info = jsonToObject(selectionArgs[0], ODSLCardConverertInfo.class);
                Log.d(TAG, "CardConvertInfo:" + selectionArgs[0]);
            }

            String[] columns = new String[]{"result"};
            MatrixCursor cursor = new MatrixCursor(columns);
            String cardNo = null;
            if (info != null) {
                cardNo = convertCard(info);
            }
            if (cardNo != null) {
                Log.d(TAG, "cardNo = " + cardNo);
                cursor.addRow(new Object[]{"{" +
                        "'result': 'true'," +
                        "'message':'" + cardNo + "'" +
                        "}"});
            } else {
                cursor.addRow(new Object[]{"{" +
                        "'result': 'false'," +
                        "'message':''" +
                        "}"});
            }
            return cursor;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    public static <T> T jsonToObject(String json, Class<T> classT) {
        return sGson.fromJson(json, classT);
    }

    private static String convertCard(ODSLCardConverertInfo info) {
        Object cardObject = info.getCardObj();
        String cardString = sGson.toJson(cardObject);
        Device.Input input = info.getInputType();
        return convertCard(cardString, input);

    }

    private static String convertCard(String cardString, Device.Input type) {
        String cardNo;
        switch (type) {
            case M1_CARD:
                cardNo = bytesToOctString((jsonToObject(cardString, M1Card.class)).getUid());
                break;
            case CPU_A_CARD:
                cardNo = bytesToOctString((jsonToObject(cardString, CpuCardA.class)).getUid());
                break;
            case CPU_B_CARD:
                cardNo = bytesToOctString((jsonToObject(cardString, CpuCardB.class)).getUid());
                break;
            default:
                cardNo = null;
        }
        return cardNo;
    }

    private static String bytesToOctString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length < 2) {
            return null;
        }

        for (int i = bytes.length - 2; i >= 0; i--) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }

            stringBuilder.append(hv.toUpperCase());
        }

        try {
            return String.valueOf(Long.parseLong(stringBuilder.toString(), 16));
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
