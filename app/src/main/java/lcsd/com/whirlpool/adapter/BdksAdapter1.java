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
import lcsd.com.whirlpool.entity.Kaoshi;
import lcsd.com.whirlpool.http.AppConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public class BdksAdapter1 extends BaseAdapter {
    private List<Kaoshi> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public BdksAdapter1(List<Kaoshi> list, Context context) {
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
        ViewHodle hodle =  new ViewHodle();
        //if (view == null) {
            view = layoutInflater.inflate(R.layout.item_ks, null);
            hodle.tv_tm = (TextView) view.findViewById(R.id.item_kstm);
            hodle.tv_a = (TextView) view.findViewById(R.id.item_tv_A);
            hodle.tv_b = (TextView) view.findViewById(R.id.item_tv_B);
            hodle.tv_c = (TextView) view.findViewById(R.id.item_tv_C);
            hodle.tv_d = (TextView) view.findViewById(R.id.item_tv_D);
            hodle.iv_a = (ImageView) view.findViewById(R.id.item_iv_A);
            hodle.iv_b = (ImageView) view.findViewById(R.id.item_iv_B);
            hodle.iv_c = (ImageView) view.findViewById(R.id.item_iv_C);
            hodle.iv_d = (ImageView) view.findViewById(R.id.item_iv_D);
            hodle.iv= (ImageView) view.findViewById(R.id.item_kstp);
           // view.setTag(hodle);
       // } else {
        //    hodle = (ViewHodle) view.getTag();
        //}
        if(list.get(i).getImg()!=null){
            hodle.iv.setVisibility(View.VISIBLE);
            Glide.with(context).load(AppConfig.mainurl+list.get(i).getImg()).into(hodle.iv);
        }else {
            hodle.iv.setVisibility(View.GONE);
        }
        hodle.tv_tm.setText(list.get(i).getTimu());
        hodle.tv_a.setText("A：" + list.get(i).getA());
        hodle.tv_b.setText("B：" + list.get(i).getB());
        hodle.tv_c.setText("C：" + list.get(i).getC());
        hodle.tv_d.setText("D：" + list.get(i).getD());
        if(list.get(i).getCheck()!=null){
            switch (list.get(i).getCheck()){
                case "A":
                    hodle.iv_a.setImageResource(R.drawable.img_rdr);
                    break;
                case "B":
                    hodle.iv_b.setImageResource(R.drawable.img_rdr);
                    break;
                case "C":
                    hodle.iv_c.setImageResource(R.drawable.img_rdr);
                    break;
                case "D":
                    hodle.iv_d.setImageResource(R.drawable.img_rdr);
                    break;
            }
        }
        switch (list.get(i).getCorrect()){
            case "A":
                hodle.iv_a.setImageResource(R.drawable.img_rdb);
                break;
            case "B":
                hodle.iv_b.setImageResource(R.drawable.img_rdb);
                break;
            case "C":
                hodle.iv_c.setImageResource(R.drawable.img_rdb);
                break;
            case "D":
                hodle.iv_d.setImageResource(R.drawable.img_rdb);
                break;
        }
        return view;
    }

    class ViewHodle {
        private TextView tv_a, tv_b, tv_c, tv_d, tv_tm;
        private ImageView iv_a, iv_b, iv_c, iv_d,iv;
    }
}
