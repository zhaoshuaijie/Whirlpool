package lcsd.com.whirlpool.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.util.StringUtils;

import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Activity_zhuce extends BaseActivity implements View.OnClickListener {
    private TextView btn_zhuce_next, btn_zhuce_yzm;
    private Context context;
    private EditText et_zc_phone, Code_num, password1, password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        context = this;
        MobSDK.init(this, "1ee7a6a498945", "3ab7ef2e358bdba0e2d57ff173f80845");
        initData();
        initView();
    }

    private void initData() {

        EventHandler handler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(Activity_zhuce.this, Activity_zhuce2.class).putExtra("phone_num", et_zc_phone.getText().toString()).putExtra("phone_password", password1.getText().toString()));
                            }
                        });

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
        SMSSDK.registerEventHandler(handler);

    }


    private void initView() {
        Code_num = (EditText) findViewById(R.id.et_zc_yzm);
        et_zc_phone = (EditText) findViewById(R.id.et_zc_phone);

        btn_zhuce_next = (TextView) findViewById(R.id.btn_zhuce_next);
        btn_zhuce_next.setOnClickListener(this);
        btn_zhuce_yzm = (TextView) findViewById(R.id.btn_zhuce_yzm);
        btn_zhuce_yzm.setOnClickListener(this);

        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);
    }


    private CountDownTimer timer;

    private void countDown() {
        timer = new CountDownTimer(60 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                btn_zhuce_yzm.setOnClickListener(null);
                btn_zhuce_yzm.setText("重新发送" + "（" + millisUntilFinished / 1000 + "）");
                btn_zhuce_yzm.setTextColor(getResources().getColor(R.color.gray2));
                btn_zhuce_yzm.setBackgroundResource(R.drawable.check_button_1);
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
        btn_zhuce_yzm.setTextColor(Color.WHITE);
        btn_zhuce_yzm.setOnClickListener(this);
        btn_zhuce_yzm.setBackgroundResource(R.drawable.tv_yellow);
        btn_zhuce_yzm.setText("重新发送");
        btn_zhuce_yzm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_zhuce_next:
                if (StringUtils.isEmpty(et_zc_phone.getText().toString())) {
                    Toast.makeText(Activity_zhuce.this, "手机号码不能为空", Toast.LENGTH_SHORT)
                            .show();
                    return;
                } else if (StringUtils.isEmpty(Code_num.getText().toString())) {
                    Toast.makeText(Activity_zhuce.this, "请输入验证码验证码", Toast.LENGTH_SHORT)
                            .show();
                    return;
                } else if (StringUtils.isEmpty(password1.getText().toString()) || password1.getText().length() < 6 || password1.getText().length() > 16) {
                    Toast.makeText(Activity_zhuce.this, "请输入6~16位密码!", Toast.LENGTH_SHORT)
                            .show();
                    return;
                } else if (StringUtils.isEmpty(password2.getText().toString())) {
                    Toast.makeText(Activity_zhuce.this, "请输入确认密码!", Toast.LENGTH_SHORT)
                            .show();
                    return;
                } else if (!password1.getText().toString().equals(password2.getText().toString())) {
                    Toast.makeText(Activity_zhuce.this, "两次输入密码不一致！", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                //提交验证码验证
                String number = Code_num.getText().toString();
                SMSSDK.submitVerificationCode("86", et_zc_phone.getText().toString(), number);
                break;
            case R.id.btn_zhuce_yzm:
                if (!TextUtils.isEmpty(et_zc_phone.getText().toString()) && et_zc_phone.getText().toString().length() == 11) {
                    countDown();
                    //获取验证码
                    SMSSDK.getVerificationCode("86", et_zc_phone.getText().toString());
                } else {
                    Toast.makeText(Activity_zhuce.this, "电话号码不能为空", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
