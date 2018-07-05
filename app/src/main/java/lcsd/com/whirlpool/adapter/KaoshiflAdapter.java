package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.Kaoshifl;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public class KaoshiflAdapter extends BaseAdapter {
    private List<Kaoshifl.TTree.OSublist.TSublist> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public KaoshiflAdapter(List<Kaoshifl.TTree.OSublist.TSublist> list,Context context){
        this.list=list;
        this.context=context;
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
            view = layoutInflater.inflate(R.layout.item_ks_fl, null);
            viewHodle.tv = (TextView) view.findViewById(R.id.tv_ksfl);
            view.setTag(viewHodle);
        } else {
            viewHodle = (MyViewHodle) view.getTag();
        }
        viewHodle.tv.setText(list.get(i).getTitle());
        return view;
    }

    class MyViewHodle {
        private TextView tv;
    }
}
