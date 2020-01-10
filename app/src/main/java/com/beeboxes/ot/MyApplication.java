
package com.beeboxes.ot;

import android.app.Application;

import com.beeboxes.ot.andserver.ServerManager;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mInstance == null) {
            mInstance = this;
        }

        ServerManager mServerManager = new ServerManager(this);
        mServerManager.register(this);
        mServerManager.startServer(this);
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
}