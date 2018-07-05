package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.Zhuanti;

/**
 * Created by Administrator on 2017/8/14.
 */
public class Zhuanti2Adapter extends BaseAdapter{
    private Context context;
    private List<Zhuanti> list;
    private LayoutInflater layoutInflater;
    public Zhuanti2Adapter(Context context,List<Zhuanti> list){
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
            view = layoutInflater.inflate(R.layout.item_zt2, null);
            viewHodle.tv = (TextView) view.findViewById(R.id.item_tv_zt);
            view.setTag(viewHodle);
        } else {
            viewHodle = (ViewHodle) view.getTag();
        }
        viewHodle.tv.setText(list.get(i).getTitle());
        return view;
    }
    class ViewHodle{
        TextView tv;
    }
}
