package org.nunocky.onscreenlayerstudy01;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class LayerService extends Service {
    View view;
    WindowManager wm;

    public LayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //    return super.onStartCommand(intent, flags, startId);
        LayoutInflater inflater = LayoutInflater.from(this);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );

        view = inflater.inflate(R.layout.overlay, null);

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.addView(view, params);

        // onStartCommandの戻り値
        // http://yuki312.blogspot.jp/2012/07/androidserviceonstartcommand.html
        return START_STICKY;
    }

//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wm.removeView(view);
    }
}
