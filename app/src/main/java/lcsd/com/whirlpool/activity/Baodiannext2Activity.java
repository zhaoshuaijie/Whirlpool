package lcsd.com.whirlpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.ListAdapter;
import lcsd.com.whirlpool.adapter.MyBaseExpandableListWithGridView_Adapter;
import lcsd.com.whirlpool.entity.TRslist;
import lcsd.com.whirlpool.manager.ActivityManager;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class Baodiannext2Activity extends BaseActivity implements View.OnClickListener {
    private PtrClassicFrameLayout mPtrFrame;
    private TextView tv_title;
    private String title;
    private ListView lv;
    private ArrayList<TRslist> list;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baodiannext2);

        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
        }

        initView();
        initData();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        tv_title.setText(title);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        lv = (ListView) findViewById(R.id.lv_bd);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_baodiannext2);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
    }

    private void initData() {
        list=new ArrayList<>();
        list.addAll(MyBaseExpandableListWithGridView_Adapter.RLIST);
        adapter=new ListAdapter(this,list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(Baodiannext2Activity.this,BaodianContentActivity.class).putExtra("title",list.get(i).getTitle()).putExtra("id",list.get(i).getId()));
            }
        });
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.refreshComplete();

            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPtrFrame.refreshComplete();
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, lv, footer);
            }


            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv, header);
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
