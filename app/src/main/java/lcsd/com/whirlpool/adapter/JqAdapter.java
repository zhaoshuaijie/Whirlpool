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
 * Created by Administrator on 2017/6/29.
 */
public class JqAdapter extends BaseAdapter{
    private List<JiqiaoNext.TRslist> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public JqAdapter(Context context, List<JiqiaoNext.TRslist> list) {
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
        ViewHodle viewHodle = null;
        if (view == null) {
            viewHodle=new ViewHodle();
            view = layoutInflater.inflate(R.layout.item_jq2, null);
            viewHodle.tv = (TextView) view.findViewById(R.id.jq_tv);
            view.setTag(viewHodle);
        } else {
            viewHodle = (ViewHodle) view.getTag();
        }
        viewHodle.tv.setText(list.get(i).getTitle());
        return view;
    }
    class ViewHodle {
        private TextView tv;
    }
}
