package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.ZhuantiNext2;
import lcsd.com.whirlpool.http.AppConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */
public class Gv_Adapter2 extends BaseAdapter {
    private Context context;
    private List<ZhuantiNext2.TRslist> list;
    private LayoutInflater layoutInflater;

    public Gv_Adapter2(Context context, List<ZhuantiNext2.TRslist> list) {
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
        MyViewHodle viewHodle = null;
        if (view == null) {
            viewHodle = new MyViewHodle();
            view = layoutInflater.inflate(R.layout.item_gv, null);
            viewHodle.iv = (ImageView) view.findViewById(R.id.item_gv_iv);
            view.setTag(viewHodle);
        } else {
            viewHodle = (MyViewHodle) view.getTag();
        }
        Glide.with(context).load(AppConfig.mainurl + list.get(i).getThumb()).into(viewHodle.iv);
        return view;
    }

    class MyViewHodle {
        private ImageView iv;
    }
}
