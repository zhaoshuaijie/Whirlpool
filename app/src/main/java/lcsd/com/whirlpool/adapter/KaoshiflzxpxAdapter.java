package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.Kaoshifl;

/**
 * Created by jie on 2018/7/6.
 * 专项测试培训师认证 初中高级列表适配器
 */
public class KaoshiflzxpxAdapter extends BaseAdapter {
    private List<Kaoshifl.TTree.OSublist.TSublist> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public KaoshiflzxpxAdapter(List<Kaoshifl.TTree.OSublist.TSublist> list, Context context) {
        this.list = list;
        this.context = context;
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
        MyViewHodle viewHodle = null;
        if (view == null) {
            viewHodle = new MyViewHodle();
            view = layoutInflater.inflate(R.layout.item_ks_fl_px, null);
            viewHodle.tv = view.findViewById(R.id.tv_ksfl);
            viewHodle.iv = view.findViewById(R.id.iv_ksfl);
            view.setTag(viewHodle);
        } else {
            viewHodle = (MyViewHodle) view.getTag();
        }
        viewHodle.tv.setText(list.get(i).getTitle());
        if (list.get(i).isLock()) {
            viewHodle.iv.setImageResource(R.drawable.img_lock);
        } else {
            viewHodle.iv.setImageResource(0);
        }
        return view;
    }

    class MyViewHodle {
        private TextView tv;
        private ImageView iv;
    }
}
