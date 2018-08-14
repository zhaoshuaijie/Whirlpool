package lcsd.com.whirlpool.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.UserInfo;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.http.AppContext;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.util.L;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView button;
    private TextView tv_forgot;
    private Context context;
    private EditText login_phone, login_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        button =  findViewById(R.id.btn_login);
        button.setOnClickListener(this);
        tv_forgot = findViewById(R.id.tv_forgot);
        tv_forgot.setOnClickListener(this);
        login_phone =  findViewById(R.id.login_phone);
        login_code =  findViewById(R.id.login_code);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(login_phone.getText().toString())) {
                    Toast.makeText(context, "请输入手机号", Toast.LENGTH_LONG).show();
                    return;

                } else if (TextUtils.isEmpty(login_code.getText().toString())) {
                    Toast.makeText(context, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                request_logo();
                break;
            case R.id.tv_forgot:
                startActivity(new Intent(context, Modify_Activity.class));
                break;
        }
    }

    private void request_logo() {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "login");
        map.put("mobile", login_phone.getText().toString());
        map.put("pass", login_code.getText().toString());
        ApiClient.requestNetHandle(context, AppConfig.Sy, "登陆中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.d("登录信息：", json);
                    try {
                        JSONObject object = new JSONObject(json);
                        String info = object.getString("info");
                        int status = object.getInt("status");
                        if (status == 1) {
                            String token = object.getString("token");
                            Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("HuierpuUser", MODE_PRIVATE);
                            SharedPreferences.Editor usereditor = sharedPreferences.edit();
                            usereditor.putString("token", token);
                            usereditor.commit();
                            AppContext.token = token;
                            requestLUserInfo();
                        } else {
                            Toast.makeText(context, "登陆失败：" + info, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "登陆失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestLUserInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("c", "usercp");
        ApiClient.requestNetHandle(context, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                L.d("个人信息--------", json);
                UserInfo userInfo = JSON.parseObject(json, UserInfo.class);
                AppContext.getInstance().saveUserInfo(userInfo);
                startActivity(new Intent(context, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                LoginActivity.this.finish();
            }

            @Override
            public void onFailure(String msg) {
                L.d("个人信息异常：---", msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
