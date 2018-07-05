package lcsd.com.whirlpool.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.MyAdapter;
import lcsd.com.whirlpool.entity.Jiqiao;
import lcsd.com.whirlpool.entity.JiqiaoNext;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.util.L;
import lcsd.com.whirlpool.view.MultipleStatusView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JiqiaonextActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    private TextView tv_title, tv1, tv2;
    private ListView lv;
    private MyAdapter adapter;
    private MultipleStatusView mStatusView;
    private PtrClassicFrameLayout mPtr;
    private Context mContext;
    private List<JiqiaoNext.TRslist> list;
    private String url1, url2, title;
    private boolean isxiyiji = true;
    private int pageid = 1;
    private int total;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiqiaonext);

        if (getIntent() != null) {
            index = getIntent().getIntExtra("index", 0);
            title = getIntent().getStringExtra("title");
        }
        mContext = this;
        initView();
        initData();
        requesDate();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        lv = (ListView) findViewById(R.id.jq_lv);
        tv_title.setText(title);
        mStatusView = (MultipleStatusView) findViewById(R.id.multiple_status_view);
        mPtr = (PtrClassicFrameLayout) findViewById(R.id.jiqiao_ptrframelayout);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
        tv1.setOnClickListener(onClickListener);
        tv2.setOnClickListener(onClickListener);
        mStatusView.showLoading();
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new MyAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        mPtr.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                requestData(2, isxiyiji ? url1 : url2);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestData(1, isxiyiji ? url1 : url2);
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

    private void requesDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "skill");
        L.e("请求数据：", "外层");
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    Jiqiao jiqiao = JSON.parseObject(json, Jiqiao.class);
                    if (jiqiao.getTree().get(index) != null) {
                        if (jiqiao.getTree().get(index).getSublist() != null && jiqiao.getTree().get(index).getSublist().size() > 0 && jiqiao.getTree().get(index).getSublist().get(0).getUrl() != null && jiqiao.getTree().get(index).getSublist().get(0).getUrl().length() > 0) {
                            url1 = jiqiao.getTree().get(index).getSublist().get(0).getUrl();
                        }
                        if (jiqiao.getTree().get(index).getSublist() != null && jiqiao.getTree().get(index).getSublist().size() > 0 && jiqiao.getTree().get(index).getSublist().get(1).getUrl() != null && jiqiao.getTree().get(index).getSublist().get(1).getUrl().length() > 0) {
                            url2 = jiqiao.getTree().get(index).getSublist().get(1).getUrl();
                        }
                        if (isxiyiji) {
                            requestData(1, url1);
                        } else {
                            requestData(1, url2);
                        }
                    } else {
                        mStatusView.showEmpty();
                    }
                } else {
                    mStatusView.showError();
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

    //请求底层列表数据
    private void requestData(final int i, String url) {
        Map<String, Object> map = new HashMap<>();
        if (i == 1) {
            map.put("pageid", 1 + "");
            pageid = 1;
        }
        if (i == 2) {
            if (pageid < total) {
                pageid++;
                map.put("pageid", pageid);
            } else {
                mPtr.refreshComplete();
                return;
            }
        }
        map.put("psize", 10 + "");
        L.e("请求数据：", "底层" + "pageid=" + pageid);
        ApiClient.requestNetHandle(this, url, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    JiqiaoNext jq = JSON.parseObject(json, JiqiaoNext.class);
                    total = jq.getTotal();
                    if (jq.getRslist() != null && jq.getRslist().size() != 0) {
                        if (i == 1) {
                            list.clear();
                        }
                        list.addAll(jq.getRslist());
                        adapter.notifyDataSetChanged();
                        mStatusView.showContent();
                    } else {
                        mStatusView.showEmpty();
                    }
                    if (i == 1 || i == 2) {
                        mPtr.refreshComplete();
                    }
                } else {
                    mStatusView.showError();
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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tv1.setTextColor(getResources().getColor(R.color.black));
            tv2.setTextColor(getResources().getColor(R.color.black));
            tv1.setBackgroundResource(R.color.color_huise);
            tv2.setBackgroundResource(R.color.color_huise);
            switch (view.getId()) {
                case R.id.tv_1:
                    tv1.setTextColor(getResources().getColor(R.color.white));
                    tv1.setBackgroundResource(R.color.darkblue);
                    if (list != null) {
                        list.clear();
                    }
                    isxiyiji = true;
                    if (url1 != null) {
                        mStatusView.showLoading();
                        requestData(1, url1);
                    }
                    break;
                case R.id.tv_2:
                    tv2.setTextColor(getResources().getColor(R.color.white));
                    tv2.setBackgroundResource(R.color.darkblue);
                    if (list != null) {
                        list.clear();
                    }
                    isxiyiji = false;
                    if (url2 != null) {
                        mStatusView.showLoading();
                        requestData(1, url2);
                    }
                    break;
            }
        }
    };
    final View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mStatusView.showLoading();
            if (url1 == null || url2 == null) {
                requesDate();
            } else {
                if (isxiyiji) {
                    requestData(1, url1);
                } else {
                    requestData(1, url2);
                }
            }
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(mContext, ZixunContentActivity.class).putExtra("title", list.get(i).getTitle()).putExtra("url", list.get(i).getUrl()).putExtra("id", list.get(i).getId()).putExtra("img", list.get(i).getThumb()));
    }
}
