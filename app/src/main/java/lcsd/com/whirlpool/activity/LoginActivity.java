package lcsd.com.whirlpool.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.mob.MobSDK;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView button/*, btn_zhuce*/;
    private TextView tv_forgot;
    private Context context;
    private EditText login_phone, login_code;
    //新增登陆验证码
    private EditText et_yzm;
    private TextView tv_yzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        MobSDK.init(this, "1ee7a6a498945", "3ab7ef2e358bdba0e2d57ff173f80845");
        initYzm();

        initView();
        SharedPreferences userpreferences = getSharedPreferences("HuierpuUser", MODE_PRIVATE);
        login_phone.setText(userpreferences.getString("userid", ""));
        login_code.setText(userpreferences.getString("pwd", ""));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        button = (TextView) findViewById(R.id.btn_login);
        button.setOnClickListener(this);
        /*btn_zhuce = (TextView) findViewById(R.id.btn_zhuce);
        btn_zhuce.setOnClickListener(this);*/
        tv_forgot = (TextView) findViewById(R.id.tv_forgot);
        tv_forgot.setOnClickListener(this);
        login_phone = (EditText) findViewById(R.id.login_phone);
        login_code = (EditText) findViewById(R.id.login_code);

        et_yzm = (EditText) findViewById(R.id.et_lg_yzm);
        tv_yzm = (TextView) findViewById(R.id.btn_lg_yzm);
        tv_yzm.setOnClickListener(this);
    }

    private void initYzm() {
        EventHandler handler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!TextUtils.isEmpty(login_code.getText().toString())) {
                                    request_logo();
                                }
                            }
                        });

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "验证码已发送,五分钟内有效！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, des, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(handler);
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
                } else if (TextUtils.isEmpty(et_yzm.getText().toString())) {
                    Toast.makeText(context, "请输入验证码", Toast.LENGTH_LONG).show();
                    return;
                }
                //request_logo();
                //提交验证码验证
                String number = et_yzm.getText().toString();
                SMSSDK.submitVerificationCode("86", login_phone.getText().toString(), number);
                break;
           /* case R.id.btn_zhuce:
                startActivity(new Intent(context, Activity_zhuce.class));
                break;*/
            case R.id.tv_forgot:
                startActivity(new Intent(context, Modify_Activity.class));
                break;
            //新增登陆验证
            case R.id.btn_lg_yzm:
                if (TextUtils.isEmpty(login_phone.getText().toString())) {
                    Toast.makeText(context, "手机号码不能为空", Toast.LENGTH_SHORT)
                            .show();
                    return;
                } else if (login_phone.getText().toString().length() != 11) {
                    Toast.makeText(context, "请正确输入手机号", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                countDown();
                //获取验证码
                SMSSDK.getVerificationCode("86", login_phone.getText().toString());
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
                    try {
                        JSONObject object = new JSONObject(json);
                        String info = object.getString("info");
                        int status = object.getInt("status");
                        String url = object.getString("url");
                        if (status == 1) {
                            Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("HuierpuUser", MODE_PRIVATE);
                            SharedPreferences.Editor usereditor = sharedPreferences.edit();
                            usereditor.putString("userid", login_phone.getText().toString());
                            usereditor.putString("pwd", login_code.getText().toString());
                            usereditor.commit();
                            requestLUserInfo();
                        } else {
                            Toast.makeText(context, "登陆失败：" + info, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
        ApiClient.requestNetHandle(context, AppConfig.Sy + "?c=usercp", "", null, new ResultListener() {
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
        SMSSDK.unregisterAllEventHandler();
    }

    private CountDownTimer timer;

    private void countDown() {
        timer = new CountDownTimer(60 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tv_yzm.setOnClickListener(null);
                tv_yzm.setText("重新发送" + "（" + millisUntilFinished / 1000 + "）");
                tv_yzm.setTextColor(getResources().getColor(R.color.gray2));
                tv_yzm.setBackgroundResource(R.drawable.check_button_1);
            }

            @Override
            public void onFinish() {
                clickCode();
            }
        };
        // 调用start方法开始倒计时
        timer.start();
    }

    private void clickCode() {
        tv_yzm.setTextColor(Color.WHITE);
        tv_yzm.setOnClickListener(this);
        tv_yzm.setBackgroundResource(R.drawable.tv_yellow);
        tv_yzm.setText("重新发送");
        tv_yzm.setOnClickListener(this);
    }
}
