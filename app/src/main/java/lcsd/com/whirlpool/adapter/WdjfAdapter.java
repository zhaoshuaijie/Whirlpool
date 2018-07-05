package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.MyPoint;
import lcsd.com.whirlpool.util.StringUtils;

/**
 * Created by Administrator on 2017/9/29.
 */
public class WdjfAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyPoint> list;

    public WdjfAdapter(Context mContext, List<MyPoint> list) {
        this.mContext = mContext;
        this.list = list;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_wdjf, null);
            viewHodle.tv_time = (TextView) view.findViewById(R.id.tv_wdjf_time);
            viewHodle.tv_content = (TextView) view.findViewById(R.id.tv_wdjf_content);
            viewHodle.tv_jf = (TextView) view.findViewById(R.id.tv_wdjf_fen);
            view.setTag(viewHodle);
        } else {
            viewHodle = (ViewHodle) view.getTag();
        }

        viewHodle.tv_time.setText(StringUtils.timeStamptoYearDate(list.get(i).getDateline()));
        viewHodle.tv_content.setText(list.get(i).getNote());
        viewHodle.tv_jf.setText("+ "+list.get(i).getVal());

        return view;
    }

    class ViewHodle {
        private TextView tv_time, tv_content, tv_jf;
    }
}
