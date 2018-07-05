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
import lcsd.com.whirlpool.entity.Zhibo;
import lcsd.com.whirlpool.http.AppConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
public class ZhiboAdapter extends BaseAdapter{
    private List<Zhibo.TRslist> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public ZhiboAdapter(Context context, List<Zhibo.TRslist> list){
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
            view = layoutInflater.inflate(R.layout.item_zblist, null);
            viewHodle.tv = (TextView) view.findViewById(R.id.item_sc_title);
            viewHodle.iv= (ImageView) view.findViewById(R.id.item_sc_iv);
            viewHodle.tv_is= (TextView) view.findViewById(R.id.tv_iszhibo);
            view.setTag(viewHodle);
        } else {
            viewHodle = (MyViewHodle) view.getTag();
        }
        viewHodle.tv.setText(list.get(i).getTitle());
        Glide.with(context).load(AppConfig.mainurl+list.get(i).getThumb()).into(viewHodle.iv);
        if(list.get(i).getZb_status()!=null&&list.get(i).getZb_status().equals("1")){
            viewHodle.tv_is.setText("进入直播间");
        }else {
            viewHodle.tv_is.setText("进入回看");
        }
        return view;
    }
    class MyViewHodle {
        private TextView tv,tv_is;
        private ImageView iv;
    }
}
