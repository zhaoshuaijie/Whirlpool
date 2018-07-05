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
import lcsd.com.whirlpool.entity.NewsListAll;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.util.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public class ZixunAdapter extends BaseAdapter{
    private Context context;
    private List<NewsListAll.Rslist> list;
    private LayoutInflater layoutInflater;
    public ZixunAdapter(Context context,List<NewsListAll.Rslist> list){
        this.context=context;
        this.list=list;
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
        ViewHodle viewHodle=null;
        if(view==null){
            viewHodle=new ViewHodle();
            view=layoutInflater.inflate(R.layout.item_zixun,null);
            viewHodle.tv1= (TextView) view.findViewById(R.id.zixun_tv1);
            viewHodle.tv2= (TextView) view.findViewById(R.id.zixun_tv2);
            viewHodle.iv= (ImageView) view.findViewById(R.id.zixun_iv);
            viewHodle.tv_lll= (TextView) view.findViewById(R.id.tv_lll);
            viewHodle.tv_time= (TextView) view.findViewById(R.id.tv_time);
            view.setTag(viewHodle);
        }else{
            viewHodle= (ViewHodle) view.getTag();
        }
        viewHodle.tv1.setText(list.get(i).getTitle());
        viewHodle.tv2.setText(list.get(i).getNote());
        viewHodle.tv_lll.setText("浏览："+list.get(i).getHits());
        viewHodle.tv_time.setText(StringUtils.timeStamp2Date(list.get(i).getDateline()));
        Glide.with(context).
                load(AppConfig.mainurl + list.get(i).getThumb()).into(viewHodle.iv);
        return view;
    }
    class ViewHodle{
        TextView tv1,tv2,tv_lll,tv_time;
        ImageView iv;
    }
}
