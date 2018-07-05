package lcsd.com.whirlpool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.ExpandableAdapter;
import lcsd.com.whirlpool.adapter.MyBaseExpandableListWithGridView_Adapter;
import lcsd.com.whirlpool.entity.Product;
import lcsd.com.whirlpool.manager.ActivityManager;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class BaodiannextActivity extends BaseActivity implements View.OnClickListener {
    private PtrClassicFrameLayout mPtrFrame;
    private ExpandableListView Elv;
    private ExpandableAdapter adapter;
    private TextView tv_title;
    private ArrayList<Product.TTree.OSublist.TSublist.SSublist> List;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baodiannext);

        if(getIntent()!=null){
            title=getIntent().getStringExtra("title");
        }

        initView();
        inotData();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        tv_title.setText(title);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        Elv = (ExpandableListView) findViewById(R.id.baodiannext_elv);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_baodiannext);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
    }

    private void inotData() {
        List=new ArrayList();
        List.addAll(MyBaseExpandableListWithGridView_Adapter.LIST);
        adapter = new ExpandableAdapter(this,List);
        Elv.setAdapter(adapter);
        Elv.setGroupIndicator(null);
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
                return super.checkCanDoLoadMore(frame, Elv, footer);
            }


            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, Elv, header);
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
