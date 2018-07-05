package lcsd.com.whirlpool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.MyBaseExpandableListWithGridView_Adapter;
import lcsd.com.whirlpool.entity.Product;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.view.CustomExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaodianActivity extends BaseActivity implements View.OnClickListener {
    private TextView title, tv_xyj, tv_bx;
    //定义父列表项List数据集合
    private List<Map<String, Object>> parentMapList = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> parentMapList1 = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> parentMapList2 = new ArrayList<Map<String, Object>>();
    //定义子列表项List数据集合
    private List<List<Map<String, Object>>> childMapList_list = new ArrayList<List<Map<String, Object>>>();
    private List<List<Map<String, Object>>> childMapList_list1 = new ArrayList<List<Map<String, Object>>>();
    private List<List<Map<String, Object>>> childMapList_list2 = new ArrayList<List<Map<String, Object>>>();

    private ArrayList<Product.TTree.OSublist> list1;
    private ArrayList<Product.TTree.OSublist> list2;
    private List<Product.TTree.OSublist> list;
    private ArrayList<Product.TTree.OSublist.TSublist> tlist1;
    private ArrayList<Product.TTree.OSublist.TSublist> tlist2;

    private CustomExpandableListView expandableListView;
    private MyBaseExpandableListWithGridView_Adapter adapter;
    private ImageView iv1, iv2;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baodian);

        initView();
        initData();
        requestData();
    }


    private void initView() {
        title = (TextView) findViewById(R.id.titlebar_title);
        title.setText("产品宝典");
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        iv1 = (ImageView) findViewById(R.id.baodian_iv1);
        iv2 = (ImageView) findViewById(R.id.baodian_iv2);
        tv_bx = (TextView) findViewById(R.id.tv_bingxiang);
        tv_xyj = (TextView) findViewById(R.id.tv_xiyiji);
        tv_xyj.setOnClickListener(onClickListener);
        tv_bx.setOnClickListener(onClickListener);

    }

    private void initData() {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        tlist1 = new ArrayList<>();
        tlist2 = new ArrayList<>();
        expandableListView = (CustomExpandableListView) findViewById(R.id.baodian_elv);
    }

    private void requestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "products");
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    product = JSON.parseObject(json, Product.class);
                    if (product.getTree().get(0).getSublist() != null && product.getTree().get(0).getSublist().size() != 0) {
                        list1.addAll(product.getTree().get(0).getSublist());
                        for (int i = 0; i < product.getTree().get(0).getSublist().size(); i++) {
                            //提供父列表的数据
                            Map<String, Object> parentMap = new HashMap<String, Object>();
                            parentMap.put("parentName", product.getTree().get(0).getSublist().get(i).getTitle());
                            parentMapList1.add(parentMap);
                            //提供当前父列的子列数据
                            List<Map<String, Object>> childMapList = new ArrayList<Map<String, Object>>();
                            if (product.getTree().get(0).getSublist().get(i).getSublist() != null) {
                                for (int j = 0; j < product.getTree().get(0).getSublist().get(i).getSublist().size(); j++) {
                                    Map<String, Object> childMap = new HashMap<String, Object>();
                                    childMap.put("childName", product.getTree().get(0).getSublist().get(i).getSublist().get(j).getTitle());
                                    childMapList.add(childMap);
                                }
                            }
                            childMapList_list1.add(childMapList);
                        }
                    }
                    if (product.getTree().get(1).getSublist() != null && product.getTree().get(1).getSublist().size() != 0) {
                        list2.addAll(product.getTree().get(1).getSublist());
                        for (int i = 0; i < product.getTree().get(1).getSublist().size(); i++) {
                            //提供父列表的数据
                            Map<String, Object> parentMap = new HashMap<String, Object>();
                            parentMap.put("parentName", product.getTree().get(1).getSublist().get(i).getTitle());
                            parentMapList2.add(parentMap);
                            //提供当前父列的子列数据
                            List<Map<String, Object>> childMapList = new ArrayList<Map<String, Object>>();
                            if (product.getTree().get(1).getSublist().get(i).getSublist() != null) {
                                for (int j = 0; j < product.getTree().get(1).getSublist().get(i).getSublist().size(); j++) {
                                    Map<String, Object> childMap = new HashMap<String, Object>();
                                    childMap.put("childName", product.getTree().get(1).getSublist().get(i).getSublist().get(j).getTitle());
                                    childMapList.add(childMap);
                                }
                            }
                            childMapList_list2.add(childMapList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(BaodianActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tv_bx.setBackgroundResource(R.color.darkblue);
            tv_xyj.setBackgroundResource(R.color.darkblue);
            tv_xyj.setTextColor(getResources().getColor(R.color.white));
            tv_bx.setTextColor(getResources().getColor(R.color.white));
            iv1.setVisibility(View.INVISIBLE);
            iv2.setVisibility(View.INVISIBLE);
            switch (view.getId()) {
                case R.id.tv_xiyiji:
                    tv_xyj.setBackgroundResource(R.color.color_huise);
                    tv_xyj.setTextColor(getResources().getColor(R.color.black));
                    iv1.setVisibility(View.VISIBLE);
                    if (parentMapList.size() != 0) {
                        parentMapList.clear();
                    }
                    if (childMapList_list.size() != 0) {
                        childMapList_list.clear();
                    }
                    parentMapList.addAll(parentMapList1);
                    childMapList_list.addAll(childMapList_list1);
                    adapter = new MyBaseExpandableListWithGridView_Adapter(BaodianActivity.this, parentMapList, childMapList_list, list1);
                    expandableListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.tv_bingxiang:
                    tv_bx.setBackgroundResource(R.color.color_huise);
                    tv_bx.setTextColor(getResources().getColor(R.color.black));
                    iv2.setVisibility(View.VISIBLE);

                    if (parentMapList.size() != 0) {
                        parentMapList.clear();
                    }
                    if (childMapList_list.size() != 0) {
                        childMapList_list.clear();
                    }
                    parentMapList.addAll(parentMapList2);
                    childMapList_list.addAll(childMapList_list2);
                    adapter = new MyBaseExpandableListWithGridView_Adapter(BaodianActivity.this, parentMapList, childMapList_list, list2);
                    expandableListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
