package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.Baodiannext2Activity;
import lcsd.com.whirlpool.activity.BaodiannextActivity;
import lcsd.com.whirlpool.entity.Product;
import lcsd.com.whirlpool.entity.TRslist;
import lcsd.com.whirlpool.view.CanAddInListViewGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MyBaseExpandableListWithGridView_Adapter extends BaseExpandableListAdapter {

    List<Map<String, Object>> parentMapList;
    List<List<Map<String, Object>>> childMapList_list;
    Context context;
    List<Map<String, Object>> nowChildMapList;
    ArrayList<Product.TTree.OSublist> tlist;
    public static ArrayList<Product.TTree.OSublist.TSublist.SSublist> LIST=new ArrayList<>();
    public static ArrayList<TRslist> RLIST=new ArrayList<>();
    public MyBaseExpandableListWithGridView_Adapter(Context context, List<Map<String, Object>> parentMapList, List<List<Map<String, Object>>> childMapList_list, ArrayList<Product.TTree.OSublist> tlist) {
        this.parentMapList = parentMapList;
        this.childMapList_list = childMapList_list;
        this.tlist = tlist;
        this.context = context;
    }

    //获取当前父item的数据数量
    @Override
    public int getGroupCount() {
        return parentMapList.size();
    }

    //获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        //子列表项的数量
        // return childMapList_list.get(groupPosition).size();
        //子列表项的数量本来是list  多个  现在同样的数据以girdview形式展示  就只有个girdview项 所以返回1
        return 1;
    }

    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return parentMapList.get(groupPosition);
    }

    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childMapList_list.get(groupPosition).get(childPosition);
    }

    //得到父item的ID
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        //return false;
        return true;
    }

    //设置父item组件
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.parent_layout, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tv_pinpai);
        tv.setText(parentMapList.get(groupPosition).get("parentName") + "");

        //判断isExpanded就可以控制是按下还是关闭，同时更换图片
        if (isExpanded) {

        } else {

        }
        return convertView;
    }

    //设置子item的组件
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.child_layout_girdview, null);
        }
        CanAddInListViewGridView canAddInListViewGridView = (CanAddInListViewGridView) convertView.findViewById(R.id.channel_item_child_gridView);
        List<Map<String, Object>> childMapList = childMapList_list.get(groupPosition);
        nowChildMapList = childMapList_list.get(groupPosition);
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, childMapList, R.layout.gridview_item, new String[]{"childName"}, new int[]{R.id.id_tv_gridview_item});
        canAddInListViewGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Product.TTree.OSublist.TSublist.SSublist> list = (ArrayList<Product.TTree.OSublist.TSublist.SSublist>) tlist.get(groupPosition).getSublist().get(position).getSublist();
                if(tlist.get(groupPosition).getSublist().get(position).getRslist()!=null&&tlist.get(groupPosition).getSublist().get(position).getRslist().size()!=0){
                    if(RLIST!=null){
                        RLIST.clear();
                    }
                    RLIST.addAll(tlist.get(groupPosition).getSublist().get(position).getRslist());
                    context.startActivity(new Intent(context, Baodiannext2Activity.class).putExtra("title",tlist.get(groupPosition).getSublist().get(position).getTitle()));
                }else{
                    if(LIST!=null){
                        LIST.clear();
                    }
                    if (list!=null){
                        LIST.addAll(list);
                    }
                    context.startActivity(new Intent(context, BaodiannextActivity.class).putExtra("title",tlist.get(groupPosition).getSublist().get(position).getTitle()));
                }

            }
        });
        canAddInListViewGridView.setAdapter(simpleAdapter);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // return false;
        return true;
    }
}
