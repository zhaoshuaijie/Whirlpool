package lcsd.com.whirlpool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.FeedBackActivity;
import lcsd.com.whirlpool.activity.MainActivity;
import lcsd.com.whirlpool.adapter.FeedBackAdaper;
import lcsd.com.whirlpool.entity.FeedBack;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.view.MultipleStatusView;

/**
 * Created by jie on 2018/5/14.
 */
public class Fragment06 extends Fragment {
    private MultipleStatusView mStatusView;
    private ListView mListView;
    private List<FeedBack> mList;
    private FeedBackAdaper mAdaper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_main_06, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        requestData();
    }

    private void initView() {
        mStatusView = getActivity().findViewById(R.id.f6_multiple_status_view);
        mListView = getActivity().findViewById(R.id.lv_fkfl);

        mStatusView.showLoading();
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdaper = new FeedBackAdaper(getContext(), mList);
        mListView.setAdapter(mAdaper);
        //设置重试视图点击事件
        mStatusView.setOnRetryClickListener(mRetryClickListener);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getActivity().startActivity(new Intent(getContext(), FeedBackActivity.class)
                        .putExtra("cate_id", mList.get(i).getId())
                        .putExtra("title", mList.get(i).getTitle()));
            }
        });
    }

    final View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mStatusView.showLoading();
            requestData();
        }
    };

    private void requestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "feedback");
        ApiClient.requestNetHandle(getContext(), AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        List<FeedBack> list = JSON.parseArray(json, FeedBack.class);
                        if (list != null && list.size() > 0) {
                            mList.clear();
                            mList.addAll(list);
                            mAdaper.notifyDataSetChanged();
                            mStatusView.showContent();
                        } else {
                            mStatusView.showEmpty();
                        }
                    } catch (Exception e) {
                        try {
                            JSONObject object = new JSONObject(json);
                            if (object.getString("status").equals("2")) {
                                ((MainActivity) getActivity()).ShowAginLoginDialog();
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
}
