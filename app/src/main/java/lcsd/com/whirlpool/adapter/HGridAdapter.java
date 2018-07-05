package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.Zhuanti;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */
public class HGridAdapter extends BaseAdapter{
    private Context context;
    private List<Zhuanti> list;
    private LayoutInflater layoutInflater;
    public HGridAdapter(Context context,List<Zhuanti> list){
        this.context=context;
        this.list=list;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=layoutInflater.inflate(R.layout.item_hgrid,null);
        final TextView tv= (TextView) view.findViewById(R.id.hg_tv);
        tv.setText(list.get(i).getTitle());
        if(list.get(i).istrue()){
            tv.setTextColor(context.getResources().getColor(R.color.white));
            tv.setBackgroundResource(R.color.darkblue);
        }else {
            tv.setTextColor(context.getResources().getColor(R.color.gray2));
            tv.setBackgroundResource(R.color.color_huise);
        }
        /*tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setTextColor(context.getResources().getColor(R.color.gray2));
                tv.setBackgroundResource(R.color.color_huise);
            }
        });*/
        return view;
    }
}
