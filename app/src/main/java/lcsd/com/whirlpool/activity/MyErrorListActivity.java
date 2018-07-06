package lcsd.com.whirlpool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.MyErrorAdapter;
import lcsd.com.whirlpool.entity.Kaoshi;
import lcsd.com.whirlpool.manager.ActivityManager;

/**
 * 错题展示页面
 */
public class MyErrorListActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<Kaoshi> mList;
    private TextView tv_title;
    private ListView mListView;
    private MyErrorAdapter mMyErrorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_error_list);
        mList = (ArrayList<Kaoshi>) getIntent().getSerializableExtra("errorlist");
        initView();
        initData();
    }

    private void initView() {
        if (mList == null || mList.size() == 0) {
            finish();
        }
        tv_title = findViewById(R.id.titlebar_title);
        mListView = findViewById(R.id.livt_error);
        tv_title.setText("我的错题");
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
    }

    private void initData() {
        mMyErrorAdapter = new MyErrorAdapter(mList, this);
        mListView.setAdapter(mMyErrorAdapter);
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
