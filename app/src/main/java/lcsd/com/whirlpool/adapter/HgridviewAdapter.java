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
import lcsd.com.whirlpool.entity.Sy;
import lcsd.com.whirlpool.http.AppConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public class HgridviewAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater listContainer;
    private List<Sy.Products> list;
    public HgridviewAdapter(Context context,List<Sy.Products> list){
        this.context=context;
        this.list=list;
        listContainer=LayoutInflater.from(context);
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
        view=listContainer.inflate(R.layout.item_hgridview,null);
        ImageView iv= (ImageView) view.findViewById(R.id.hgridview_iv);
        TextView tv1= (TextView) view.findViewById(R.id.xinghao);
        TextView tv2= (TextView) view.findViewById(R.id.maidian);
        Glide.with(context).
                load(AppConfig.mainurl+list.get(i).getThumb()).into(iv);
        tv1.setText("型号："+list.get(i).getType());
        tv2.setText("浏览："+list.get(i).getHits());
        return view;

    }
}
