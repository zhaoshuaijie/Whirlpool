package lcsd.com.whirlpool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.RankAdapter;
import lcsd.com.whirlpool.entity.Rank;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.util.L;
import lcsd.com.whirlpool.view.MultipleStatusView;

public class RankAddActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ListView lv;
    private MultipleStatusView mStatusView;
    private List<Rank.TRslist> list;
    private RankAdapter adapter;
    private int pageid = 1;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_add);

        initView();
        initData();
        requestData(0);
    }

    private void initView() {
        title = (TextView) findViewById(R.id.titlebar_title);
        title.setText("Top新增榜");
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        mStatusView = (MultipleStatusView) findViewById(R.id.multiple_status_view);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.Rankadd_ptrframelayout);
        lv = (ListView) findViewById(R.id.rankadd_lv);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.disableWhenHorizontalMove(true);
        mStatusView.showLoading();
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new RankAdapter(list, this);
        lv.setAdapter(adapter);
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
        //设置重试视图点击事件
        mStatusView.setOnRetryClickListener(mRetryClickListener);
    }

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
        map.put("c", "usercp");
        map.put("f", "rank_week");
        map.put("pageid", pageid);
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.d("TAG", "-----TOP新增榜-----" + json);
                    try {
                        Rank rank = JSON.parseObject(json, Rank.class);
                        total = rank.getTotal();
                        if (rank.getRslist() != null && rank.getRslist().size() > 0) {
                            if (i == 1) {
                                list.clear();
                            }
                            list.addAll(rank.getRslist());
                            adapter.notifyDataSetChanged();
                            mStatusView.showContent();
                        } else {
                            mStatusView.showEmpty();
                        }
                        if (i == 1 || i == 2) {
                            ptrClassicFrameLayout.refreshComplete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        mStatusView.showError();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                try {
                    mStatusView.showNoNetwork();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    final View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mStatusView.showLoading();
            requestData(1);
        }
    };

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
