package lcsd.com.whirlpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.KaoshiflAdapter;
import lcsd.com.whirlpool.adapter.KaoshiflzxAdapter;
import lcsd.com.whirlpool.entity.Kaoshifl;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.util.L;
import lcsd.com.whirlpool.view.MultipleStatusView;
import lcsd.com.whirlpool.view.ScrollViewWithListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考试页面
 */
public class KaoshiActivity extends BaseActivity implements View.OnClickListener {
    private TextView title, tv_suiji, tv_zhuanxiang, tv_bx, tv_xyj;
    private ImageView iv1, iv2, iv_bx, iv_xyj;
    private LinearLayout ll_zx, ll_zx_iv, ll_zhuanxiang;
    private MultipleStatusView mStatusView;
    private ScrollViewWithListView lv;
    private List<Kaoshifl.TTree.OSublist.TSublist> list;
    private List<Kaoshifl.TTree.OSublist.TSublist> list_xyj;
    private List<Kaoshifl.TTree.OSublist.TSublist> list_bx;
    private ArrayList<Kaoshifl.TTree.OSublist> list_zx;
    private KaoshiflAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaoshi);

        initView();
        initData();
        requestData();
    }

    private void initView() {
        title = findViewById(R.id.titlebar_title);
        title.setText("考试中心");
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        findViewById(R.id.tv_pxs).setOnClickListener(this);
        findViewById(R.id.tv_qt).setOnClickListener(this);
        findViewById(R.id.tv_xrz).setOnClickListener(this);
        ll_zhuanxiang = findViewById(R.id.ll_zhuanxiang);
        tv_suiji = findViewById(R.id.tv_suiji);
        tv_zhuanxiang = findViewById(R.id.tv_zhuanxiang);
        iv1 = findViewById(R.id.kaoshi_iv1);
        iv2 = findViewById(R.id.kaoshi_iv2);
        mStatusView = findViewById(R.id.multiple_status_view);
        tv_zhuanxiang.setOnClickListener(onClickListener);
        tv_suiji.setOnClickListener(onClickListener);
        tv_bx = findViewById(R.id.tv_bx);
        tv_xyj = findViewById(R.id.tv_xyj);
        iv_bx = findViewById(R.id.bx_iv);
        iv_xyj = findViewById(R.id.xyj_iv);
        ll_zx = findViewById(R.id.ll_zx);
        ll_zx_iv = findViewById(R.id.ll_zx_iv);
        tv_bx.setOnClickListener(onClickListener1);
        tv_xyj.setOnClickListener(onClickListener1);
        lv = findViewById(R.id.lv_ks);
        mStatusView.showLoading();
    }

    private void initData() {
        list = new ArrayList<>();
        list_xyj = new ArrayList<>();
        list_bx = new ArrayList<>();
        list_zx = new ArrayList<>();
        adapter = new KaoshiflAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(KaoshiActivity.this, KaoshicontentActivity.class)
                        .putExtra("id", list.get(i).getId()).putExtra("cate", list.get(i).getIdentifier())
                        .putExtra("type", "rand"));
            }
        });
        //设置重试视图点击事件
        mStatusView.setOnRetryClickListener(mRetryClickListener);
    }

    final View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mStatusView.showLoading();
            requestData();
        }
    };

    private void requestData() {
        final Map<String, Object> map = new HashMap<>();
        map.put("id", "exam");
        ApiClient.requestNetHandle(KaoshiActivity.this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        Kaoshifl kaoshifl = JSON.parseObject(json, Kaoshifl.class);
                        //随机测试
                        if (kaoshifl != null && kaoshifl.getTree() != null && kaoshifl.getTree().size() > 0) {
                            if (kaoshifl.getTree().get(0).getSublist() != null & kaoshifl.getTree().get(0).getSublist().size() > 0) {
                                if (kaoshifl.getTree().get(0).getSublist().get(0).getTitle().equals("洗衣机")) {
                                    if (kaoshifl.getTree().get(0).getSublist().get(0).getSublist() != null &&
                                            kaoshifl.getTree().get(0).getSublist().get(0).getSublist().size() > 0) {
                                        list_xyj.addAll(kaoshifl.getTree().get(0).getSublist().get(0).getSublist());
                                    }
                                } else if (kaoshifl.getTree().get(0).getSublist().get(0).getTitle().equals("冰箱")) {
                                    if (kaoshifl.getTree().get(0).getSublist().get(0).getSublist() != null &&
                                            kaoshifl.getTree().get(0).getSublist().get(0).getSublist().size() > 0) {
                                        list_bx.addAll(kaoshifl.getTree().get(0).getSublist().get(0).getSublist());
                                    }
                                }
                                if (kaoshifl.getTree().get(0).getSublist().get(1).getTitle().equals("洗衣机")) {
                                    if (kaoshifl.getTree().get(0).getSublist().get(1).getSublist() != null &&
                                            kaoshifl.getTree().get(0).getSublist().get(1).getSublist().size() > 0) {
                                        list_xyj.addAll(kaoshifl.getTree().get(0).getSublist().get(1).getSublist());
                                    }
                                } else if (kaoshifl.getTree().get(0).getSublist().get(1).getTitle().equals("冰箱")) {
                                    if (kaoshifl.getTree().get(0).getSublist().get(1).getSublist() != null &&
                                            kaoshifl.getTree().get(0).getSublist().get(1).getSublist().size() > 0) {
                                        list_bx.addAll(kaoshifl.getTree().get(0).getSublist().get(1).getSublist());
                                    }
                                }
                            }
                        }
                        //专项测试
                        if (kaoshifl != null && kaoshifl.getTree() != null && kaoshifl.getTree().size() > 1) {
                            if (kaoshifl.getTree().get(1).getSublist() != null &&
                                    kaoshifl.getTree().get(1).getSublist().size() > 0) {
                                list_zx.addAll(kaoshifl.getTree().get(1).getSublist());
                            }
                        }
                        mStatusView.showContent();
                    } catch (Exception e) {
                        mStatusView.showError();
                    }
                } else {
                    mStatusView.showEmpty();
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
            tv_suiji.setBackgroundResource(R.color.darkblue);
            tv_suiji.setTextColor(getResources().getColor(R.color.white));
            tv_zhuanxiang.setBackgroundResource(R.color.darkblue);
            tv_zhuanxiang.setTextColor(getResources().getColor(R.color.white));
            iv1.setVisibility(View.INVISIBLE);
            iv2.setVisibility(View.INVISIBLE);
            switch (view.getId()) {
                case R.id.tv_suiji:
                    tv_suiji.setBackgroundResource(R.color.color_huise);
                    tv_suiji.setTextColor(getResources().getColor(R.color.black));
                    iv1.setVisibility(View.VISIBLE);
                    ll_zx.setVisibility(View.VISIBLE);
                    ll_zx_iv.setVisibility(View.VISIBLE);
                    ll_zhuanxiang.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_zhuanxiang:
                    tv_zhuanxiang.setBackgroundResource(R.color.color_huise);
                    tv_zhuanxiang.setTextColor(getResources().getColor(R.color.black));
                    iv2.setVisibility(View.VISIBLE);
                    ll_zx.setVisibility(View.GONE);
                    ll_zx_iv.setVisibility(View.GONE);
                    lv.setVisibility(View.GONE);
                    ll_zhuanxiang.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    private View.OnClickListener onClickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            iv_bx.setVisibility(View.INVISIBLE);
            iv_xyj.setVisibility(View.INVISIBLE);
            switch (view.getId()) {
                case R.id.tv_bx:
                    iv_bx.setVisibility(View.VISIBLE);
                    if (list_bx != null && list_bx.size() > 0) {
                        if (list != null) {
                            list.clear();
                        }
                        list.addAll(list_bx);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.tv_xyj:
                    iv_xyj.setVisibility(View.VISIBLE);
                    if (list_xyj != null && list_xyj.size() > 0) {
                        if (list != null) {
                            list.clear();
                        }
                        list.addAll(list_xyj);
                        adapter.notifyDataSetChanged();
                    }
                    break;
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
            case R.id.tv_pxs:
                if (list_zx != null) {
                    startActivity(new Intent(this, SpecialcertificationActivity.class).putExtra("list", list_zx.get(0).getSublist()));
                }
                break;
            case R.id.tv_qt:
                if (list_zx != null) {
                    startActivity(new Intent(this, SpecialgeneralActivity.class).putExtra("list", list_zx.get(1).getSublist()));
                }
                break;
            case R.id.tv_xrz:
                if (list_zx != null) {
                    startActivity(new Intent(this, SpecialgeneralActivity.class).putExtra("list", list_zx.get(2).getSublist()));
                }
                break;
        }
    }
}
