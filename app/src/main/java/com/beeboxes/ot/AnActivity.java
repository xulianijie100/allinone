package com.beeboxes.ot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.beeboxes.odsl.ODSLMatchInfo;
import com.google.gson.Gson;

import static com.beeboxes.ot.SpUtils.SP_KEY_ACTIVITY_AUTO_RET;
import static com.beeboxes.ot.SpUtils.SP_KEY_ACTIVITY_RET;
import static com.beeboxes.ot.SpUtils.SP_KEY_ACTIVITY_RET_MSG;

public class AnActivity extends AppCompatActivity {
    private static final String TAG = "##ODSL TEST##";

    private static final int MSG_FINISH = 0;
    SpUtils mSpUtils;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FINISH:
                    boolean ret = mSpUtils.getSpBoolean(SP_KEY_ACTIVITY_RET, false);
                    String message = mSpUtils.getSpString(SP_KEY_ACTIVITY_RET_MSG, "");
                    if (ret) {
                        Intent intent1 = new Intent();
                        intent1.putExtra("result", true);
                        intent1.putExtra("message", message);
                        setResult(Activity.RESULT_OK, intent1);
                        finish();
                    } else {
                        Intent intent1 = new Intent();
                        intent1.putExtra("result", false);
                        intent1.putExtra("message", message);
                        setResult(Activity.RESULT_OK, intent1);
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an);

        Intent intent = getIntent();
        String json = intent.getStringExtra("json");

        Log.e(TAG, "json:" + json);
        ODSLMatchInfo info  = jsonToObject(json, ODSLMatchInfo.class);

        TextView textView = (TextView) findViewById(R.id.message);

        textView.setText(info.toString());

        mSpUtils = new SpUtils(this);
        final String message = mSpUtils.getSpString(SP_KEY_ACTIVITY_RET_MSG, "");
        if (mSpUtils.getSpBoolean(SP_KEY_ACTIVITY_AUTO_RET, false)) {
            mHandler.sendEmptyMessageDelayed(MSG_FINISH, 5000);

        } else {
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent();
                    intent1.putExtra("result", true);
                    intent1.putExtra("message", message);
                    setResult(Activity.RESULT_OK, intent1);
                    finish();
                }
            });

            FloatingActionButton fabf = findViewById(R.id.fabf);
            fabf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent();
                    intent1.putExtra("result", false);
                    intent1.putExtra("message", message);
                    setResult(Activity.RESULT_OK, intent1);
                    finish();
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_an, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static <T> T jsonToObject(String json, Class<T> classT) {
        Gson gson = new Gson();
        T object = gson.fromJson(json, classT);
        return object;
    }
}
