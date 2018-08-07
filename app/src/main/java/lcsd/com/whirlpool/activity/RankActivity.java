package lcsd.com.whirlpool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.RankAdapter;
import lcsd.com.whirlpool.entity.Rank;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.http.AppContext;
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
import lcsd.com.whirlpool.view.CircleImageView;

public class RankActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ListView lv;
    private LinearLayout ll;
    private TextView tv_name, tv_jf, tv_mc;
    private CircleImageView iv_head;
    private List<Rank.TRslist> list;
    private RankAdapter adapter;
    private int pageid = 1;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        initView();
        initData();
        requestData(0);
    }

    private void initView() {
        title = (TextView) findViewById(R.id.titlebar_title);
        title.setText("积分Top榜");
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.Rank_ptrframelayout);
        lv = (ListView) findViewById(R.id.rank_lv);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.disableWhenHorizontalMove(true);
        ll = (LinearLayout) findViewById(R.id.rank_ll);
        tv_name = (TextView) findViewById(R.id.rank_name);
        tv_jf = (TextView) findViewById(R.id.rank_tv_jifen);
        tv_mc = (TextView) findViewById(R.id.rank_tv_mingci);
        iv_head = (CircleImageView) findViewById(R.id.rank_iv_head);
    }

    private void initData() {
        if (AppContext.getInstance().checkUser()) {
            if (AppContext.getInstance().getUserInfo().getAvatar().length() < 1) {
                iv_head.setImageResource(R.drawable.img_head);
            } else {
                RequestOptions options = new RequestOptions();
                options.dontAnimate();
                options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                options.fitCenter();
                Glide.with(this).load(AppConfig.mainurl + AppContext.getInstance().getUserInfo().getAvatar()).apply(options).into(iv_head);
            }
            tv_name.setText(AppContext.getInstance().getUserInfo().getFullname());
            tv_mc.setText(AppContext.getInstance().getUserInfo().getRank() + "");
            tv_jf.setText(AppContext.getInstance().getUserInfo().getPoint());

        }
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
        map.put("f", "rank");
        map.put("pageid", pageid);
        ApiClient.requestNetHandle(this, AppConfig.Sy, "加载中", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.d("TAG", "-----TOP-----" + json);
                    try {
                        Rank rank = JSON.parseObject(json, Rank.class);
                        total = rank.getTotal();
                        if (rank.getRslist() != null && rank.getRslist().size() > 0) {
                            if (i == 1) {
                                list.clear();
                            }
                            list.addAll(rank.getRslist());
                            adapter.notifyDataSetChanged();
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
