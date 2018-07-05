package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.Kuaixun;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */
public class KuaixunAdapter extends BaseAdapter{
    private List<Kuaixun.TRslist> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public KuaixunAdapter(Context context, List<Kuaixun.TRslist> list){
        this.context = context;
        this.list = list;
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
        MyViewHodle viewHodle = null;
        if (view == null) {
            viewHodle=new MyViewHodle();
            view = layoutInflater.inflate(R.layout.item_adapter_kuaixun, null);
            viewHodle.tv = (TextView) view.findViewById(R.id.item_tv_kuaixun);
            viewHodle.tv_hit= (TextView) view.findViewById(R.id.item_tv_kuaixun_hits);
            view.setTag(viewHodle);
        } else {
            viewHodle = (MyViewHodle) view.getTag();
        }
        viewHodle.tv.setText(list.get(i).getTitle());
        viewHodle.tv_hit.setText("浏览："+list.get(i).getHits());
        return view;
    }

    class MyViewHodle {
        private TextView tv,tv_hit;
    }
}
