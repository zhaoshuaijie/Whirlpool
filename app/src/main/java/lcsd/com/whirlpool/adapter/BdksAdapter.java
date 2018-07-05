package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.Kaoshi;
import lcsd.com.whirlpool.http.AppConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public class BdksAdapter extends BaseAdapter{
    private List<Kaoshi> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public BdksAdapter(List<Kaoshi> list, Context context) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHodle hodle=null;
        if(view==null){
            hodle=new ViewHodle();
            view=layoutInflater.inflate(R.layout.item_ks,null);
            hodle.tv_tm= (TextView) view.findViewById(R.id.item_kstm);
            hodle.tv_a= (TextView) view.findViewById(R.id.item_tv_A);
            hodle.tv_b= (TextView) view.findViewById(R.id.item_tv_B);
            hodle.tv_c= (TextView) view.findViewById(R.id.item_tv_C);
            hodle.tv_d= (TextView) view.findViewById(R.id.item_tv_D);
            hodle.iv_a= (ImageView) view.findViewById(R.id.item_iv_A);
            hodle.iv_b= (ImageView) view.findViewById(R.id.item_iv_B);
            hodle.iv_c= (ImageView) view.findViewById(R.id.item_iv_C);
            hodle.iv_d= (ImageView) view.findViewById(R.id.item_iv_D);
            hodle.ll_a= (LinearLayout) view.findViewById(R.id.item_ll_A);
            hodle.ll_b= (LinearLayout) view.findViewById(R.id.item_ll_B);
            hodle.ll_c= (LinearLayout) view.findViewById(R.id.item_ll_C);
            hodle.ll_d= (LinearLayout) view.findViewById(R.id.item_ll_D);
            hodle.iv= (ImageView) view.findViewById(R.id.item_kstp);
            view.setTag(hodle);
        }else{
            hodle= (ViewHodle) view.getTag();
        }
        if(list.get(i).getImg()!=null){
            hodle.iv.setVisibility(View.VISIBLE);
            Glide.with(context).load(AppConfig.mainurl+list.get(i).getImg()).into(hodle.iv);
        }else {
            hodle.iv.setVisibility(View.GONE);
        }
        hodle.tv_tm.setText(list.get(i).getTimu());
        hodle.tv_a.setText("A："+list.get(i).getA());
        hodle.tv_b.setText("B："+list.get(i).getB());
        hodle.tv_c.setText("C："+list.get(i).getC());
        hodle.tv_d.setText("D："+list.get(i).getD());
        final ViewHodle finalHodle = hodle;
        hodle.ll_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalHodle.iv_a.setImageResource(R.drawable.img_rdy);
                finalHodle.iv_b.setImageResource(R.drawable.img_rdw);
                finalHodle.iv_c.setImageResource(R.drawable.img_rdw);
                finalHodle.iv_d.setImageResource(R.drawable.img_rdw);
                list.get(i).setCheck("A");
            }
        });
        hodle.ll_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalHodle.iv_a.setImageResource(R.drawable.img_rdw);
                finalHodle.iv_b.setImageResource(R.drawable.img_rdy);
                finalHodle.iv_c.setImageResource(R.drawable.img_rdw);
                finalHodle.iv_d.setImageResource(R.drawable.img_rdw);
                list.get(i).setCheck("B");
            }
        });
        hodle.ll_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalHodle.iv_a.setImageResource(R.drawable.img_rdw);
                finalHodle.iv_b.setImageResource(R.drawable.img_rdw);
                finalHodle.iv_c.setImageResource(R.drawable.img_rdy);
                finalHodle.iv_d.setImageResource(R.drawable.img_rdw);
                list.get(i).setCheck("C");
            }
        });
        hodle.ll_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalHodle.iv_a.setImageResource(R.drawable.img_rdw);
                finalHodle.iv_b.setImageResource(R.drawable.img_rdw);
                finalHodle.iv_c.setImageResource(R.drawable.img_rdw);
                finalHodle.iv_d.setImageResource(R.drawable.img_rdy);
                list.get(i).setCheck("D");
            }
        });
        return view;
    }
    class ViewHodle{
        private TextView tv_a,tv_b,tv_c,tv_d,tv_tm;
        private ImageView iv_a,iv_b,iv_c,iv_d,iv;
        private LinearLayout ll_a,ll_b,ll_c,ll_d;
    }
}
