package lcsd.com.whirlpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.KaoshiPxAdapter;
import lcsd.com.whirlpool.entity.KaoshiZxContent;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.view.MultipleStatusView;

public class SpecialcertificationListActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTextView_title;
    private String title;
    private ListView mListView;
    private String sublist;
    private List<KaoshiZxContent> mList;
    private KaoshiPxAdapter mPxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialcertification_list);
        sublist = getIntent().getStringExtra("list");
        initView();
        initData();
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        mTextView_title = findViewById(R.id.titlebar_title);
        mTextView_title.setText(title);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        mListView = findViewById(R.id.scfl_lv);
    }

    private void initData() {
        mList = new ArrayList<>();
        mList = JSON.parseArray(sublist, KaoshiZxContent.class);
        mPxAdapter = new KaoshiPxAdapter(mList, this);
        mListView.setAdapter(mPxAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(SpecialcertificationListActivity.this, KaoshicontentActivity.class).
                        putExtra("id", mList.get(i).getId()).putExtra("cate", mList.get(i).getIdentifier()).
                        putExtra("type", "zhuangxiang").putExtra("fen",mList.get(i).getFen()));

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
