package com.beeboxes.ot;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.google.gson.Gson;
import com.opnext.domain.AccessRecord;

public class AService extends Service {
    private static final String TAG = "##ODSL TEST##";

    @SuppressLint("HandlerLeak")
    private Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msgfromClient) {
            try {
                switch (msgfromClient.what) {
                    case 200:
                        Bundle bundle = msgfromClient.getData();
                        String json = bundle.getString("obj");
                        AccessRecord record = jsonToObject(json, AccessRecord.class);
                        Log.e(TAG, record.toString());
                        break;
                }
                super.handleMessage(msgfromClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    public AService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String json = intent.getStringExtra("json");
        Log.e(TAG, json);
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private static <T> T jsonToObject(String json, Class<T> classT) {
        Gson gson = new Gson();
        T object = gson.fromJson(json, classT);
        return object;
    }
}
