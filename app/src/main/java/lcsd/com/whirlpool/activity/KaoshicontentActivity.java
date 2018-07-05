package lcsd.com.whirlpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.Kaoshi;
import lcsd.com.whirlpool.entity.SjKaoshi;
import lcsd.com.whirlpool.entity.UserInfo;
import lcsd.com.whirlpool.fragment.FragmentContent;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.http.AppContext;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.util.L;

public class KaoshicontentActivity extends BaseActivity implements View.OnClickListener {
    private String cate, type, id, point;
    private int psise = 10;
    private ArrayList<SjKaoshi.TTest> list;
    private ViewPager pager;
    public static ArrayList<Kaoshi> List;
    private ArrayList<Kaoshi> list_ct;
    private MyPagerAdapter adapter;
    private TextView tv_tj, tv_left, tv_right, tv_title, tv_jfct;
    private ImageView iv_dc;
    private RelativeLayout rl_dc;
    // true为提交 ，fasle为关闭;
    private boolean IsSubmit = true, isCt = false;
    private int num, fen;
    private ArrayList<String> mErridlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaoshicontent);

        if (getIntent() != null) {
            cate = getIntent().getStringExtra("cate");
            id = getIntent().getStringExtra("id");
            fen = getIntent().getIntExtra("fen", 0);
            if (getIntent().getStringExtra("type").equals("rand")) {
                type = "rand";
            } else {
                type = null;
            }
        }
        initView();
        initData();
        requestSt();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        tv_title.setText("考试中心");
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        pager = (ViewPager) findViewById(R.id.viewpage);
        tv_tj = (TextView) findViewById(R.id.tv_tj);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_tj.setVisibility(View.INVISIBLE);
        iv_dc = (ImageView) findViewById(R.id.iv_dc);
        rl_dc = (RelativeLayout) findViewById(R.id.rl_dc);
        tv_jfct = (TextView) findViewById(R.id.tv_jfct);
        tv_jfct.setOnClickListener(this);
    }

    private void initData() {
        mErridlist = new ArrayList<>();
        tv_tj.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        list = new ArrayList<>();
        List = new ArrayList<>();
        list_ct = new ArrayList<>();
        adapter = new MyPagerAdapter(this.getSupportFragmentManager(), List);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (!isCt) {
                            if (pager.getCurrentItem() > 1 && pager.getCurrentItem() == pager.getAdapter()
                                    .getCount() - 1) {
                                tv_tj.setVisibility(View.VISIBLE);
                                //tv_right.setVisibility(View.INVISIBLE);
                            } else {
                                tv_tj.setVisibility(View.INVISIBLE);
                                //tv_right.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                }
            }
        });
    }

    private void requestSt() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "exam");
        map.put("cate", cate);
        if (type != null) {
            //随机测试需上传的
            map.put("type", type);
            map.put("psize", psise + "");
        }
        ApiClient.requestNetHandle(this, AppConfig.Sy, "加载中", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    SjKaoshi sjKaoshi = JSON.parseObject(json, SjKaoshi.class);
                    if (sjKaoshi.getTest() != null && sjKaoshi.getTest().size() > 0) {
                        list.addAll(sjKaoshi.getTest());
                    }
                    point = sjKaoshi.getPoint();
                    initShiti();
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(KaoshicontentActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //初始化试题数据
    private void initShiti() {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Kaoshi kaoshi = new Kaoshi();

                kaoshi.setId(list.get(i).getId());

                kaoshi.setImg(list.get(i).getThumb());

                kaoshi.setTimu(i + 1 + ". " + list.get(i).getTitle() /*+ list.get(i).getKeys()得到正确答案进行测试*/);
                kaoshi.setCorrect(list.get(i).getKeys());
                if (list.get(i).getKeys().length() > 1) {
                    kaoshi.setType(2);
                } else {
                    kaoshi.setType(1);
                }
                try {
                    kaoshi.setA("A：" + list.get(i).getOption().get(0).getTitle());
                } catch (Exception e) {
                    kaoshi.setA("   ");
                }
                try {
                    kaoshi.setB("B：" + list.get(i).getOption().get(1).getTitle());
                } catch (Exception e) {
                    kaoshi.setB("   ");
                }
                try {
                    kaoshi.setC("C：" + list.get(i).getOption().get(2).getTitle());
                } catch (Exception e) {
                    kaoshi.setC("   ");
                }
                try {
                    kaoshi.setD("D：" + list.get(i).getOption().get(3).getTitle());
                } catch (Exception e) {
                    kaoshi.setD("   ");
                }
                if (list.get(i).getOption().size() > 4) {
                    try {
                        kaoshi.setE("E：" + list.get(i).getOption().get(4).getTitle());
                    } catch (Exception e) {
                        kaoshi.setE("   ");
                    }
                }
                if (list.get(i).getOption().size() > 5) {
                    try {
                        kaoshi.setF("F：" + list.get(i).getOption().get(5).getTitle());
                    } catch (Exception e) {
                        kaoshi.setF("   ");
                    }
                }
                List.add(kaoshi);
            }
            adapter.notifyDataSetChanged();
        } else {
            tv_tj.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_back:
                this.finish();
                break;
            case R.id.titlebar_home:
                ActivityManager.getActivityManager().finishAll();
                break;
            case R.id.tv_tj:
                if (IsSubmit) {
                    if (list_ct.size() > 0) {
                        list_ct.clear();
                    }
                    for (int i = 0; i < List.size(); i++) {
                        if (List.get(i).getCheck() == null || (!List.get(i).getCheck().equals(List.get(i).getCorrect()))) {
                            list_ct.add(List.get(i));
                        }
                    }
                    if (mErridlist != null) {
                        mErridlist.clear();
                    }
                    for (int i = 0; i < list_ct.size(); i++) {
                        mErridlist.add(list_ct.get(i).getId());
                    }
                    if (list_ct.size() > 0) {
                        num = List.size() - list_ct.size();
                        if (fen != 0) {
                            if ((num * 5) >= fen) {
                                pager.setVisibility(View.GONE);
                                rl_dc.setVisibility(View.VISIBLE);
                                iv_dc.setImageResource(R.drawable.img_ddl);
                                tv_left.setVisibility(View.INVISIBLE);
                                tv_right.setVisibility(View.INVISIBLE);
                                tv_tj.setText("关 闭");
                                tv_jfct.setText("恭喜您通过考核！\n共" + List.size() + "题， 您答错" + list_ct.size() + "题\n查看答题");
                            } else {
                                pager.setVisibility(View.GONE);
                                rl_dc.setVisibility(View.VISIBLE);
                                iv_dc.setImageResource(R.drawable.img_dcl);
                                tv_left.setVisibility(View.INVISIBLE);
                                tv_right.setVisibility(View.INVISIBLE);
                                tv_tj.setText("关 闭");
                                tv_jfct.setText("很遗憾您未通过考核！\n共" + List.size() + "题， 您答错" + list_ct.size() + "题\n查看答题");
                            }
                        } else {
                            pager.setVisibility(View.GONE);
                            rl_dc.setVisibility(View.VISIBLE);
                            iv_dc.setImageResource(R.drawable.img_dcl);
                            tv_left.setVisibility(View.INVISIBLE);
                            tv_right.setVisibility(View.INVISIBLE);
                            tv_tj.setText("关 闭");
                            tv_jfct.setText("共" + List.size() + "题， 您答错" + list_ct.size() + "题\n查看答题");
                        }
                        isCt = true;
                        IsSubmit = false;
                        if (type == null) {
                            //答错提交
                            request_tjcw();
                        }
                    } else {
                        num = List.size();
                        //全部答对
                        request_tijiao();
                    }
                } else {
                    this.finish();
                }
                break;
            case R.id.tv_left:
                pager.arrowScroll(1);
                break;
            case R.id.tv_right:
                pager.arrowScroll(2);
                break;
            case R.id.tv_jfct:
                if (isCt) {
                    startActivity(new Intent(KaoshicontentActivity.this, MyErrorListActivity.class).putExtra("errorlist", list_ct));
                }
                break;
        }
    }

    //专项测试打错提交
    private void request_tjcw() {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "usercp");
        map.put("f", "test");
        //专项测试
        map.put("id", id);
        map.put("type", "onetest");
        map.put("num", num);
        for (int i = 0; i < mErridlist.size(); i++) {
            map.put("errid" + "[" + i + "]", mErridlist.get(i));
        }
        Log.e("提交", "id=" + id + " type=onetest" + " num=" + num + " errs" + mErridlist);
        ApiClient.requestNetHandle(KaoshicontentActivity.this, AppConfig.request_tijiao, "提交中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        L.e("专项答错提交", json);
                        JSONObject object = new JSONObject(json);
                        int status = object.getInt("status");
                        String info = object.getString("info");
                        //后续处理
                        Toast.makeText(KaoshicontentActivity.this, info, Toast.LENGTH_SHORT).show();
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

    private void request_tijiao() {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "usercp");
        map.put("f", "test");
        if (type != null) {
            //随机测试
            map.put("type", "randtest");
            map.put("num", 10 + "");
        } else {
            //专项测试
            map.put("id", id);
            map.put("type", "onetest");
            map.put("num", num);
            map.put("errid", "");
        }
        Log.e("提交", "id+" + id + " type=onetest" + " num=" + num + " errs=0");
        ApiClient.requestNetHandle(KaoshicontentActivity.this, AppConfig.request_tijiao, "提交中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.e("提交成功-----------", json);
                    try {
                        JSONObject object = new JSONObject(json);
                        int status = object.getInt("status");
                        String info = object.getString("info");
                        if (status == 1) {
                            pager.setVisibility(View.GONE);
                            rl_dc.setVisibility(View.VISIBLE);
                            iv_dc.setImageResource(R.drawable.img_ddl);
                            tv_left.setVisibility(View.INVISIBLE);
                            tv_right.setVisibility(View.INVISIBLE);
                            tv_tj.setText("关 闭");
                            if (type != null) {
                                tv_jfct.setText("全部答对，积 " + (int) ((Double.parseDouble(point)) * 10) + " 分！");
                            } else {
                                if (fen != 0) {
                                    tv_jfct.setText("恭喜您全部答对，通过考核！");
                                } else {
                                    tv_jfct.setText("全部答对，积 " + point + " 分！");
                                }
                            }
                            isCt = false;
                            IsSubmit = false;
                            updatexinxi();
                        } else {
                            Toast.makeText(KaoshicontentActivity.this, info, Toast.LENGTH_SHORT).show();
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

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Kaoshi> list;

        public MyPagerAdapter(FragmentManager fm, List<Kaoshi> list) {
            super(fm);
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("item", position);
            return FragmentContent.getInstance(bundle);
        }

    }

    //更新积分信息
    private void updatexinxi() {
        ApiClient.requestNetHandle(KaoshicontentActivity.this, AppConfig.Sy + "?c=usercp", "", null, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                // L.d("个人信息--------", json);
                UserInfo userInfo = JSON.parseObject(json, UserInfo.class);
                if (userInfo != null) {
                    if (AppContext.getInstance().checkUser()) {
                        AppContext.getInstance().cleanUserInfo();
                    }
                    AppContext.getInstance().saveUserInfo(userInfo);
                }
            }

            @Override
            public void onFailure(String msg) {
                L.d("个人信息异常：---", msg);
            }
        });
    }

    //不可滑动返回
    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
