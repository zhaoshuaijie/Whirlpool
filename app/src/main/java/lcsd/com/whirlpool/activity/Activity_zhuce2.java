package lcsd.com.whirlpool.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.SimpleArrayAdapter;
import lcsd.com.whirlpool.entity.Register;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.util.L;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_zhuce2 extends BaseActivity {
    private Spinner spinner1, spinner2, spinner3;
    private String[] s1 = {"男", "女","性别"};
    private SimpleArrayAdapter adapter1, adapter2, adapter3;
    private CheckBox cb;
    private String phone_num, phone_password;
    private TextView tv_zc;
    private EditText et_nc, et_x, et_m;
    private Integer I, J, K = null;
    private Register register;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce2);
        context = this;
        if (getIntent() != null) {
            phone_num = getIntent().getStringExtra("phone_num");
            phone_password = getIntent().getStringExtra("phone_password");
        }
        requestData();
        initView();
    }

    private void requestData() {

        ApiClient.requestNetHandle(context, AppConfig.request_register + "?c=register", "", null, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    if (register == null) {
                        register = JSON.parseObject(json, Register.class);
                        L.d("公司职务-----------------", json);
                        initData();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        cb = (CheckBox) findViewById(R.id.cb);
        tv_zc = (TextView) findViewById(R.id.tv_zc);
        tv_zc.setOnClickListener(onClickListener);
        et_nc = (EditText) findViewById(R.id.et_nc);
        et_x = (EditText) findViewById(R.id.et_xing);
        et_m = (EditText) findViewById(R.id.et_ming);
        spinner1 = (Spinner) findViewById(R.id.zhuce_spinner1);
        spinner2 = (Spinner) findViewById(R.id.zhuce_spinner2);
        spinner3 = (Spinner) findViewById(R.id.zhuce_spinner3);

        adapter1 = new SimpleArrayAdapter(this, R.layout.myspinne);
        for (int i = 0; i < s1.length; i++) {
            adapter1.add(s1[i]);
        }
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setSelection(adapter1.getCount(),true);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                I = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initData() {

        if (register != null) {
            adapter2 = new SimpleArrayAdapter(this, R.layout.myspinne);
            for (int i = 0; i < register.getContent().getDepartment().size(); i++) {
                adapter2.add(register.getContent().getDepartment().get(i).getTitle());
            }
            adapter2.add("所属分公司");
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setSelection(adapter2.getCount(),true);
        }
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                J = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        if (register != null) {
            adapter3 = new SimpleArrayAdapter(this, R.layout.myspinne);
            for (int i = 0; i < register.getContent().getPosition().size(); i++) {
                adapter3.add(register.getContent().getPosition().get(i).getTitle());
            }
            adapter3.add("职务");
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner3.setAdapter(adapter3);
            spinner3.setSelection(adapter3.getCount(),true);
        }
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                K = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (et_nc.getText().toString().length() < 1) {
                Toast.makeText(Activity_zhuce2.this, "请输入昵称", Toast.LENGTH_SHORT).show();
                return;
            } else if (et_x.getText().toString().length() < 1 || et_m.getText().toString().length() < 1) {
                Toast.makeText(Activity_zhuce2.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                return;
            } else if (I == null) {
                Toast.makeText(Activity_zhuce2.this, "请选择性别", Toast.LENGTH_SHORT).show();
                return;
            } else if (J == null) {
                Toast.makeText(Activity_zhuce2.this, "请选择所属分公司", Toast.LENGTH_SHORT).show();
                return;
            } else if (K == null) {
                Toast.makeText(Activity_zhuce2.this, "请选择职务", Toast.LENGTH_SHORT).show();
                return;
            } else if (!cb.isChecked()) {
                Toast.makeText(Activity_zhuce2.this, "请阅读相关条款", Toast.LENGTH_SHORT).show();
                return;
            }
            request_zc();
        }
    };

    private void request_zc() {
        Map<String,Object> map=new HashMap<>();
        map.put("c", "register");
        map.put("f", "save");
        map.put("mobile", phone_num);
        map.put("newpass", phone_password);
        map.put("chkpass", phone_password);
        map.put("nickname", et_nc.getText().toString());
        map.put("fullname", et_x.getText().toString() + "" + et_m.getText().toString());
        map.put("gender", I + 1 + "");
        map.put("department_id", register.getContent().getDepartment().get(J).getId() + "");
        map.put("position", register.getContent().getPosition().get(K).getId() + "");
        ApiClient.requestNetHandle(context, AppConfig.Sy, "注册中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        JSONObject object=new JSONObject(json);
                        String info=object.getString("info");
                        int status=object.getInt("status");
                        String url=object.getString("url");
                        if(status==1){
                            Toast.makeText(context,"注册成功",Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences=getSharedPreferences("HuierpuUser",MODE_PRIVATE);
                            SharedPreferences.Editor usereditor=sharedPreferences.edit();
                            usereditor.putString("userid",phone_num);
                            usereditor.putString("pwd",phone_password);
                            usereditor.commit();
                            ActivityManager.finishAll();
                        }else{
                            Toast.makeText(context,"注册失败："+info,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
