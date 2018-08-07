package lcsd.com.whirlpool.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lcsd.com.whirlpool.R;
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

import com.mingle.widget.ShapeLoadingDialog;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 新闻详情页
 */
public class ZixunContentActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private String title, url, id, img;
    private WebView webview;
    private ImageView iv_sc;
    private SmallBang mSmallBang;
    private boolean istrue;//已经收藏为true，未收藏为false
    private ShapeLoadingDialog loadingDialog;
    private ArrayList<String> images = new ArrayList<>();
    private MySqliteHelper helper;
    private long mLasttime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixun_content);
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
            id = getIntent().getStringExtra("id");
            img = getIntent().getStringExtra("img");
        }
        loadingDialog = new ShapeLoadingDialog.Builder(this)
                .loadText("加载中...")
                .build();
        loadingDialog.show();
        helper = DbManager.getIntance(this);
        request_issc();
        initView();
        initData();
        initSql();
    }

    private void initView() {
        webview = (WebView) findViewById(R.id.webview);
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        iv_sc = (ImageView) findViewById(R.id.titlebar_Collection);
        tv_title.setText(title);
        iv_sc.setVisibility(View.VISIBLE);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        mSmallBang = SmallBang.attach2Window(this);
    }

    private void initData() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().getLoadWithOverviewMode();
        webview.getSettings().getJavaScriptCanOpenWindowsAutomatically();
        webview.getSettings().setDomStorageEnabled(true);
        webview.loadUrl(url);
        //载入js
        webview.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");

        webview.setWebViewClient(new WebViewClient() {
            // 网页跳转
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingDialog.dismiss();
                // html加载完成之后，添加监听图片的点击js函数
                addImageClickListner();
            }
        });
    }

    //添加到数据库
    private void initSql() {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.ID, Integer.parseInt(id));
        values.put(Constant.IMG, img);
        values.put(Constant.TITLE, title);
        values.put(Constant.TYPE, "资讯");
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_back:
                this.finish();
                break;
            case R.id.titlebar_Collection:
                if (System.currentTimeMillis() - mLasttime < 1000)//防止快速点击操作
                    return;
                mLasttime = System.currentTimeMillis();
                if (!istrue) {
                    request_sc();
                } else {
                    request_qxsc();
                }
                break;
            case R.id.titlebar_home:
                ActivityManager.getActivityManager().finishAll();
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
                        } else if (status == 2) {
                            ShowAginLoginDialog();
                        }
                        Toast.makeText(ZixunContentActivity.this, info, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ZixunContentActivity.this, "系统异常，请稍后重试。", Toast.LENGTH_SHORT).show();
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
                        } else if (status == 2) {
                            ShowAginLoginDialog();
                        }
                        Toast.makeText(ZixunContentActivity.this, info, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ZixunContentActivity.this, "系统异常，请稍后重试。", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 注入js函数监听
    private void addImageClickListner() {
        //遍历页面中所有img的节点，因为节点里面的图片的url即objs[i].src，保存所有图片的src.
        //为每个图片设置点击事件，objs[i].onclick
        webview.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{" +
                "window.imagelistner.readImageUrl(objs[i].src);  " +
                " objs[i].onclick=function()  " +
                " {  " +
                " window.imagelistner.openImage(this.src);  " +
                "  }  " +
                "}" +
                "})()");
    }

    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void readImageUrl(String img) {     //把所有图片的url保存在ArrayList<String>中
            images.add(img);
        }

        @android.webkit.JavascriptInterface  //对于targetSdkVersion>=17的，要加这个声明
        public void openImage(String clickimg)//点击图片所调用到的函数
        {
            int index = 0;
            ArrayList<String> list = addImages();
            for (String url : list)
                if (url.equals(clickimg)) index = list.indexOf(clickimg);//获取点击图片在整个页面图片中的位置
            String[] imgs = new String[list.size()];
            for (int i = 0; i < imgs.length; i++) {
                imgs[i] = list.get(i);
            }
            Intent intent = new Intent();
            intent.putExtra("imgs", imgs);
            intent.putExtra("index", index);
            intent.setClass(context, ImagePageActivity.class);
            context.startActivity(intent);//启动ViewPagerActivity,用于显示图片
        }
    }


    //去重复
    private ArrayList<String> addImages() {
        ArrayList<String> list = new ArrayList<>();
        Set set = new HashSet();
        for (String cd : images) {
            if (set.add(cd)) {
                list.add(cd);
            }
        }
        return list;
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
                        } else if (status == 2) {
                            ShowAginLoginDialog();
                        } else {
                            iv_sc.setImageResource(R.drawable.img_collection);
                            istrue = false;
                        }
                        iv_sc.setOnClickListener(ZixunContentActivity.this);
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
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
