package org.nunocky.onscreenlayerstudy01;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {

    private final Button.OnClickListener mOnClickListener = new View.OnClickListener() {
        int gravity;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:
                    // LEFT TOP
                    gravity = Gravity.START | Gravity.TOP;
                    break;
                case R.id.button2:
                    gravity = Gravity.END | Gravity.TOP;
                    // RIGHT TOP
                    break;
                case R.id.button3:
                    // LEFT BOTTOM
                    gravity = Gravity.START | Gravity.BOTTOM;
                    break;
                case R.id.button4:
                    // RIGHT BOTTOM
                    gravity = Gravity.END | Gravity.BOTTOM;
                    break;
                case R.id.button5:
                    // CENTER
                    gravity = Gravity.CENTER;
                    break;
                default:
                    break;
            }

            EventBus.getDefault().post(new GravityChangeEvent(gravity));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);

        button1.setOnClickListener(mOnClickListener);
        button2.setOnClickListener(mOnClickListener);
        button3.setOnClickListener(mOnClickListener);
        button4.setOnClickListener(mOnClickListener);
        button5.setOnClickListener(mOnClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (isMyServiceRunning(LayerService.class))
                stopService(new Intent(MainActivity.this, LayerService.class));
            else
                startService(new Intent(MainActivity.this, LayerService.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, LayerService.class));
    }

    //

    /**
     * サービスが稼働中か否か
     * -> http://stackoverflow.com/questions/600207/how-to-check-if-a-service-is-running-on-android
     *
     * @param serviceClass 確認したいサービスのクラス
     * @return 稼働中なら true
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
