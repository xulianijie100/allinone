package com.beeboxes.ot;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beeboxes.ot.net.RetrofitHelper;
import com.beeboxes.ot.util.NetUtils;
import com.beeboxes.ot.util.SpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerSetActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edit_ip;
    private EditText edit_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_set);
        initToolbar();
        iniView();
    }

    private void iniView() {
        ImageView server_ip_delete = findViewById(R.id.server_ip_delete);
        ImageView server_port_delete = findViewById(R.id.server_port_delete);
        server_ip_delete.setOnClickListener(this);
        server_port_delete.setOnClickListener(this);
        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        edit_ip = findViewById(R.id.edit_ip);
        edit_port = findViewById(R.id.edit_port);

        String ip = SpUtil.readString(this, "server_ip");
        String port = SpUtil.readString(this, "server_port");
        if (!TextUtils.isEmpty(ip)) {
            edit_ip.setText(ip);
            edit_ip.setSelection(ip.length());
        }
        if (!TextUtils.isEmpty(port)) {
            edit_port.setText(port);
        }
    }


    private void initToolbar() {
        TextView tv_title = findViewById(R.id.tv_main_title);
        tv_title.setText("地址设置");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                String ip = edit_ip.getText().toString();
                String port = edit_port.getText().toString();
                if (TextUtils.isEmpty(ip)) {
                    toast(getResources().getString(R.string.ip_empty));
                    return;
                }
                if (!NetUtils.ipRegex(ip)) {
                    toast(getResources().getString(R.string.ip_address_tip));
                    return;
                }
                if (TextUtils.isEmpty(port)) {
                    toast(getResources().getString(R.string.port_empty));
                    return;
                }
                SpUtil.writeString(this, "server_ip", ip);
                SpUtil.writeString(this, "server_port", port);
                RetrofitHelper.getInstance().changeBaseUrl(ip, port);
                String serverIp = NetUtils.getLocalIPAddress().getHostAddress();
                String path= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"head.jpg";
                path = NetUtils.imageToBase64(path);
                uploadImg(serverIp,path);
                finish();
                break;

            case R.id.server_ip_delete:
                edit_ip.setText("");
                break;
            case R.id.server_port_delete:
                edit_port.setText("");
                break;
            default:
                break;
        }
    }

    public void toast(String str) {
        Toast.makeText(ServerSetActivity.this, str, Toast.LENGTH_SHORT).show();
    }


    public void uploadImg(String ip, String img) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("requestIP", ip);
            jsonObject.put("visitorUserImg", img);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("param--",jsonObject.toString());
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<ResponseBody> call = RetrofitHelper.getInstance().getApiService().getValidationInfo(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("result--",response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
