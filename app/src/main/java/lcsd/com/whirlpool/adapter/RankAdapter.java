package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.Rank;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.view.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/27.
 */
public class RankAdapter extends BaseAdapter{
    private List<Rank.TRslist> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public RankAdapter( List<Rank.TRslist> list,Context context){
        this.list=list;
        this.context=context;
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
        if(i<3){
            view=layoutInflater.inflate(R.layout.item_rank1,null);
            TextView tv_name= (TextView) view.findViewById(R.id.tv_name_rank1);
            TextView tv_jf= (TextView) view.findViewById(R.id.tv_jifen_rank1);
            TextView tv_mc= (TextView) view.findViewById(R.id.tv_mingci_rank1);
            CircleImageView iv= (CircleImageView) view.findViewById(R.id.iv_head_rank1);
            ImageView iv_h= (ImageView) view.findViewById(R.id.iv_h_rank1);

            if(list.get(i).getAvatar()!=null&&list.get(i).getAvatar().toString().length()>1){
                RequestOptions options = new RequestOptions();
                options.dontAnimate();
                options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                options.fitCenter();
                Glide.with(context).load(AppConfig.mainurl+list.get(i).getAvatar()).apply(options).into(iv);
            }else {
                iv.setImageResource(R.drawable.img_head);
            }
            switch (i){
                case 0:
                    iv_h.setImageResource(R.drawable.rank1);
                    break;
                case 1:
                    iv_h.setImageResource(R.drawable.rank2);
                    break;
                case 2:
                    iv_h.setImageResource(R.drawable.rank3);
                    break;
            }
            tv_name.setText(list.get(i).getFullname());
            tv_jf.setText(list.get(i).getPoint());
            tv_mc.setText(list.get(i).getRank()+"");
            return view;
        }else {
            ViewHodle viewHodle=new ViewHodle();
                view=layoutInflater.inflate(R.layout.item_rank2,null);
                viewHodle.tv_name= (TextView) view.findViewById(R.id.tv_name_rank2);
                viewHodle.tv_jf= (TextView) view.findViewById(R.id.tv_jifen_rank2);
                viewHodle.tv_mc= (TextView) view.findViewById(R.id.tv_mingci_rank2);
                viewHodle.iv= (CircleImageView) view.findViewById(R.id.iv_head_rank2);
            if(list.get(i).getAvatar()!=null&&list.get(i).getAvatar().toString().length()>1){
                RequestOptions options = new RequestOptions();
                options.dontAnimate();
                options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                options.fitCenter();
                Glide.with(context).load(AppConfig.mainurl+list.get(i).getAvatar()).apply(options).into(viewHodle.iv);
            }else {
                viewHodle.iv.setImageResource(R.drawable.img_head);
            }
            viewHodle.tv_name.setText(list.get(i).getFullname());
            viewHodle.tv_jf.setText(list.get(i).getPoint());
            viewHodle.tv_mc.setText(list.get(i).getRank()+"");

            return view;
        }
    }
    class ViewHodle{
        TextView tv_name,tv_jf,tv_mc;
        CircleImageView iv;
    }
}
