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

import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Modify_Activity extends BaseActivity implements View.OnClickListener {
    private TextView btn_chzhmm, btn_modify_yzm;
    private Context context;
    private EditText et_modify_phone, et_modify_yzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
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
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "验证成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context, Modify_Activity2.class).putExtra("phone", et_modify_phone.getText().toString()));

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

    private void initView() {
        btn_chzhmm = (TextView) findViewById(R.id.btn_chongzhimima);
        btn_chzhmm.setOnClickListener(this);
        btn_modify_yzm = (TextView) findViewById(R.id.btn_modify_yzm);
        btn_modify_yzm.setOnClickListener(this);
        et_modify_phone = (EditText) findViewById(R.id.et_modify_phone);
        et_modify_yzm = (EditText) findViewById(R.id.et_modify_yzm);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chongzhimima:
                if (TextUtils.isEmpty(et_modify_phone.getText().toString())) {
                    Toast.makeText(context, "请输入手机号", Toast.LENGTH_LONG).show();

                } else if (TextUtils.isEmpty(et_modify_yzm.getText().toString())) {
                    Toast.makeText(context, "请输入验证码", Toast.LENGTH_LONG).show();

                } else {
                    //提交验证码验证
                    String number = et_modify_yzm.getText().toString();
                    SMSSDK.submitVerificationCode("86", et_modify_phone.getText().toString(), number);
                }

                break;
            case R.id.btn_modify_yzm:
                if (TextUtils.isEmpty(et_modify_phone.getText().toString())) {
                    Toast.makeText(context, "电话号码不能为空", Toast.LENGTH_LONG).show();
                    return;
                } else if (et_modify_phone.getText().toString().length() != 11) {
                    Toast.makeText(context, "清正确输入手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                countDown();
                //获取验证码
                SMSSDK.getVerificationCode("86", et_modify_phone.getText().toString());
                break;
        }

    }

    private CountDownTimer timer;

    private void countDown() {
        timer = new CountDownTimer(60 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                btn_modify_yzm.setOnClickListener(null);
                btn_modify_yzm.setText("重新发送" + "（" + millisUntilFinished / 1000 + "）");
                btn_modify_yzm.setTextColor(getResources().getColor(R.color.gray2));
                btn_modify_yzm.setBackgroundResource(R.drawable.check_button_1);
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
        btn_modify_yzm.setTextColor(Color.WHITE);
        btn_modify_yzm.setOnClickListener(this);
        btn_modify_yzm.setBackgroundResource(R.drawable.tv_yellow);
        btn_modify_yzm.setText("重新发送");
        btn_modify_yzm.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

}
