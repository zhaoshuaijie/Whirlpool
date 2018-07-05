package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.BaodianContentActivity;
import lcsd.com.whirlpool.entity.Product;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/5.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Product.TTree.OSublist.TSublist.SSublist> List;

    public ExpandableAdapter(Context context, ArrayList<Product.TTree.OSublist.TSublist.SSublist> List) {
        this.context = context;
        this.List = List;
    }

    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return List.get(groupPosition).getRslist().get(childPosition);
    }

    //得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //设置子item的组件
    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_children, null);
        }
        TextView tv = (TextView) convertView
                .findViewById(R.id.second_textview);
        tv.setText(List.get(groupPosition).getRslist().get(childPosition).getTitle());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, BaodianContentActivity.class).putExtra("title", List.get(groupPosition).getRslist().get(childPosition).getTitle()).putExtra("id", List.get(groupPosition).getRslist().get(childPosition).getId()));
            }
        });
        return convertView;
    }

    //获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return List.get(groupPosition).getRslist().size();
    }

    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return List.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return List.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //设置父item组件
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_parent, null);
        }
        TextView tv = (TextView) convertView
                .findViewById(R.id.parent_textview);
        ImageView iv = (ImageView) convertView.findViewById(R.id.parent_iv);
        tv.setText(List.get(groupPosition).getTitle());
        if (isExpanded) {
            iv.setImageResource(R.mipmap.jtx);
        } else {
            iv.setImageResource(R.mipmap.jty);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
