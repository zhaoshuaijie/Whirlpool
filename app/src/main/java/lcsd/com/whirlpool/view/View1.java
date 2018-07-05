package lcsd.com.whirlpool.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.ImagePageActivity;
import lcsd.com.whirlpool.adapter.ImgAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */
public class View1 extends FrameLayout {
    private static View1 view1 = null;
    private Context context;
    private ImgAdapter adapter;
    private String[] imgs;
    private ListView lv;
    private List<String> list;

    public static View1 getInstance(Context context, List<String> list) {
        if (view1 == null)
            return new View1(context, list);
        return view1;
    }

    public View1(Context context, List<String> list) {
        super(context);
        this.context = context;
        this.list = list;
        LayoutInflater.from(context).inflate(R.layout.view1, this);
        initView();
        initData();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv_v1);
        if (list != null) {
            imgs = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                imgs[i] = list.get(i);
            }
        }
    }

    private void initData() {
        adapter = new ImgAdapter(context, imgs);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                context.startActivity(new Intent(context, ImagePageActivity.class).putExtra("imgs", imgs).putExtra("index", i));
            }
        });
    }


}
