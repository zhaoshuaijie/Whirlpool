package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.FeedBack;

/**
 * Created by jie on 2018/6/15.
 * 建议反馈列表
 */
public class FeedBackAdaper extends BaseAdapter {
    private Context context;
    private List<FeedBack> list;
    private LayoutInflater layoutInflater;

    public FeedBackAdaper(Context context, List<FeedBack> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
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
            viewHodle = new ViewHodle();
            view = layoutInflater.inflate(R.layout.item_feedback, null);
            viewHodle.tv = view.findViewById(R.id.tv_feedback);
            view.setTag(viewHodle);
        } else {
            viewHodle = (ViewHodle) view.getTag();
        }
        viewHodle.tv.setText(list.get(i).getTitle());
        return view;
    }

    class ViewHodle {
        TextView tv;
    }
}
