package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.ZhuantiNext;

/**
 * Created by Administrator on 2017/8/15.
 */
public class Zhuanti3Adapter extends BaseAdapter{
    private Context context;
    private List<ZhuantiNext.TRslist> list;
    private LayoutInflater layoutInflater;
    public Zhuanti3Adapter(Context context,List<ZhuantiNext.TRslist> list){
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
        ViewHodle viewHodle = null;
        if (view == null) {
            viewHodle=new ViewHodle();
            view = layoutInflater.inflate(R.layout.item_zt3, null);
            viewHodle.tv = (TextView) view.findViewById(R.id.item_tv_zt3);
            viewHodle.tv_hit= (TextView) view.findViewById(R.id.tv_hit);
            view.setTag(viewHodle);
        } else {
            viewHodle = (ViewHodle) view.getTag();
        }
        viewHodle.tv.setText(list.get(i).getTitle());
        viewHodle.tv_hit.setText("浏览："+list.get(i).getHits());
        return view;
    }
    class ViewHodle{
        TextView tv,tv_hit;
    }
}
