package lcsd.com.whirlpool.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.BdksAdapter;
import lcsd.com.whirlpool.adapter.BdksAdapter1;
import lcsd.com.whirlpool.entity.Kaoshi;
import lcsd.com.whirlpool.entity.Kaoshi1;
import lcsd.com.whirlpool.entity.UserInfo;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.http.AppContext;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.util.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15.
 */
public class View5 extends FrameLayout implements View.OnClickListener {
    private static View5 view5 = null;
    private Context context;
    private LinearLayout ll;
    private ScrollViewWithListView lv;
    private BdksAdapter adapter;
    private TextView tv_tj, tv_jf;
    private ImageView iv;
    // true为提交 ，fasle为重做;
    private boolean IsSubmit = true;
    private List<Kaoshi> list2;
    private List<Kaoshi> list1;
    private ScrollView sc;
    private List<Kaoshi1> list;
    private boolean is_test;
    private String id,point;

    public static View5 getInstance(Context context, boolean is_test, List<Kaoshi1> list, String id,String point) {
        if (view5 == null)
            return new View5(context, is_test, list, id,point);
        return view5;
    }

    public View5(Context context, boolean is_test, List<Kaoshi1> list, String id,String point) {
        super(context);
        this.context = context;
        this.is_test = is_test;
        this.list = list;
        this.id = id;
        this.point=point;
        LayoutInflater.from(context).inflate(R.layout.view5, this);
        initView();
        initData();
    }

    private void initView() {
        lv = (ScrollViewWithListView) findViewById(R.id.view5_lv1);
        tv_tj = (TextView) findViewById(R.id.view5_tv);
        tv_tj.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.view5_iv);
        ll = (LinearLayout) findViewById(R.id.view5_llall);
        tv_jf = (TextView) findViewById(R.id.view5_tvjf);
        tv_jf.setText("积 "+point+" 分");
        sc = (ScrollView) findViewById(R.id.view5_sc);
    }

    private void initData() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        adapter = new BdksAdapter(list1, context);
        lv.setAdapter(adapter);
            //已做
           /* lv.setVisibility(GONE);
            tv_tj.setVisibility(GONE);
            iv.setVisibility(VISIBLE);
            iv.setImageResource(R.drawable.img_ddl);
            tv_jf.setVisibility(VISIBLE);*/

            if (list != null&&list.size()>0) {
                tv_tj.setVisibility(VISIBLE);
                for (int i = 0; i < list.size(); i++) {
                    Kaoshi kaoshi = new Kaoshi();
                    kaoshi.setImg(list.get(i).getThumb());
                    kaoshi.setTimu(i + 1 + ". " + list.get(i).getTitle());
                    kaoshi.setCorrect(list.get(i).getKeys());
                    try {
                        kaoshi.setA(list.get(i).getOption().get(0).getCnt());
                    } catch (Exception e) {
                        kaoshi.setA("   ");
                    }
                    try {
                        kaoshi.setB(list.get(i).getOption().get(1).getCnt());
                    } catch (Exception e) {
                        kaoshi.setB("   ");
                    }
                    try {
                        kaoshi.setC(list.get(i).getOption().get(2).getCnt());
                    } catch (Exception e) {
                        kaoshi.setC("   ");
                    }
                    try {
                        kaoshi.setD(list.get(i).getOption().get(3).getCnt());
                    } catch (Exception e) {
                        kaoshi.setD("   ");
                    }
                    list1.add(kaoshi);
                }
                adapter.notifyDataSetChanged();
            }else {
                tv_tj.setVisibility(GONE);
            }
    }

    @Override
    public void onClick(View view) {
        if (IsSubmit) {
            if (list2.size() > 0) {
                list2.clear();
            }
            for (int i = 0; i < list1.size(); i++) {
                if (list1.get(i).getCheck() == null || (!list1.get(i).getCheck().equals(list1.get(i).getCorrect()))) {
                    list2.add(list1.get(i));
                }
            }
            if (list2.size() > 0) {
                iv.setVisibility(VISIBLE);
                iv.setImageResource(R.drawable.img_dcl);
                tv_tj.setText("重 做");
                IsSubmit = false;
                BdksAdapter1 adapter1 = new BdksAdapter1(list2, context);
                lv.setAdapter(adapter1);
                ll.setBackgroundResource(R.color.gray4);
                sc.fullScroll(View.FOCUS_UP);
            } else {
                if(is_test){
                    //已做显示答对，但是不积分
                    lv.setVisibility(GONE);
                    tv_tj.setVisibility(GONE);
                    iv.setVisibility(VISIBLE);
                    iv.setImageResource(R.drawable.img_ddl);
                }else {
                    request_tijiao();
                }
            }
        } else {
            iv.setVisibility(GONE);
            IsSubmit = true;
            tv_tj.setText("提 交");
            //将list1数据重置（选中item重置）
            for (int i = 0; i < list1.size(); i++) {
                list1.get(i).setCheck(null);
            }
            ll.setBackgroundResource(R.color.gray3);
            BdksAdapter bdksAdapter = new BdksAdapter(list1, context);
            lv.setAdapter(bdksAdapter);
            sc.fullScroll(View.FOCUS_UP);
        }
    }

    private void request_tijiao() {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "usercp");
        map.put("f", "test");
        map.put("id", id);
        ApiClient.requestNetHandle(context, AppConfig.request_tijiao, "提交中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.d("提交成功-----------", json);
                    try {
                        JSONObject object = new JSONObject(json);
                        int status = object.getInt("status");
                        String info = object.getString("info");
                        if (status == 1) {
                            lv.setVisibility(GONE);
                            tv_tj.setVisibility(GONE);
                            iv.setVisibility(VISIBLE);
                            iv.setImageResource(R.drawable.img_ddl);
                            tv_jf.setText("积 "+point+" 分");
                            tv_jf.setVisibility(VISIBLE);
                            updatexinxi();
                        } else {
                            Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
    private void updatexinxi(){
        ApiClient.requestNetHandle(context, AppConfig.Sy + "?c=usercp", "", null, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                L.d("个人信息--------", json);
                UserInfo userInfo= JSON.parseObject(json,UserInfo.class);
                if(userInfo!=null){
                    if(AppContext.getInstance().checkUser()){
                        AppContext.getInstance().cleanUserInfo();
                    }
                    AppContext.getInstance().saveUserInfo(userInfo);
                }
            }

            @Override
            public void onFailure(String msg) {
                L.d("个人信息异常：---",msg);
            }
        });
    }
}
