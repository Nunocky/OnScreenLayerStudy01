package org.nunocky.onscreenlayerstudy01;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import de.greenrobot.event.EventBus;

public class LayerService extends Service {
    View view;
    WindowManager wm;
    int gravity;

    public LayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        if (view == null) {
            initialize();
            placeView();
        }


        // onStartCommandの戻り値
        // http://yuki312.blogspot.jp/2012/07/androidserviceonstartcommand.html
        return START_STICKY;
    }

    private void initialize() {
        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.overlay, null);
        gravity = Gravity.CENTER;

        // MEMO: 周期的に実行する処理の登録など
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wm.removeView(view);
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(GravityChangeEvent ev) {
        this.gravity = ev.gravity;
        placeView();
    }

    private void placeView() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = gravity;

        if (view.getParent() != null) {
            wm.removeView(view);
        }
        wm.addView(view, params);
    }

}
