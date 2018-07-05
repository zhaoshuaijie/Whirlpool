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

import java.util.ArrayList;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.Kaoshi;
import lcsd.com.whirlpool.http.AppConfig;

/**
 * Created by jie on 2018/6/21.
 */
public class MyErrorAdapter extends BaseAdapter {
    private ArrayList<Kaoshi> mList;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public MyErrorAdapter(ArrayList<Kaoshi> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodle viewHodle = null;
        if (view == null) {
            viewHodle = new ViewHodle();
            view = layoutInflater.inflate(R.layout.item_my_error, null);
            viewHodle.tv_timu = view.findViewById(R.id.item_timu);
            viewHodle.tv_daan = view.findViewById(R.id.item_daan);
            viewHodle.iv_tm = view.findViewById(R.id.item_iv_timu);
            viewHodle.tv1 = view.findViewById(R.id.item_tv1);
            viewHodle.tv2 = view.findViewById(R.id.item_tv2);
            viewHodle.tv3 = view.findViewById(R.id.item_tv3);
            viewHodle.tv4 = view.findViewById(R.id.item_tv4);
            viewHodle.tv5 = view.findViewById(R.id.item_tv5);
            viewHodle.tv6 = view.findViewById(R.id.item_tv6);
            view.setTag(viewHodle);
        } else {
            viewHodle = (ViewHodle) view.getTag();
        }
        viewHodle.tv_timu.setText(mList.get(i).getTimu());
        if (mList.get(i).getImg() != null && mList.get(i).getImg().length() > 1) {
            viewHodle.iv_tm.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(AppConfig.mainurl + mList.get(i).getImg()).into(viewHodle.iv_tm);
        } else {
            viewHodle.iv_tm.setVisibility(View.GONE);
        }
        if (mList.get(i).getCheck() != null && mList.get(i).getCheck().length() > 0) {
            viewHodle.tv_daan.setText("正确答案：" + mList.get(i).getCorrect() + "，您选择的是：" + mList.get(i).getCheck() + "！");
        } else {
            viewHodle.tv_daan.setText("正确答案：" + mList.get(i).getCorrect() + "，您未作答！");
        }
        viewHodle.tv1.setText(mList.get(i).getA());
        viewHodle.tv2.setText(mList.get(i).getB());
        viewHodle.tv3.setText(mList.get(i).getC());
        viewHodle.tv4.setText(mList.get(i).getD());
        if (mList.get(i).getE() != null) {
            viewHodle.tv5.setVisibility(View.VISIBLE);
            viewHodle.tv5.setText(mList.get(i).getE());
        } else {
            viewHodle.tv5.setVisibility(View.GONE);
        }
        if (mList.get(i) != null) {
            viewHodle.tv6.setVisibility(View.VISIBLE);
            viewHodle.tv6.setText(mList.get(i).getF());
        } else {
            viewHodle.tv6.setVisibility(View.GONE);
        }

        return view;
    }

    class ViewHodle {
        TextView tv_timu, tv_daan, tv1, tv2, tv3, tv4, tv5, tv6;
        ImageView iv_tm;
    }
}
