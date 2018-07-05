package lcsd.com.whirlpool.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.Kaoshi1;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.listener.SmallBangListener;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.sql.Constant;
import lcsd.com.whirlpool.sql.DbManager;
import lcsd.com.whirlpool.sql.MySqliteHelper;
import lcsd.com.whirlpool.util.L;
import lcsd.com.whirlpool.util.StringUtils;
import lcsd.com.whirlpool.view.SmallBang;
import lcsd.com.whirlpool.view.View1;
import lcsd.com.whirlpool.view.View2;
import lcsd.com.whirlpool.view.View3;
import lcsd.com.whirlpool.view.View4;
import lcsd.com.whirlpool.view.View5;
import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

import com.mingle.widget.ShapeLoadingDialog;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaodianContentActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title, tv1, tv2, tv3, tv4, tv5/*, tv_xh, tv_jj*/;
    private ImageView /*iv,*/iv_sc;
    private ViewPager vp;
    private View view1, view2, view3, view4, view5;
    private ViewPagerAdapter viewPagerAdapter;
    private List<View> viewList = new ArrayList<>();// view数组
    private String title, id, point, hit;
    private String param, video, content, thumb;
    private List<String> pictures;
    private ShapeLoadingDialog loadingDialog;
    private boolean is_test;
    private List<Kaoshi1> list;
    private SmallBang mSmallBang;
    private boolean istrue = false;
    private MySqliteHelper helper;
    private PromptDialog promptDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baodian_content);
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            id = getIntent().getStringExtra("id");
        }
        helper = DbManager.getIntance(this);
        //创建对象
        promptDialog = new PromptDialog(this);
        loadingDialog = new ShapeLoadingDialog.Builder(this)
                .loadText("加载中...")
                .build();
        loadingDialog.show();
        request_issc();
        initView();
        requestData();
    }


    private void initView() {
        //tv_xh = (TextView) findViewById(R.id.bd_tvxh);
        // tv_jj = (TextView) findViewById(R.id.bd_tvjj);
        //iv = (ImageView) findViewById(R.id.bd_iv);
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        tv_title.setText(title);
        iv_sc = (ImageView) findViewById(R.id.titlebar_Collection);
        iv_sc.setVisibility(View.VISIBLE);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        pictures = new ArrayList<>();
        tv1 = (TextView) findViewById(R.id.vp_tv1);
        tv2 = (TextView) findViewById(R.id.vp_tv2);
        tv3 = (TextView) findViewById(R.id.vp_tv3);
        tv4 = (TextView) findViewById(R.id.vp_tv4);
        tv5 = (TextView) findViewById(R.id.vp_tv5);
        vp = (ViewPager) findViewById(R.id.baodian_vp);
        mSmallBang = SmallBang.attach2Window(this);
    }

    private void initData() {
        tv1.setOnClickListener(onClickListener);
        tv2.setOnClickListener(onClickListener);
        tv3.setOnClickListener(onClickListener);
        tv4.setOnClickListener(onClickListener);
        tv5.setOnClickListener(onClickListener);
        view1 = View1.getInstance(this, pictures);
        view2 = View2.getInstance(this, param);
        view3 = View3.getInstance(this, content);
        view4 = View4.getInstance(this, video, id, hit);
        view5 = View5.getInstance(this, is_test, list, id, point);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);
        viewPagerAdapter = new ViewPagerAdapter(viewList);
        vp.setAdapter(viewPagerAdapter);
        vp.addOnPageChangeListener(onPageChangeListener);
        vp.setCurrentItem(0);
        initSql();
        loadingDialog.dismiss();
    }

    //添加到数据库
    private void initSql() {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.ID, Integer.parseInt(id));
        values.put(Constant.IMG, thumb);
        values.put(Constant.TITLE, title);
        values.put(Constant.TYPE, "产品宝典");
        values.put(Constant.TIME, StringUtils.NowTime());
        long result = db.insert(Constant.TABLE_NAME, null, values);
        if (result > 0) {
            L.d("TAG", "----------添加成功-----------");
        } else {
            L.d("TAG", "----------添加失败-----------");
            SQLiteDatabase sqldb = helper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constant.TIME, StringUtils.NowTime());
            int count = sqldb.update(Constant.TABLE_NAME, contentValues, Constant.ID + "=" + Integer.parseInt(id), null);
            if (count > 0) {
                L.d("TAG", "----------修改成功-----------");
            } else {
                L.d("TAG", "----------修改失败-----------");
            }
            sqldb.close();
        }
        db.close();
    }

    private void requestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        JSONObject object = new JSONObject(json);
                        //BdConten bdConten = JSON.parseObject(response, BdConten.class);
                        param = object.getString("param");
                        video = object.getString("video");
                        content = object.getString("content");
                        JSONArray arr = object.getJSONArray("pictures");
                        if (arr != null) {
                            for (int i = 0; i < arr.length(); i++) {
                                pictures.add(arr.getString(i));
                            }
                        }
                        /*tv_xh.setText("型号：" + object.getString("type"));
                        tv_jj.setText(object.getString("note"));
                        thumb=object.getString("thumb");
                        Glide.with(BaodianContentActivity.this).load(AppConfig.mainurl +object.getString("thumb")).into(iv);*/
                        hit = "浏览：" + object.getString("hits");
                        is_test = object.getBoolean("is_test");
                        L.d("是否考过试-----" + is_test + "");
                        point = object.getString("point");
                        list = JSON.parseArray(object.getString("test"), Kaoshi1.class);
                        initData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        loadingDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(BaodianContentActivity.this, msg, Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_back:
                if (((View4) view4).getjindu() < 100) {
                    showtuichuDialog(0);
                }
                break;
            case R.id.titlebar_home:
                if (((View4) view4).getjindu() < 100) {
                    showtuichuDialog(1);
                }
                break;
            case R.id.titlebar_Collection:
                if (!istrue) {
                    request_sc();
                } else {
                    request_qxsc();
                }
                break;
        }
    }

    private void request_sc() {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "fav");
        map.put("f", "add");
        map.put("id", id);
        ApiClient.requestNetHandle(this, AppConfig.Sy, "收藏中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        JSONObject object = new JSONObject(json);
                        int status = object.getInt("status");
                        String info = object.getString("info");
                        if (status == 1) {
                            like(iv_sc);
                        }
                        Toast.makeText(BaodianContentActivity.this, info, Toast.LENGTH_SHORT).show();
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

    private void request_qxsc() {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "fav");
        map.put("f", "cancel");
        map.put("id", id);
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        JSONObject object = new JSONObject(json);
                        int status = object.getInt("status");
                        String info = object.getString("info");
                        if (status == 1) {
                            iv_sc.setImageResource(R.drawable.img_collection);
                            istrue = false;
                        }
                        Toast.makeText(BaodianContentActivity.this, info, Toast.LENGTH_SHORT).show();
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

    public void like(View view) {
        iv_sc.setImageResource(R.drawable.img_collection_red);
        istrue = true;
        mSmallBang.bang(view);
        mSmallBang.setmListener(new SmallBangListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tv1.setBackgroundResource(R.color.yellow1);
            tv2.setBackgroundResource(R.color.yellow1);
            tv3.setBackgroundResource(R.color.yellow1);
            tv4.setBackgroundResource(R.color.yellow1);
            tv5.setBackgroundResource(R.color.yellow1);
            switch (view.getId()) {
                case R.id.vp_tv1:
                    tv1.setBackgroundResource(R.color.darkblue);
                    vp.setCurrentItem(0);
                    if (view4 != null) {
                        ((View4) view4).changevideo(1);
                    }
                    break;
                case R.id.vp_tv2:
                    tv2.setBackgroundResource(R.color.darkblue);
                    vp.setCurrentItem(1);
                    if (view4 != null) {
                        ((View4) view4).changevideo(1);
                    }
                    break;
                case R.id.vp_tv3:
                    tv3.setBackgroundResource(R.color.darkblue);
                    vp.setCurrentItem(2);
                    if (view4 != null) {
                        ((View4) view4).changevideo(1);
                    }
                    break;
                case R.id.vp_tv4:
                    tv4.setBackgroundResource(R.color.darkblue);
                    vp.setCurrentItem(3);
                    if (view4 != null) {
                        ((View4) view4).changevideo(2);
                    }
                    break;
                case R.id.vp_tv5:
                    tv5.setBackgroundResource(R.color.darkblue);
                    vp.setCurrentItem(4);
                    if (view4 != null) {
                        ((View4) view4).changevideo(1);
                    }
                    break;
            }
        }
    };
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            tv1.setBackgroundResource(R.color.yellow1);
            tv2.setBackgroundResource(R.color.yellow1);
            tv3.setBackgroundResource(R.color.yellow1);
            tv4.setBackgroundResource(R.color.yellow1);
            tv5.setBackgroundResource(R.color.yellow1);
            switch (position) {
                case 0:
                    tv1.setBackgroundResource(R.color.darkblue);
                    if (view4 != null) {
                        ((View4) view4).changevideo(1);
                    }
                    break;
                case 1:
                    tv2.setBackgroundResource(R.color.darkblue);
                    if (view4 != null) {
                        ((View4) view4).changevideo(1);
                    }
                    break;
                case 2:
                    tv3.setBackgroundResource(R.color.darkblue);
                    if (view4 != null) {
                        ((View4) view4).changevideo(1);
                    }
                    break;
                case 3:
                    tv4.setBackgroundResource(R.color.darkblue);
                    if (view4 != null) {
                        ((View4) view4).changevideo(2);
                    }
                    break;
                case 4:
                    tv5.setBackgroundResource(R.color.darkblue);
                    if (view4 != null) {
                        ((View4) view4).changevideo(1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * ViewPage适配器
     **/
    public class ViewPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public ViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(mListViews.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(mListViews.get(position));
            return mListViews.get(position);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void startUpdate(View container) {
            super.startUpdate(container);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (view4 != null) {
            ((View4) view4).changevideo(1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (view4 != null && vp.getCurrentItem() == 3) {
            ((View4) view4).changevideo(2);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (view4 != null) {
            ((View4) view4).changevideo(0);
        }
    }

    private void request_issc() {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "usercp");
        map.put("f", "is_fav");
        map.put("id", id);
        ApiClient.requestNetHandle(this, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        JSONObject object = new JSONObject(json);
                        int status = object.getInt("status");
                        String info = object.getString("info");
                        if (status == 1) {
                            iv_sc.setImageResource(R.drawable.img_collection_red);
                            istrue = true;
                        } else {
                            iv_sc.setImageResource(R.drawable.img_collection);
                            istrue = false;
                        }
                        iv_sc.setOnClickListener(BaodianContentActivity.this);
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

    @Override
    public void onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        if (((View4) view4).getjindu() < 100) {
            showtuichuDialog(0);
            return;
        }
        super.onBackPressed();
    }

    private void showtuichuDialog(final int type) {
        //按钮的定义，创建一个按钮的对象
        final PromptButton confirm = new PromptButton("确定", new PromptButtonListener() {
            @Override
            public void onClick(PromptButton button) {
                if (type == 0) {
                    finish();
                } else if (type == 1) {
                    ActivityManager.getActivityManager().finishAll();
                }
            }
        });
        confirm.setTextColor(getResources().getColor(R.color.yellow1));
        confirm.setFocusBacColor(getResources().getColor(R.color.gray1));
        confirm.setDelyClick(true);//点击后，是否再对话框消失后响应按钮的监听事件
        promptDialog.showWarnAlert("确定退出？（视频还未看完，未获得积分）",
                new PromptButton("取消", null), confirm);
    }
}
