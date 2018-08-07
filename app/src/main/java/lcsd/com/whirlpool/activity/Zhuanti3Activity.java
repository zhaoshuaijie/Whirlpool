package lcsd.com.whirlpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

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
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.Zhuanti3Adapter;
import lcsd.com.whirlpool.entity.ZhuantiNext;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;

public class Zhuanti3Activity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private String title, cate;
    private PtrClassicFrameLayout mPtrFrame;
    private ListView lv;
    private List<ZhuantiNext.TRslist> list;
    private Zhuanti3Adapter adapter;
    private int pageid = 1;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuanti3);
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            cate = getIntent().getStringExtra("cate");
        }

        initView();
        initData();
        requestDate(0);
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        tv_title.setText(title);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.zt3_PtrClassicFrameLayout);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        lv = (ListView) findViewById(R.id.lv_zt3);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new Zhuanti3Adapter(this, list);
        lv.setAdapter(adapter);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {


            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestDate(1);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                requestDate(2);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                if (pageid < total) {
                    return super.checkCanDoLoadMore(frame, lv, footer);
                } else {
                    return false;
                }
            }


            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv, header);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(Zhuanti3Activity.this, ZixunContentActivity.class).putExtra("id", list.get(i).getId()).putExtra("title", list.get(i).getTitle()).putExtra("url", list.get(i).getUrl()).putExtra("img", list.get(i).getThumb()));
            }
        });
    }

    private void requestDate(final int i) {
        if (i == 2) {
            if (pageid < total) {
                pageid++;
            } else {
                mPtrFrame.refreshComplete();
                return;
            }
        }
        if (i == 1) {
            pageid = 1;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", "train");
        map.put("cate", cate);
        map.put("pageid", pageid + "");
        ApiClient.requestNetHandle(this, AppConfig.Sy, "加載中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        ZhuantiNext zhuantiNext = JSON.parseObject(json, ZhuantiNext.class);
                        if (zhuantiNext.getTotal() != null) {
                            total = zhuantiNext.getTotal();
                            if (zhuantiNext.getRslist() != null && zhuantiNext.getRslist().size() != 0) {
                                if (i == 1) {
                                    list.clear();
                                }
                                list.addAll(zhuantiNext.getRslist());
                                adapter.notifyDataSetChanged();
                            }
                            if (i == 1 || i == 2) {
                                mPtrFrame.refreshComplete();
                            }
                        }
                    } catch (Exception e) {
                        try {
                            JSONObject object = new JSONObject(json);
                            if (object.getString("status").equals("2")) {
                                ShowAginLoginDialog();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(Zhuanti3Activity.this, msg, Toast.LENGTH_SHORT).show();
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
