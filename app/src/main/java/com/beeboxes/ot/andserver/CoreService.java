
package com.beeboxes.ot.andserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.beeboxes.ot.util.NetUtils;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;

import java.util.concurrent.TimeUnit;

public class CoreService extends Service {

    private Server mServer;

    @Override
    public void onCreate() {
        mServer = AndServer.serverBuilder(this)
            .inetAddress(NetUtils.getLocalIPAddress())
            .port(8000)
            .timeout(10, TimeUnit.SECONDS)
            .listener(new Server.ServerListener() {
                @Override
                public void onStarted() {
                    String hostAddress = mServer.getInetAddress().getHostAddress();
                    ServerManager.onServerStart(CoreService.this, hostAddress);
                }

                @Override
                public void onStopped() {
                    ServerManager.onServerStop(CoreService.this);
                }

                @Override
                public void onException(Exception e) {
                    ServerManager.onServerError(CoreService.this, e.getMessage());
                }
            })
            .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startServer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopServer();
        super.onDestroy();
    }

    /**
     * Start server.
     */
    private void startServer() {
        if (mServer.isRunning()) {
            String hostAddress = mServer.getInetAddress().getHostAddress();
            ServerManager.onServerStart(CoreService.this, hostAddress);
        } else {
            mServer.startup();
        }
    }

    /**
     * Stop server.
     */
    private void stopServer() {
        mServer.shutdown();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}