package com.beeboxes.ot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beeboxes.BBoxUtils;
import com.beeboxes.settingsmanager.SettingsManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ODSLConfigActivity extends AppCompatActivity {
    @BindView(R.id.et_card_verify_failed)
    EditText mEtCardVerityFailed;
    @BindView(R.id.et_after_11_failed)
    EditText mEtAfter11Failed;
    @BindView(R.id.et_after_11_success)
    EditText mEtAfter11Success;
    @BindView(R.id.et_after_1n_failed)
    EditText mEtAfter1nFailed;
    @BindView(R.id.et_after_1n_success)
    EditText mEtAfter1nSuccess;
    @BindView(R.id.et_after_finger_success)
    EditText mEtAfterFingerSuccess;
    @BindView(R.id.et_after_finger_failed)
    EditText mEtAfterFingerFailed;
    @BindView(R.id.cb_pic)
    CheckBox mPicture;
    @BindView(R.id.cb_feature)
    CheckBox mFeature;
    @BindView(R.id.btn_back)
    Button mBtnBack;
    @BindView(R.id.btn_set)
    Button mBtnSet;
    //    @BindView(R.id.cb_provider_return)
//    CheckBox mProviderReturn;
    @BindView(R.id.sp_return)
    Spinner mSpProviderReture;
    @BindView(R.id.et_provider_return_message)
    EditText mEtProviderReturnMsg;
    @BindView(R.id.cb_activity_auto_return)
    CheckBox mActivityAutoReturn;
    @BindView(R.id.cb_activity_return)
    CheckBox mActivityReturn;
    @BindView(R.id.et_activity_return_message)
    EditText mEtActivityReturnMsg;

    private SettingsManager mSm;
    private ArrayAdapter<String> mAdapter;
    private SpUtils mSpUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.odsl_config);
        ButterKnife.bind(this);
        initToolbar();
        mSpUtils = new SpUtils(this);
        String[] mItems = getResources().getStringArray(R.array.provider_ret);
        mAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, mItems);
        mSpProviderReture.setAdapter(mAdapter);
        mSm = SettingsManager.getInstance();
        mEtCardVerityFailed.setText(String.valueOf(mSm.getInt(BBoxUtils.ODSL_MSG_DISPLAY_TIME_CARD_VERITY_FAILED, 0)));
        mEtAfter11Failed.setText(String.valueOf(mSm.getInt(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_11_FAILED, 0)));
        mEtAfter11Success.setText(String.valueOf(mSm.getInt(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_11_SUCCEEDED, 0)));
        mEtAfter1nFailed.setText(String.valueOf(mSm.getInt(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_1N_FAILED, 0)));
        mEtAfter1nSuccess.setText(String.valueOf(mSm.getInt(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_1N_SUCCEEDED, 0)));
        mEtAfterFingerFailed.setText(String.valueOf(mSm.getInt(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_FINGERPRINT_FAILED, 0)));
        mEtAfterFingerSuccess.setText(String.valueOf(mSm.getInt(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_FINGERPRINT_SUCCEEDED, 0)));
        mPicture.setChecked(mSm.getBoolean(BBoxUtils.ODSL_AFTER_MATCH_WITH_PIC, false));
        mFeature.setChecked(mSm.getBoolean(BBoxUtils.CORE_FR_MATCH_SAVE_FEATURE, false));
        mSpProviderReture.setSelection(mSpUtils.getSpInt(SpUtils.SP_KEY_PROVIDER_RET, 0));
        mEtProviderReturnMsg.setText(mSpUtils.getSpString(SpUtils.SP_KEY_PROVIDER_RET_MSG, ""));
        mActivityReturn.setChecked(mSpUtils.getSpBoolean(SpUtils.SP_KEY_ACTIVITY_RET, false));
        mActivityAutoReturn.setChecked(mSpUtils.getSpBoolean(SpUtils.SP_KEY_ACTIVITY_AUTO_RET, false));
        mEtActivityReturnMsg.setText(mSpUtils.getSpString(SpUtils.SP_KEY_ACTIVITY_RET_MSG, ""));

    }

    @OnClick({
            R.id.btn_set,
            R.id.btn_back,
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set:
                mSm.set(BBoxUtils.ODSL_MSG_DISPLAY_TIME_CARD_VERITY_FAILED, mEtCardVerityFailed.getText().toString());
                mSm.set(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_11_FAILED, mEtAfter11Failed.getText().toString());
                mSm.set(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_11_SUCCEEDED, mEtAfter11Success.getText().toString());
                mSm.set(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_1N_FAILED, mEtAfter1nFailed.getText().toString());
                mSm.set(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_1N_SUCCEEDED, mEtAfter1nSuccess.getText().toString());
                mSm.set(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_FINGERPRINT_FAILED, mEtAfterFingerFailed.getText().toString());
                mSm.set(BBoxUtils.ODSL_MSG_DISPLAY_TIME_AFTER_FINGERPRINT_SUCCEEDED, mEtAfterFingerSuccess.getText().toString());
                mSpUtils.setSpString(SpUtils.SP_KEY_PROVIDER_RET_MSG, mEtProviderReturnMsg.getText().toString());
                mSpUtils.setSpString(SpUtils.SP_KEY_ACTIVITY_RET_MSG, mEtActivityReturnMsg.getText().toString());
                mSpUtils.setSpInt(SpUtils.SP_KEY_PROVIDER_RET, mSpProviderReture.getSelectedItemPosition());
                Toast.makeText(ODSLConfigActivity.this, "设置成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_back:
                finish();
                break;

        }
    }

    @OnCheckedChanged({
            R.id.cb_pic,
            R.id.cb_feature,
            R.id.cb_activity_auto_return,
            R.id.cb_activity_return,
    })
    void onCheckChanged(CheckBox cb, boolean checked) {
        SettingsManager sm = SettingsManager.getInstance();
        switch (cb.getId()) {
            case R.id.cb_feature:
                sm.set(BBoxUtils.CORE_FR_MATCH_SAVE_FEATURE, String.valueOf(checked));
                break;
            case R.id.cb_pic:
                sm.set(BBoxUtils.ODSL_AFTER_MATCH_WITH_PIC, String.valueOf(checked));
                break;
            case R.id.cb_activity_auto_return:
                mSpUtils.setSpBoolean(SpUtils.SP_KEY_ACTIVITY_AUTO_RET, checked);
                break;
            case R.id.cb_activity_return:
                mSpUtils.setSpBoolean(SpUtils.SP_KEY_ACTIVITY_RET, checked);
                break;
        }

    }

    private void initToolbar() {
        TextView tv_title=findViewById(R.id.tv_main_title);
        tv_title.setText("设置");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_set_ip:
                startActivity(new Intent(ODSLConfigActivity.this, ServerSetActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
