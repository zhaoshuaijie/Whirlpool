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

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.ZhiboAdapter;
import lcsd.com.whirlpool.entity.Zhibo;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.util.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class ZhiboActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ListView lv;
    private int pageid = 1;
    private int total;
    private List<Zhibo.TRslist> list;
    private ZhiboAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhibo);

        initView();
        initData();
        requestData(0);
    }

    private void initView() {
        title = (TextView) findViewById(R.id.titlebar_title);
        title.setText("直播平台");
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.zhibo_ptrframelayout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.disableWhenHorizontalMove(true);
        lv = (ListView) findViewById(R.id.lv_zhibo);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new ZhiboAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list.get(i).getZb_status() != null && list.get(i).getZb_status().equals("1")) {
                    startActivity(new Intent(ZhiboActivity.this, WebActivity.class).putExtra("title", list.get(i).getTitle()).putExtra("url", list.get(i).getZb_url()).putExtra("img", list.get(i).getThumb()));
                } else {
                    startActivity(new Intent(ZhiboActivity.this, Playvideo2Activity.class).putExtra("title", list.get(i).getTitle()).putExtra("url", list.get(i).getHk_url()).putExtra("img", list.get(i).getThumb()));
                }
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
        final Map<String, Object> map = new HashMap<>();
        map.put("id", "live");
        map.put("cate", "zhibo");
        map.put("pageid", pageid + "");
        map.put("psize", "10");
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        Zhibo zhibo = JSON.parseObject(json, Zhibo.class);
                        total = zhibo.getTotal();
                        if (zhibo.getRslist() != null && zhibo.getRslist().size() > 0) {
                            if (i == 1) {
                                list.clear();
                            }
                            list.addAll(zhibo.getRslist());
                            adapter.notifyDataSetChanged();
                        }
                        if (i == 1 || i == 2) {
                            ptrClassicFrameLayout.refreshComplete();
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
                Toast.makeText(ZhiboActivity.this, msg, Toast.LENGTH_SHORT).show();
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
