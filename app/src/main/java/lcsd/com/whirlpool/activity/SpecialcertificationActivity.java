package lcsd.com.whirlpool.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.KaoshiflzxpxAdapter;
import lcsd.com.whirlpool.entity.Kaoshifl;
import lcsd.com.whirlpool.manager.ActivityManager;

/**
 * 培训师认证 初中高级列表
 */
public class SpecialcertificationActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private ListView mListView;
    private ArrayList<Kaoshifl.TTree.OSublist.TSublist> list_zx;
    private KaoshiflzxpxAdapter adapter_zx;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialcertification);
        mContext = this;
        list_zx = (ArrayList<Kaoshifl.TTree.OSublist.TSublist>) getIntent().getSerializableExtra("list");
        initView();
        initData();
    }

    private void initView() {
        title = findViewById(R.id.titlebar_title);
        title.setText("培训师认证考核");
        mListView = findViewById(R.id.lv_zxpx);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
    }

    private void initData() {
        adapter_zx = new KaoshiflzxpxAdapter(list_zx, this);
        mListView.setAdapter(adapter_zx);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!list_zx.get(i).isLock()) {
                    if (list_zx.get(i).getSublist() != null && list_zx.get(i).getSublist().length() > 0) {
                        startActivity(new Intent(SpecialcertificationActivity.this, SpecialcertificationListActivity.class).putExtra("list", list_zx.get(i).getSublist()));
                    }
                } else {
                    Toast.makeText(mContext, "请先通过上一级考核！", Toast.LENGTH_SHORT).show();
                }
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
