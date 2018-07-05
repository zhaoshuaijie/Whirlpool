package lcsd.com.whirlpool.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcsd.com.whirlpool.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import lcsd.com.whirlpool.adapter.WdjfAdapter;
import lcsd.com.whirlpool.entity.MyPoint;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.http.AppContext;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.util.L;
import lcsd.com.whirlpool.view.CircleImageView;

/**
 * Created by wei on 2017/6/7.
 */
public class Fragment01 extends Fragment {
    private Context context;
    private PtrClassicFrameLayout mPtrFrame;
    private TextView tv_nc, tv_gx, tv_jf, tv_tx;
    private CircleImageView iv_head;
    private ListView lv;
    private WdjfAdapter mAdapter;
    private List<MyPoint> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_main_01, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        initData();
        request_jf(0);
    }

    private void initView() {
        tv_gx = (TextView) getActivity().findViewById(R.id.f1_gx);
        tv_nc = (TextView) getActivity().findViewById(R.id.f1_nc);
        tv_jf = (TextView) getActivity().findViewById(R.id.f1_jf);
        tv_tx = (TextView) getActivity().findViewById(R.id.f1_tx);
        iv_head = (CircleImageView) getActivity().findViewById(R.id.f1_head);
        updataUI();
        lv = (ListView) getActivity().findViewById(R.id.lv_xinxi);
        mPtrFrame = (PtrClassicFrameLayout) getActivity().findViewById(R.id.rotate_header_list_view_frame1);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {


            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                    request_jf(1);
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
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    private void initData() {
       list=new ArrayList<>();
        mAdapter=new WdjfAdapter(context,list);
        lv.setAdapter(mAdapter);
    }

    private void updataUI() {
        if (AppContext.getInstance().checkUser()) {
            if (AppContext.getInstance().getUserInfo().getAvatar().length() < 1) {
                iv_head.setImageResource(R.drawable.img_head);
            } else {
                Glide.with(this).load(AppConfig.mainurl + AppContext.getInstance().getUserInfo().getAvatar()).into(iv_head);
            }
            tv_gx.setText("个性签名：" + AppContext.getInstance().getUserInfo().getSpecial());
            tv_nc.setText("昵称：" + AppContext.getInstance().getUserInfo().getNickname());
            tv_jf.setText("积分：" + AppContext.getInstance().getUserInfo().getPoint());
            tv_tx.setText("头衔：" + AppContext.getInstance().getUserInfo().getHonor());

        }
    }
    private void request_jf(final int i) {
        Map<String,Object> map=new HashMap<>();
        map.put("c","usercp");
        map.put("f","point");
        ApiClient.requestNetHandle(context, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if(json!=null){
                    try {
                        List<MyPoint> mList= JSON.parseArray(json,MyPoint.class);
                        if(mList!=null&&mList.size()>0){
                            if(i==1&&list!=null){
                                list.clear();
                            }
                            list.addAll(mList);
                            mAdapter.notifyDataSetChanged();
                            mPtrFrame.refreshComplete();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            updataUI();
        }
    }
}
