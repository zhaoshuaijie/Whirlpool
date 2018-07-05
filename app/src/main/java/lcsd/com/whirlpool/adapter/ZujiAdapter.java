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
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.sql.Sqlentity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
public class ZujiAdapter extends BaseAdapter{
    private List<Sqlentity> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public ZujiAdapter(Context context, List<Sqlentity> list){
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
            view = layoutInflater.inflate(R.layout.item_zuji, null);
            viewHodle.tv = (TextView) view.findViewById(R.id.item_zj_title);
            viewHodle.iv= (ImageView) view.findViewById(R.id.item_zj_iv);
            viewHodle.tv1= (TextView) view.findViewById(R.id.item_zj_sj);
            view.setTag(viewHodle);
        } else {
            viewHodle = (MyViewHodle) view.getTag();
        }
        viewHodle.tv1.setText("浏览时间："+list.get(i).getTime());
        viewHodle.tv.setText(list.get(i).getTitle());
        Glide.with(context).load(AppConfig.mainurl+list.get(i).getImg()).into(viewHodle.iv);
        return view;
    }

    class MyViewHodle {
        private TextView tv,tv1;
        private ImageView iv;
    }
}
