package lcsd.com.whirlpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.KaoshiflzxAdapter;
import lcsd.com.whirlpool.entity.Kaoshifl;
import lcsd.com.whirlpool.manager.ActivityManager;

public class SpecialgeneralActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private ListView mListView;
    private ArrayList<Kaoshifl.TTree.OSublist.TSublist> list_zx;
    private KaoshiflzxAdapter adapter_zx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialgeneral);
        list_zx = (ArrayList<Kaoshifl.TTree.OSublist.TSublist>) getIntent().getSerializableExtra("list");
        initView();
        initData();
    }

    private void initView() {
        if (list_zx == null) {
            finish();
        }
        title = findViewById(R.id.titlebar_title);
        mListView = findViewById(R.id.lv_zx);
        title.setText("其他考试");
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
    }

    private void initData() {
        adapter_zx = new KaoshiflzxAdapter(list_zx, this);
        mListView.setAdapter(adapter_zx);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(SpecialgeneralActivity.this, KaoshicontentActivity.class).
                        putExtra("id", list_zx.get(i).getId()).putExtra("cate", list_zx.get(i).getIdentifier()).
                        putExtra("type", "zhuanxiang"));
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
