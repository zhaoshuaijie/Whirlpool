package lcsd.com.whirlpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.ScAdapter;
import lcsd.com.whirlpool.entity.ShouCang;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class ShouangActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ListView lv;
    private String title, id;
    private List<ShouCang.TRslist> list;
    private ScAdapter adapter;
    private int pageid = 1;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouang);

        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
        }
        initView();
        initData();
        request_data(0);
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        tv_title.setText(title);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.sc_ptrframelayout);
        lv = (ListView) findViewById(R.id.lv_sc);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.disableWhenHorizontalMove(true);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new ScAdapter(this, list,title);
        lv.setAdapter(adapter);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                request_data(1);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                request_data(2);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                if(pageid<total){
                    return super.checkCanDoLoadMore(frame, lv, footer);
                }else{
                    return false;
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv, header);
            }
        });
    }

    private void request_data(final int i) {
        if(i==2){
            if (pageid  < total) {
                pageid++;
            }else{
                ptrClassicFrameLayout.refreshComplete();
                return;
            }
        }
        if(i==1){
            pageid=1;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("c", "usercp");
        map.put("f", "fav");
        map.put("pid", id);
        map.put("pageid",pageid);
        ApiClient.requestNetHandle(ShouangActivity.this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    ShouCang Sc = JSONArray.parseObject(json, ShouCang.class);
                    total = Sc.getTotal();
                    if (Sc.getRslist() != null && Sc.getRslist().size() > 0) {
                        if (i == 1) {
                            list.clear();
                        }
                        list.addAll(Sc.getRslist());
                        adapter.notifyDataSetChanged();
                    }
                    if (i == 1 || i == 2) {
                        ptrClassicFrameLayout.refreshComplete();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

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

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
