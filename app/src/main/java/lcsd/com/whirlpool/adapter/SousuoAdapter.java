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
import lcsd.com.whirlpool.entity.Shousuo;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.util.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public class SousuoAdapter extends BaseAdapter{
    private List<Shousuo.TRslist> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public SousuoAdapter(Context context, List<Shousuo.TRslist> list){
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
            view = layoutInflater.inflate(R.layout.item_sousuo, null);
            viewHodle.tv = (TextView) view.findViewById(R.id.item_sc_title);
            viewHodle.iv= (ImageView) view.findViewById(R.id.item_sc_iv);
            view.setTag(viewHodle);
        } else {
            viewHodle = (MyViewHodle) view.getTag();
        }
        viewHodle.tv.setText("产品型号："+list.get(i).getTitle());
        Glide.with(context).load(AppConfig.mainurl+list.get(i).getThumb()).into(viewHodle.iv);
        return view;
    }

    class MyViewHodle {
        private TextView tv;
        private ImageView iv;
    }
}
