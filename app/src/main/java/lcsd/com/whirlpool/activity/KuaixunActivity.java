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
import lcsd.com.whirlpool.adapter.KuaixunAdapter;
import lcsd.com.whirlpool.entity.Kuaixun;
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
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import lcsd.com.whirlpool.util.L;

public class KuaixunActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ListView lv;
    private KuaixunAdapter adapter;
    private Kuaixun kuaixun;
    private List<Kuaixun.TRslist> list;
    private int pageid = 1;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuaixun);

        initView();
        initData();
        requestData(0);
    }


    private void initView() {
        title = (TextView) findViewById(R.id.titlebar_title);
        title.setText("成交秘籍");
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.kuaixun_ptrframelayout);
        lv = (ListView) findViewById(R.id.lv_kuaixun);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.disableWhenHorizontalMove(true);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new KuaixunAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(KuaixunActivity.this, ZixunContentActivity.class).putExtra("title", list.get(i).getTitle()).putExtra("url", list.get(i).getUrl()).putExtra("id", list.get(i).getId()).putExtra("img", list.get(i).getThumb()));
            }
        });
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                requestData(2);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestData(1);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, lv, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv, header);
            }
        });
    }

    //0：一开始请求，1：刷新调用 2：加载调用
    private void requestData(final int i) {
        if (i == 2) {
            if (pageid < total) {
                pageid++;
            } else {
                ptrClassicFrameLayout.refreshComplete();
                return;
            }
        }
        if (i == 1) {
            pageid = 1;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", "new_products");
        map.put("pageid", pageid + "");
        map.put("psize", "10");
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.d("成交秘籍", json);
                    kuaixun = JSON.parseObject(json, Kuaixun.class);
                    total = kuaixun.getTotal();
                    if (kuaixun.getRslist() != null && kuaixun.getRslist().size() != 0) {
                        if (i == 1) {
                            list.clear();
                        }
                        list.addAll(kuaixun.getRslist());
                        adapter.notifyDataSetChanged();
                    }
                    if (i == 1 || i == 2) {
                        ptrClassicFrameLayout.refreshComplete();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(KuaixunActivity.this, msg, Toast.LENGTH_SHORT).show();
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
