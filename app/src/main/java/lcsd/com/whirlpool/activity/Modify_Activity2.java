package lcsd.com.whirlpool.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Modify_Activity2 extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_01, ll_02;
    private TextView btn_xiugai;
    private EditText et_new_code, et_new_code_confirm;
    private Context context;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_2);
        context = this;
        if (getIntent() != null) {
            phone = getIntent().getStringExtra("phone");
        }
        initView();
    }

    private void initView() {
        ll_01 = (LinearLayout) findViewById(R.id.ll_modify);
        ll_02 = (LinearLayout) findViewById(R.id.ll_modifycomplete);
        btn_xiugai = (TextView) findViewById(R.id.btn_xiugai);
        btn_xiugai.setOnClickListener(this);
        et_new_code = (EditText) findViewById(R.id.et_new_code);
        et_new_code_confirm = (EditText) findViewById(R.id.et_new_code_confirm);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_xiugai:
                if (TextUtils.isEmpty(et_new_code.getText().toString())) {
                    Toast.makeText(context, "请输入新密码", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(et_new_code_confirm.getText().toString())) {
                    Toast.makeText(context, "请确认新密码", Toast.LENGTH_LONG).show();
                    return;
                } else if (!(et_new_code.getText().toString().equals(et_new_code_confirm.getText().toString()))) {
                    Toast.makeText(context, "两次输入密码不一致!", Toast.LENGTH_LONG).show();
                    return;
                }
                request_paw();
                break;
        }
    }

    private void request_paw() {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "login");
        map.put("f", "getpass");
        map.put("mobile", phone);
        map.put("newpass", et_new_code.getText().toString());
        map.put("chkpass", et_new_code_confirm.getText().toString());
        ApiClient.requestNetHandle(this, AppConfig.Sy, "修改中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        JSONObject object = new JSONObject(json);
                        String info = object.getString("info");
                        int status = object.getInt("status");
                        String url = object.getString("url");
                        if (status == 1) {
                            ll_01.setVisibility(View.GONE);
                            ll_02.setVisibility(View.VISIBLE);
                            handler.sendEmptyMessageDelayed(1, 3500);

                            SharedPreferences sharedPreferences = getSharedPreferences("HuierpuUser", MODE_PRIVATE);
                            SharedPreferences.Editor usereditor = sharedPreferences.edit();
                            usereditor.putString("userid", phone);
                            usereditor.putString("pwd", "");
                            usereditor.commit();
                        } else {
                            Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            JSONObject object = new JSONObject(json);
                            if (object.getString("status").equals("2")) {
                                ShowAginLoginDialog();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ActivityManager.finishAll();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
            }
        }
    };
}
