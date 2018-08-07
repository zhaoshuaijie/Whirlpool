package lcsd.com.whirlpool.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.MainActivity;
import lcsd.com.whirlpool.activity.ShouangActivity;
import lcsd.com.whirlpool.adapter.ScListAdapter;
import lcsd.com.whirlpool.entity.ScList;
import lcsd.com.whirlpool.entity.UserInfo;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.http.AppContext;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.util.L;

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
import lcsd.com.whirlpool.view.MultipleStatusView;

/**
 * Created by wei on 2017/6/7.
 */
public class Fragment03 extends Fragment {
    private Context context;
    private PtrClassicFrameLayout mPtrFrame;
    private GridView gridView;
    private List<ScList> list;
    private ScListAdapter adapter;
    private MultipleStatusView mStatusView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_main_03, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        initData();
        request_scfl(0);
    }


    private void initView() {
        gridView = (GridView) getView().findViewById(R.id.f3_gridview);
        mPtrFrame = (PtrClassicFrameLayout) getActivity().findViewById(R.id.rotate_header_list_view_frame3);
        mStatusView = (MultipleStatusView) getActivity().findViewById(R.id.f3_multiple_status_view);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        list = new ArrayList<>();
        mStatusView.showLoading();
    }

    private void initData() {
        adapter = new ScListAdapter(context, list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(context, ShouangActivity.class).putExtra("id", list.get(i).getId()).putExtra("title", list.get(i).getTitle()));
            }
        });
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                request_scfl(1);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPtrFrame.refreshComplete();


            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return false;
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, gridView, header);
            }
        });
        //设置重试视图点击事件
        mStatusView.setOnRetryClickListener(mRetryClickListener);
    }

    final View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mStatusView.showLoading();
            request_scfl(1);
        }
    };

    private void request_scfl(final int i) {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "usercp");
        map.put("f", "fav_cate");
        ApiClient.requestNetHandle(context, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.d("TAG", "我的收藏分类：-----------------" + json);
                    try {
                        if (i == 1) {
                            list.clear();
                        }
                        List<ScList> sc = JSONArray.parseArray(json, ScList.class);
                        list.addAll(sc);
                        if (list.size() > 0) {
                            adapter.notifyDataSetChanged();
                        }
                        if (i == 1) {
                            mPtrFrame.refreshComplete();
                        }
                        mStatusView.showContent();
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            JSONObject object=new JSONObject(json);
                            if(object.getString("status").equals("2")){
                                ((MainActivity)getActivity()).ShowAginLoginDialog();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
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

    @Override
    public void onResume() {
        super.onResume();
        mPtrFrame.autoRefresh();
    }
}
