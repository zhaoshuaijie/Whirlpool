package lcsd.com.whirlpool.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.BaodianActivity;
import lcsd.com.whirlpool.activity.BaodianContentActivity;
import lcsd.com.whirlpool.activity.JiqiaoActivity;
import lcsd.com.whirlpool.activity.KaoshiActivity;
import lcsd.com.whirlpool.activity.KuaixunActivity;
import lcsd.com.whirlpool.activity.ZhiboActivity;
import lcsd.com.whirlpool.activity.ZhuantiActivity;
import lcsd.com.whirlpool.activity.ZixunActivity;
import lcsd.com.whirlpool.activity.ZixunContentActivity;
import lcsd.com.whirlpool.adapter.HgridviewAdapter;
import lcsd.com.whirlpool.entity.Sy;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.http.AppContext;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import lcsd.com.whirlpool.view.MultipleStatusView;

/**
 * Created by wei on 2017/6/7.
 */
public class Fragment_main extends Fragment implements View.OnClickListener {
    private Context context;
    private ScrollView ll_main;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private GridView gridview;
    private HgridviewAdapter hgridviewAdapter;
    private ImageView iv;
    private TextView tv_title, tv_note, tv_sj, tv_ll;
    private LinearLayout ll_zx;
    private Sy sy;
    private List<Sy.Products> list;
    private MultipleStatusView mStatusView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        initData();
        requestData(0);
    }


    private void initView() {
        tv_title = (TextView) getActivity().findViewById(R.id.fmian_tvtitle);
        tv_note = (TextView) getActivity().findViewById(R.id.fmian_tvnote);
        tv_sj = (TextView) getActivity().findViewById(R.id.fmian_tvsj);
        tv_ll = (TextView) getActivity().findViewById(R.id.fmian_tvll);
        ll_zx = (LinearLayout) getActivity().findViewById(R.id.ll_zuixin);
        iv = (ImageView) getActivity().findViewById(R.id.fmian_iv);
        ll_main = (ScrollView) getActivity().findViewById(R.id.ll_main);
        mStatusView = (MultipleStatusView)getActivity().findViewById(R.id.multiple_status_view);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) getActivity().findViewById(R.id.frag_main_ptrframelayout);
        getActivity().findViewById(R.id.ll_main_1).setOnClickListener(this);
        getActivity().findViewById(R.id.ll_main_2).setOnClickListener(this);
        getActivity().findViewById(R.id.ll_main_3).setOnClickListener(this);
        getActivity().findViewById(R.id.ll_main_4).setOnClickListener(this);
        getActivity().findViewById(R.id.ll_main_5).setOnClickListener(this);
        getActivity().findViewById(R.id.ll_main_6).setOnClickListener(this);
        getActivity().findViewById(R.id.ll_main_7).setOnClickListener(this);
        getActivity().findViewById(R.id.ll_main_8).setOnClickListener(this);
        gridview = (GridView) getActivity().findViewById(R.id.frag_main_grid);
        ll_zx.setOnClickListener(this);
        mStatusView.showLoading();
    }

    private void initGrid() {
        //设置横向gridview
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (list.size() * (275 + 10) * density);
        int itemWidth = (int) (275 * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridview.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridview.setColumnWidth(itemWidth); // 设置列表项宽
        gridview.setHorizontalSpacing(10); // 设置列表项水平间距
        gridview.setStretchMode(GridView.NO_STRETCH);
        gridview.setNumColumns(list.size()); // 设置列数量=列表集合数
        hgridviewAdapter = new HgridviewAdapter(context, list);
        gridview.setAdapter(hgridviewAdapter);
    }

    private void initData() {
        list = new ArrayList<>();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (AppContext.getInstance().getUserInfo() != null && AppContext.getInstance().getUserInfo().getPopedom() != null && AppContext.getInstance().getUserInfo().getPopedom().getProducts() != null) {
                    startActivity(new Intent(context, BaodianContentActivity.class).putExtra("title", list.get(i).getTitle()).putExtra("id", list.get(i).getId()));
                } else {
                    Toast.makeText(context, "您的会员权限不能查看该内容", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.disableWhenHorizontalMove(true);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestData(1);
            }


            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, ll_main, header);
            }
        });

        //设置重试视图点击事件
        mStatusView.setOnRetryClickListener(mRetryClickListener);
    }
    final View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mStatusView.showLoading();
            requestData(1);
        }
    };
    private void requestData(final int i) {
        ApiClient.requestNetHandle(context, AppConfig.Sy, "", null, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    sy = JSON.parseObject(json, Sy.class);
                    if (sy.getNews() != null) {
                        tv_title.setText(sy.getNews().getTitle());
                        tv_note.setText(sy.getNews().getNote());
                        tv_sj.setText(StringUtils.timeStamp2Date(sy.getNews().getDateline()));
                        tv_ll.setText("浏览：" + sy.getNews().getHits());
                        Glide.with(context).load(AppConfig.mainurl + sy.getNews().getThumb()).into(iv);
                    }
                    if (i == 1) {
                        list.clear();
                        if (sy.getProducts() != null) {
                            list.addAll(sy.getProducts());
                            if (hgridviewAdapter != null) {
                                hgridviewAdapter.notifyDataSetChanged();
                            } else {
                                initGrid();
                            }
                        }

                    } else {
                        if (sy.getProducts() != null) {
                            list.addAll(sy.getProducts());
                            if (list.size() != 0) {
                                initGrid();
                            }
                        }

                    }
                    ptrClassicFrameLayout.refreshComplete();
                    mStatusView.showContent();
                }else {
                    mStatusView.showEmpty();
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                ptrClassicFrameLayout.refreshComplete();
                try {
                    mStatusView.showNoNetwork();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_1:
                if (AppContext.getInstance().getUserInfo() != null && AppContext.getInstance().getUserInfo().getPopedom() != null && AppContext.getInstance().getUserInfo().getPopedom().getNews() != null) {
                    startActivity(new Intent(getActivity(), ZixunActivity.class));
                } else {
                    Toast.makeText(context, "您的会员权限不能查看该内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_main_2:
                if (AppContext.getInstance().getUserInfo() != null && AppContext.getInstance().getUserInfo().getPopedom() != null && AppContext.getInstance().getUserInfo().getPopedom().getProducts() != null) {
                    startActivity(new Intent(getActivity(), BaodianActivity.class));
                } else {
                    Toast.makeText(context, "您的会员权限不能查看该内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_main_3:
                if (AppContext.getInstance().getUserInfo() != null && AppContext.getInstance().getUserInfo().getPopedom() != null && AppContext.getInstance().getUserInfo().getPopedom().getSkill() != null) {
                    startActivity(new Intent(getActivity(), JiqiaoActivity.class));
                } else {
                    Toast.makeText(context, "您的会员权限不能查看该内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_main_4:
                if (AppContext.getInstance().getUserInfo() != null && AppContext.getInstance().getUserInfo().getPopedom() != null && AppContext.getInstance().getUserInfo().getPopedom().getTrain() != null) {
                    startActivity(new Intent(getActivity(), ZhuantiActivity.class));
                } else {
                    Toast.makeText(context, "您的会员权限不能查看该内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_main_5:
                if (AppContext.getInstance().getUserInfo() != null && AppContext.getInstance().getUserInfo().getPopedom() != null && AppContext.getInstance().getUserInfo().getPopedom().getNew_products() != null) {
                    startActivity(new Intent(getActivity(), KuaixunActivity.class));
                } else {
                    Toast.makeText(context, "您的会员权限不能查看该内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_main_6:
                if (AppContext.getInstance().getUserInfo() != null && AppContext.getInstance().getUserInfo().getPopedom() != null && AppContext.getInstance().getUserInfo().getPopedom().getTeam() != null) {
                    startActivity(new Intent(getActivity(), ZixunActivity.class).putExtra("title", "团队活动"));
                } else {
                    Toast.makeText(context, "您的会员权限不能查看该内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_main_7:
                if (AppContext.getInstance().getUserInfo() != null && AppContext.getInstance().getUserInfo().getPopedom() != null && AppContext.getInstance().getUserInfo().getPopedom().getExam() != null) {
                    startActivity(new Intent(getActivity(), KaoshiActivity.class));
                } else {
                    Toast.makeText(context, "您的会员权限不能查看该内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_main_8:
                if (AppContext.getInstance().getUserInfo() != null && AppContext.getInstance().getUserInfo().getPopedom() != null && AppContext.getInstance().getUserInfo().getPopedom().getLive() != null) {
                    startActivity(new Intent(getActivity(), ZhiboActivity.class));
                } else {
                    Toast.makeText(context, "您的会员权限不能查看该内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_zuixin:
                if (sy != null) {
                    if (AppContext.getInstance().getUserInfo() != null && AppContext.getInstance().getUserInfo().getPopedom() != null && AppContext.getInstance().getUserInfo().getPopedom().getNews() != null) {
                        startActivity(new Intent(getActivity(), ZixunContentActivity.class).putExtra("title", sy.getNews().getTitle()).putExtra("url", sy.getNews().getUrl()).putExtra("id", sy.getNews().getId()).putExtra("img", sy.getNews().getThumb()));
                    } else {
                        Toast.makeText(context, "您的会员权限不能查看该内容", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
