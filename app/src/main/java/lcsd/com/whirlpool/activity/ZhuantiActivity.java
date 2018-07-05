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
import lcsd.com.whirlpool.adapter.ZhuantiAdapter;
import lcsd.com.whirlpool.entity.Zhuanti;
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

public class ZhuantiActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ListView lv;
    private List<Zhuanti> list, list1;
    private ZhuantiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuanti);

        initView();
        initData();
        requestData(0);
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        tv_title.setText("培训专题");
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.zhuanti_ptrframelayout);
        lv = (ListView) findViewById(R.id.lv_zhuanti);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.disableWhenHorizontalMove(true);
    }

    private void initData() {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        adapter = new ZhuantiAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                request(list.get(i).getId(), list.get(i).getTitle(), list.get(i).getIdentifier());
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
        Map<String, Object> map = new HashMap<>();
        map.put("id", "train");
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    list1 = JSON.parseArray(json, Zhuanti.class);
                    if (i == 1) {
                        list.clear();
                    }
                    list.addAll(list1);
                    adapter.notifyDataSetChanged();
                    if (i == 1) {
                        ptrClassicFrameLayout.refreshComplete();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(ZhuantiActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    private void request(final String cid, final String title, final String cate) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "train");
        map.put("cid", cid);
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        List<Zhuanti> list1 = JSON.parseArray(json, Zhuanti.class);
                        if (list1 != null && list1.size() != 0) {
                            startActivity(new Intent(ZhuantiActivity.this, Zhuanti2Activity.class).putExtra("title", title).putExtra("cid", cid));
                        } else {
                            startActivity(new Intent(ZhuantiActivity.this, Zhuanti3Activity.class).putExtra("title", title).putExtra("cid", cid).putExtra("cate", cate));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        startActivity(new Intent(ZhuantiActivity.this, Zhuanti3Activity.class).putExtra("title", title).putExtra("cid", cid).putExtra("cate", cate));
                    }
                } else {
                    startActivity(new Intent(ZhuantiActivity.this, Zhuanti3Activity.class).putExtra("title", title).putExtra("cid", cid).putExtra("cate", cate));
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(ZhuantiActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
