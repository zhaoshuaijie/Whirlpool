package lcsd.com.whirlpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.Zhuanti2Adapter;
import lcsd.com.whirlpool.entity.Zhuanti;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;

public class Zhuanti2Activity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private String title, cid;
    private ListView lv;
    private Zhuanti2Adapter adapter;
    private List<Zhuanti> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuanti2);
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            cid = getIntent().getStringExtra("cid");
        }

        initView();
        initData();
        requestData();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        tv_title.setText(title);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        lv = (ListView) findViewById(R.id.lv_zt2);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new Zhuanti2Adapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(Zhuanti2Activity.this,Zhuanti3Activity.class).putExtra("title",list.get(i).getTitle()).putExtra("cid",list.get(i).getId()).putExtra("cate",list.get(i).getIdentifier()));
            }
        });
    }

    private void requestData() {
        Map<String,Object> map=new HashMap<>();
        map.put("id","train");
        map.put("cid",cid);
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if(json!=null){
                   List<Zhuanti> list1 = JSON.parseArray(json, Zhuanti.class);
                    if (list1!= null && list1.size() != 0) {
                        list.addAll(list1);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(Zhuanti2Activity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_back:
                this.finish();
                break;
            case R.id.titlebar_home:
                ActivityManager.getActivityManager().finishAll();
                break;
        }
    }
}
