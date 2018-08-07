package lcsd.com.whirlpool.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ProgressBar;
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


public class  WelcomeActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Context context;
    private TextView textview;
    private final int MSG = 1;
    private int pro;
    private int i;

    private boolean islogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(AppContext.token!=null){
            islogin = true;
        }else {
            islogin = false;
        }
        initView();
        context = this;
    }


    private void initView() {
        progressBar =  findViewById(R.id.pb_welcome);
        textview =  findViewById(R.id.tv_wel);
        start();
    }

    //创建一个Handler
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //处理消息
            switch (msg.what) {
                case MSG:
                    //设置滚动条和text的值
                    progressBar.setProgress(pro);
                    textview.setText(String.valueOf(i) + "%");
                    if (pro == 100) {
                        if (islogin) {
                            if (getIntent().getStringExtra("type") != null) {
                                startActivity(new Intent(context, MainActivity.class).putExtra("type", getIntent().getStringExtra("type")).putExtra("id", getIntent().getStringExtra("id")).putExtra("title", getIntent().getStringExtra("title")).putExtra("tuisong",getIntent().getStringExtra("tuisong")));
                            } else {
                                startActivity(new Intent(context, MainActivity.class));
                            }
                        } else {
                            startActivity(new Intent(context, LoginActivity.class));
                        }
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        WelcomeActivity.this.finish();
                    }
                    break;
            }
        }
    };

    private void start() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int max = progressBar.getMax();
                try {
                    //子线程循环间隔消息
                    while (pro < max) {
                        pro += 1;
                        i = (int) ((pro * 1.0f / progressBar.getMax()) * 100);
                        Message msg = new Message();
                        msg.what = MSG;
                        mHandler.sendMessage(msg);
                        Thread.sleep(30);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
