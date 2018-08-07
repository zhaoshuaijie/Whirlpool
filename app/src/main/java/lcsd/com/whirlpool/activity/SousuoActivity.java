package lcsd.com.whirlpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.SousuoAdapter;
import lcsd.com.whirlpool.entity.Shousuo;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import lcsd.com.whirlpool.view.CleanableEditText;

public class SousuoActivity extends BaseActivity implements View.OnClickListener {
    private CleanableEditText et;
    private TextView tv_ss;
    private ListView lv;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private List<Shousuo.TRslist> list;
    private int pageid = 1;
    private int total;
    private SousuoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);

        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.sousuo_back).setOnClickListener(this);
        et = (CleanableEditText) findViewById(R.id.sousuo_et);
        tv_ss = (TextView) findViewById(R.id.sousuo_sousuo);
        lv = (ListView) findViewById(R.id.sousuo_lv);
        tv_ss.setOnClickListener(this);
        et.setOnKeyListener(onKeyListener);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.sousuo_ptrframelayout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.disableWhenHorizontalMove(true);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new SousuoAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list.get(i).getProject_name().equals("产品宝典")) {
                    startActivity(new Intent(SousuoActivity.this, BaodianContentActivity.class).
                            putExtra("id", list.get(i).getId()).putExtra("title", list.get(i).getTitle()));
                } else {
                    startActivity(new Intent(SousuoActivity.this, ZixunContentActivity.class).
                            putExtra("id", list.get(i).getId()).putExtra("title", list.get(i).getTitle()).
                            putExtra("url", list.get(i).getUrl()).putExtra("img", list.get(i).getThumb()));
                }
            }
        });
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                reuquestData(2);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                reuquestData(1);
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

    //键盘事件
    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                /**隐藏软键盘*/
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                if (et.getText() != null && !StringUtils.isEmpty(et.getText().toString())) {
                    reuquestData(0);
                    return true;
                } else {
                    Toast.makeText(SousuoActivity.this, "请输入要搜索的内容", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            return false;
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sousuo_back:
                this.finish();
                break;
            case R.id.sousuo_sousuo:
                if (et.getText().toString().length() != 0) {
                    reuquestData(0);
                } else {
                    Toast.makeText(SousuoActivity.this, "请输入要搜索的内容", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //0：一开始请求，1：刷新调用 2：加载调用
    private void reuquestData(final int i) {
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
        map.put("c", "search");
        map.put("keywords", et.getText().toString());
        map.put("pageid", pageid + "");
        ApiClient.requestNetHandle(this, AppConfig.Sy, "搜索中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        Shousuo shousuo = JSON.parseObject(json, Shousuo.class);
                        if (shousuo.getRslist() != null && shousuo.getRslist().size() > 0) {
                            total = shousuo.getTotal();
                            if ((i == 1 || i == 0) && list != null) {
                                list.clear();
                            }
                            list.addAll(shousuo.getRslist());
                            adapter.notifyDataSetChanged();
                            if (i == 1 || i == 2) {
                                ptrClassicFrameLayout.refreshComplete();
                            }
                        } else {
                            Toast.makeText(SousuoActivity.this, "无该相关产品，换个关键词试试！", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SousuoActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
