package lcsd.com.whirlpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.JiqiaoAdapter;
import lcsd.com.whirlpool.entity.Jiqiao;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class JiqiaoActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ListView lv;
    private List<Jiqiao.TTree> list;
    private JiqiaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiqiao);

        initView();
        initData();
        requestData(0);
    }

    private void initView() {
        title = (TextView) findViewById(R.id.titlebar_title);
        title.setText("销售技巧");
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.jq_ptrframelayout);
        lv = (ListView) findViewById(R.id.lv_jiqiao);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.disableWhenHorizontalMove(true);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new JiqiaoAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(JiqiaoActivity.this, JiqiaonextActivity.class).putExtra("index", i).putExtra("title", list.get(i).getTitle()));
            }
        });
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestData(1);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv, header);
            }
        });
    }

    //0：一开始请求，1：刷新调用
    private void requestData(final int i) {
        Map<String,Object> map=new HashMap<>();
        map.put("id","skill");
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    Jiqiao jiqiao = JSON.parseObject(json, Jiqiao.class);
                    if (jiqiao != null && jiqiao.getTree() != null) {
                        if (i == 1) {
                            if(list!=null){
                                list.clear();
                            }
                        }
                        list.addAll(jiqiao.getTree());
                        adapter.notifyDataSetChanged();
                        if (i == 1) {
                            ptrClassicFrameLayout.refreshComplete();
                        }
                    } else {
                        ptrClassicFrameLayout.refreshComplete();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(JiqiaoActivity.this, msg, Toast.LENGTH_SHORT).show();
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
