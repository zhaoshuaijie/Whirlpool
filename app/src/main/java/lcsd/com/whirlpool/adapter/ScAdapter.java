package lcsd.com.whirlpool.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.json.JSONException;
import org.json.JSONObject;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.BaodianContentActivity;
import lcsd.com.whirlpool.activity.ZixunContentActivity;
import lcsd.com.whirlpool.entity.ShouCang;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/17.
 */
public class ScAdapter extends BaseAdapter {
    private List<ShouCang.TRslist> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private String title;

    public ScAdapter(Context context, List<ShouCang.TRslist> list, String title) {
        this.context = context;
        this.list = list;
        this.title = title;
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
        MyViewHodle viewHodle = null;
        if (view == null) {
            viewHodle = new MyViewHodle();
            view = layoutInflater.inflate(R.layout.item_sc, null);
            viewHodle.tv = (TextView) view.findViewById(R.id.item_sc_title);
            viewHodle.iv = (ImageView) view.findViewById(R.id.item_sc_iv);
            viewHodle.ll_sc = (LinearLayout) view.findViewById(R.id.item_ll_sc);
            viewHodle.tv_qx = (TextView) view.findViewById(R.id.tv_qx);
            viewHodle.swipeMenuLayout= (SwipeMenuLayout) view.findViewById(R.id.sc_swipe);
            view.setTag(viewHodle);
        } else {
            viewHodle = (MyViewHodle) view.getTag();
        }
        viewHodle.tv.setText(list.get(i).getTitle());
        Glide.with(context).load(AppConfig.mainurl + list.get(i).getThumb()).into(viewHodle.iv);
        final MyViewHodle finalViewHodle = viewHodle;
        viewHodle.tv_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request_qxsc(list.get(i).getLid(),i);
                finalViewHodle.swipeMenuLayout.quickClose();
            }
        });
        viewHodle.ll_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.equals("产品宝典")) {
                    context.startActivity(new Intent(context, BaodianContentActivity.class).putExtra("id", list.get(i).getLid()).putExtra("title", list.get(i).getTitle()));
                }  else {
                    context.startActivity(new Intent(context, ZixunContentActivity.class).putExtra("id", list.get(i).getLid()).putExtra("title", list.get(i).getTitle()).putExtra("url", AppConfig.Sy + "?id=" + list.get(i).getLid()).putExtra("img", list.get(i).getThumb()));
                }
            }
        });
        return view;
    }

    class MyViewHodle {
        private TextView tv, tv_qx;
        private ImageView iv;
        private LinearLayout ll_sc;
        private SwipeMenuLayout swipeMenuLayout;
    }
    private void request_qxsc(final String id, final int i) {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "fav");
        map.put("f", "cancel");
        map.put("id", id);
        ApiClient.requestNetHandle(context, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        JSONObject object = new JSONObject(json);
                        int status = object.getInt("status");
                        String info = object.getString("info");
                        if (status == 1) {
                            list.remove(i);
                            notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "系统异常，请稍后重试。", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
