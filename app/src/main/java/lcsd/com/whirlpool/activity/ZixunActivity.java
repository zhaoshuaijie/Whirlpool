package lcsd.com.whirlpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.ZixunAdapter;
import lcsd.com.whirlpool.entity.NewsListAll;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.view.Autoviewpage;
import lcsd.com.whirlpool.view.ScrollViewWithListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class ZixunActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private Autoviewpage autoviewpage;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ScrollView ll_zixun;
    private ScrollViewWithListView lv;
    private ZixunAdapter zixunAdapter;
    private List<NewsListAll.Rslist> list;
    private List<NewsListAll.Slider> list_vp;
    private NewsListAll newsListAll;
    private String title;
    private int pageid = 1;
    private int total;
    private String id = "news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixun);
        if (getIntent().getStringExtra("title") != null) {
            title = getIntent().getStringExtra("title");
        }
        initView();
        initData();
        requestData(0);
    }

    private void initView() {
        list = new ArrayList<>();
        list_vp = new ArrayList<>();
        ll_zixun = (ScrollView) findViewById(R.id.ll_zixun);
        ll_zixun.fullScroll(View.FOCUS_UP);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.zixun_ptrframelayout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.disableWhenHorizontalMove(true);

        tv_title = (TextView) findViewById(R.id.titlebar_title);
        if (title != null) {
            tv_title.setText(title);
            id = "team";
        } else {
            tv_title.setText("企业资讯");
        }
        lv = (ScrollViewWithListView) findViewById(R.id.lv_zixun);
        lv.setFocusable(false);//去除listview焦点
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);

        autoviewpage = (Autoviewpage) findViewById(R.id.auto_viewpage);

    }

    private void initData() {
        // 为autoviewpage配置
        autoviewpage.setDotSpace(10);
        autoviewpage.setDotSize(10);
        autoviewpage.setDelay(3000);
        autoviewpage.setOnItemClickListener(new Autoviewpage.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(ZixunActivity.this, ZixunContentActivity.class).putExtra("title", list_vp.get(position).getTitle()).putExtra("url", list_vp.get(position).getUrl()).putExtra("id", list_vp.get(position).getId()).putExtra("img", list_vp.get(position).getThumb()));
            }
        });

        zixunAdapter = new ZixunAdapter(this, list);
        lv.setAdapter(zixunAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(ZixunActivity.this, ZixunContentActivity.class).putExtra("title", list.get(i).getTitle()).putExtra("url", list.get(i).getUrl()).putExtra("id", list.get(i).getId()).putExtra("img", list.get(i).getThumb()));
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
                    return super.checkCanDoLoadMore(frame, ll_zixun, footer);
                } else {
                    return false;
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, ll_zixun, header);
            }
        });
    }

    private void initVp() {
        for (int i = 0; i < list_vp.size(); i++) {
            autoviewpage.addImageTitle(AppConfig.mainurl + list_vp.get(i).getThumb(), list_vp.get(i).getTitle());
        }
        autoviewpage.commit();
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
        map.put("id", id);
        map.put("pageid", pageid + "");
        map.put("psize", "10");
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        newsListAll = JSON.parseObject(json, NewsListAll.class);
                        total = Integer.parseInt(newsListAll.getTotal());
                        if (newsListAll.getSlider() != null && newsListAll.getSlider().size() != 0 && i == 0) {
                            list_vp.addAll(newsListAll.getSlider());
                            initVp();
                        }
                        if (newsListAll.getRslist() != null && newsListAll.getRslist().size() != 0) {
                            if (i == 1) {
                                list.clear();
                            }
                            list.addAll(newsListAll.getRslist());
                            zixunAdapter.notifyDataSetChanged();
                        }
                        if (i == 1 || i == 2) {
                            ptrClassicFrameLayout.refreshComplete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
                Toast.makeText(ZixunActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        // 释放资源
        try {
            autoviewpage.releaseResource();
        } catch (Exception e) {

        }
        super.onDestroy();
    }
}
