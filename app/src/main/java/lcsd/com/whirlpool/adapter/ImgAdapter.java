package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.http.AppConfig;

/**
 * Created by Administrator on 2017/6/29.
 */
public class ImgAdapter extends BaseAdapter{
    private Context context;
    private String[] imgs;
    private LayoutInflater layoutInflater;
    public ImgAdapter(Context context,String[] imgs){
        this.context=context;
        this.imgs=imgs;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int i) {
        return imgs[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodle viewHodle = null;
        if (view == null) {
            viewHodle=new ViewHodle();
            view = layoutInflater.inflate(R.layout.item_img, null);
            viewHodle.iv = (ImageView) view.findViewById(R.id.iv_img);
            view.setTag(viewHodle);
        } else {
            viewHodle = (ViewHodle) view.getTag();
        }
        Glide.with(context).load(AppConfig.mainurl+imgs[i]).into(viewHodle.iv);
        return view;
    }
    class ViewHodle {
        private ImageView iv;
    }
}
