package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.ScList;
import lcsd.com.whirlpool.http.AppConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
public class ScListAdapter extends BaseAdapter{
    private List<ScList> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public ScListAdapter(Context context, List<ScList> list){
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
            view = layoutInflater.inflate(R.layout.item_sclist, null);
            viewHodle.tv = (TextView) view.findViewById(R.id.item_sc_tv);
            viewHodle.tv_total= (TextView) view.findViewById(R.id.tv_total);
            viewHodle.iv= (ImageView) view.findViewById(R.id.item_sc_ico);
            view.setTag(viewHodle);
        } else {
            viewHodle = (MyViewHodle) view.getTag();
        }
        viewHodle.tv.setText(list.get(i).getTitle());
        viewHodle.tv_total.setText(list.get(i).getTotal());
        Glide.with(context).load(AppConfig.mainurl+list.get(i).getIco()).into(viewHodle.iv);
        return view;
    }

    class MyViewHodle {
        private TextView tv,tv_total;
        private ImageView iv;
    }
}
