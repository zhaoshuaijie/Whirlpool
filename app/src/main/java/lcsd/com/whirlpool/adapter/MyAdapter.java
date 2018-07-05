package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.JiqiaoNext;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */
public class MyAdapter extends BaseAdapter {
    private List<JiqiaoNext.TRslist> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public MyAdapter(Context context, List<JiqiaoNext.TRslist> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        MyViewHodle viewHodle = null;
        if (view == null) {
            viewHodle=new MyViewHodle();
            view = layoutInflater.inflate(R.layout.item_listview_text, null);
            viewHodle.tv = (TextView) view.findViewById(R.id.tv_text);
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
