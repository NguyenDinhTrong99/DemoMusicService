package com.trongdeptrai.demoappmusic.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.trongdeptrai.demoappmusic.utils.MusicController;

@SuppressLint("Registered")
public class MusicService extends Service {
    @SuppressLint("StaticFieldLeak")
    public static MusicController mController;
    @Override
    public void onCreate() {
        super.onCreate();
        mController = new MusicController(this);
    }
    // start service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        mController.play();
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mController.stop();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        mController.stop();
        super.onDestroy();
    }
}
