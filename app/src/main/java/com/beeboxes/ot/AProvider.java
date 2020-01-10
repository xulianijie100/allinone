package com.beeboxes.ot;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.beeboxes.odsl.ODSLMatchInfo;
import com.beeboxes.ot.net.RetrofitHelper;
import com.beeboxes.ot.util.NetUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.beeboxes.ot.SpUtils.SP_KEY_PROVIDER_RET;
import static com.beeboxes.ot.SpUtils.SP_KEY_PROVIDER_RET_MSG;

public class AProvider extends ContentProvider {
    private static final String TAG = "##ODSL TEST##";
    private  MatrixCursor cursor;

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        try {
            ODSLMatchInfo info = null;

            if (strings1 == null) {
                Log.e(TAG, "selectionArgs is null");
            } else if (strings1.length >= 1) {
                Log.e(TAG, strings1[0]);
                info = jsonToObject(strings1[0], ODSLMatchInfo.class);
                Log.e(TAG, "ODSLMatchInfo:" + info.toString());
                String pic = info.getPicPath();
                String ip = NetUtils.getLocalIPAddress().getHostAddress();
                Log.e(TAG, "ip==" + ip);
                pic = NetUtils.imageToBase64(pic);
                if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(pic)) {
                    uploadImg(ip, pic);
                }
            }

            String[] columns = new String[]{"result"};
            cursor = new MatrixCursor(columns);
            SpUtils sp = new SpUtils(getContext());

            String msg = sp.getSpString(SP_KEY_PROVIDER_RET_MSG, "");
            switch (sp.getSpInt(SP_KEY_PROVIDER_RET, 0)) {
                case 0:
                    cursor.addRow(new Object[]{"{" +
                            "'result': 'true'," +
                            "'message':'" + msg + "'" +
                            "}"});
                    break;
                case 1:
                    cursor.addRow(new Object[]{"{" +
                            "'result': 'false'," +
                            "'message':'" + msg + "'" +
                            "}"});
                    break;
                case 2:
                    long sleepTime = 10000;
                    if (info != null) {
                        sleepTime = info.getAction().getTimeout() + 1000;
                    }
                    Thread.sleep(sleepTime);
                    break;
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
        Gson gson = new Gson();
        T object = gson.fromJson(json, classT);
        return object;
    }


    public void uploadImg(String ip, String img) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("requestIP", ip);
            jsonObject.put("visitorUserImg", img);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<ResponseBody> call = RetrofitHelper.getInstance().getApiService().getValidationInfo(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                cursor.addRow(new Object[]{"{" +
                        "'result': 'false'," +
                        "'message':'网络异常'" +
                        "}"});
            }
        });
    }
}
