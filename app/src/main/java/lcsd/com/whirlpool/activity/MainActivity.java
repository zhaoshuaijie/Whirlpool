package lcsd.com.whirlpool.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.http.AppContext;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.manager.UpdateManager;
import lcsd.com.whirlpool.view.CircleImageView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ImageView iv_head;
    private CircleImageView iv_lefthead;
    private Fragment[] mFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private PtrClassicFrameLayout mPtrFrame1, mPtrFrame2, mPtrFrame3, mPtrFrame_main;
    private LinearLayout ll_menu1, ll_menu2,
            ll_menu3, ll_menu4, ll_menu5, ll_menu6, ll_menu7,
            ll_leftmain;
    private ScrollView sv_main;
    private TextView tv_nc, tv_gx, tv_jf, tv_tx, tv_bb;
    private EditText et_ss;
    private String versionname;
    public static MainActivity mMainActivity;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSystemBarTransparent();

        mMainActivity = this;
        new UpdateManager(this);
        try {
            versionname = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getIntent().getStringExtra("tuisong") != null && getIntent().getStringExtra("tuisong").equals("2")) {
            //极光推送
            if (getIntent().getStringExtra("type") != null) {
                if (getIntent().getStringExtra("type").equals("1")) {
                    startActivity(new Intent(this, ZixunContentActivity.class).putExtra("id", getIntent().getStringExtra("id")).putExtra("title", getIntent().getStringExtra("title")).putExtra("url", AppConfig.Sy + "?id=" + getIntent().getStringExtra("id")).putExtra("img", ""));
                } else if (getIntent().getStringExtra("type").equals("2")) {
                    startActivity(new Intent(this, BaodianContentActivity.class).putExtra("id", getIntent().getStringExtra("id")).putExtra("title", getIntent().getStringExtra("title")));
                } else if (getIntent().getStringExtra("type").equals("3")) {
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", getIntent().getStringExtra("id")).putExtra("title", getIntent().getStringExtra("title")));
                }
            }
        }
        mContext = this;
        initData();
        initView();
    }

    private void initView() {

        ll_leftmain = (LinearLayout) findViewById(R.id.ll_leftmain);
        ll_leftmain.setOnClickListener(null);
        iv_lefthead = (CircleImageView) findViewById(R.id.iv_lefthead);
        iv_lefthead.setOnClickListener(onClickListener);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        iv_head.setOnClickListener(this);

        tv_bb = (TextView) findViewById(R.id.main_bb);
        if (versionname != null && versionname.toString().length() > 1) {
            tv_bb.setText("当前版本：" + versionname);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        ll_menu1 = (LinearLayout) findViewById(R.id.nav_zhuye);
        ll_menu2 = (LinearLayout) findViewById(R.id.nav_xiaoxi);
        ll_menu3 = (LinearLayout) findViewById(R.id.nav_zuji);
        ll_menu4 = (LinearLayout) findViewById(R.id.nav_shouchang);
        ll_menu5 = (LinearLayout) findViewById(R.id.nav_jifen);
        ll_menu6 = (LinearLayout) findViewById(R.id.nav_set);
        ll_menu7 = (LinearLayout) findViewById(R.id.nav_proposal);
        ll_menu1.setOnClickListener(onClickListener);
        ll_menu2.setOnClickListener(onClickListener);
        ll_menu3.setOnClickListener(onClickListener);
        ll_menu4.setOnClickListener(onClickListener);
        ll_menu5.setOnClickListener(onClickListener);
        ll_menu6.setOnClickListener(onClickListener);
        ll_menu7.setOnClickListener(onClickListener);
        tv_gx = (TextView) findViewById(R.id.tv_gx);
        tv_nc = (TextView) findViewById(R.id.tv_nc);
        tv_jf = (TextView) findViewById(R.id.tv_jf);
        tv_tx = (TextView) findViewById(R.id.tv_tx);
        et_ss = (EditText) findViewById(R.id.sousuo);
        et_ss.setOnClickListener(this);
        et_ss.setFocusable(false);

        if (AppContext.getInstance().checkUser()) {
            if (AppContext.getInstance().getUserInfo().getAvatar().length() < 1) {
                iv_lefthead.setImageResource(R.drawable.img_head);
            } else {
                RequestOptions options = new RequestOptions();
                options.dontAnimate();
                options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                options.fitCenter();
                Glide.with(this).load(AppConfig.mainurl + AppContext.getInstance().getUserInfo().getAvatar()).apply(options).into(iv_lefthead);
            }
            tv_gx.setText("个性签名：" + AppContext.getInstance().getUserInfo().getSpecial());
            tv_nc.setText("昵称：" + AppContext.getInstance().getUserInfo().getNickname());
            tv_jf.setText("积分：" + AppContext.getInstance().getUserInfo().getPoint());
            tv_tx.setText("头衔：" + AppContext.getInstance().getUserInfo().getHonor());

        }

    }


    public void updataui() {
        if (AppContext.getInstance().checkUser()) {
            if (AppContext.getInstance().getUserInfo().getAvatar().length() < 1) {
                iv_lefthead.setImageResource(R.drawable.img_head);
            } else {
                RequestOptions options = new RequestOptions();
                options.dontAnimate();
                options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                options.fitCenter();
                Glide.with(this).load(AppConfig.mainurl + AppContext.getInstance().getUserInfo().getAvatar()).apply(options).into(iv_lefthead);
            }
            tv_gx.setText("个性签名：" + AppContext.getInstance().getUserInfo().getSpecial());
            tv_nc.setText("昵称：" + AppContext.getInstance().getUserInfo().getNickname());
            tv_jf.setText("积分：" + AppContext.getInstance().getUserInfo().getPoint());
            tv_tx.setText("头衔：" + AppContext.getInstance().getUserInfo().getHonor());

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppContext.getInstance().checkUser()) {
            tv_jf.setText("积分：" + AppContext.getInstance().getUserInfo().getPoint());
        }
    }

    private void initData() {
        this.mFragment = new Fragment[7];
        this.mFragmentManager = getSupportFragmentManager();
        mFragment[0] = this.mFragmentManager.findFragmentById(R.id.ll_frag_mian);
        mFragment[1] = this.mFragmentManager.findFragmentById(R.id.ll_frag_01);
        mFragment[2] = this.mFragmentManager.findFragmentById(R.id.ll_frag_02);
        mFragment[3] = this.mFragmentManager.findFragmentById(R.id.ll_frag_03);
        mFragment[4] = this.mFragmentManager.findFragmentById(R.id.ll_frag_04);
        mFragment[5] = this.mFragmentManager.findFragmentById(R.id.ll_frag_05);
        mFragment[6] = this.mFragmentManager.findFragmentById(R.id.ll_frag_06);
        mFragmentTransaction = mFragmentManager.beginTransaction()
                .hide(mFragment[0]).hide(mFragment[1]).hide(mFragment[2]).hide(mFragment[3]).hide(mFragment[4]).hide(mFragment[5]).hide(mFragment[6]);
        mFragmentTransaction.show(mFragment[0]).commit();
        mPtrFrame_main = (PtrClassicFrameLayout) findViewById(R.id.frag_main_ptrframelayout);
        sv_main = (ScrollView) findViewById(R.id.ll_main);
        mPtrFrame1 = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame1);
        mPtrFrame2 = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame2);
        mPtrFrame3 = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame3);

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mFragmentTransaction = mFragmentManager.beginTransaction()
                    .hide(mFragment[0]).hide(mFragment[1]).hide(mFragment[2])
                    .hide(mFragment[3]).hide(mFragment[4]).hide(mFragment[5])
                    .hide(mFragment[6]);
            ll_menu1.setBackgroundResource(R.color.yellow);
            ll_menu2.setBackgroundResource(R.color.yellow);
            ll_menu3.setBackgroundResource(R.color.yellow);
            ll_menu4.setBackgroundResource(R.color.yellow);
            ll_menu5.setBackgroundResource(R.color.yellow);
            ll_menu6.setBackgroundResource(R.color.yellow);
            ll_menu7.setBackgroundResource(R.color.yellow);
            switch (view.getId()) {
                case R.id.nav_zhuye:
                    mFragmentTransaction.show(mFragment[0]).commit();
                    sv_main.fullScroll(View.FOCUS_UP);
                    mPtrFrame_main.autoRefresh();
                    ll_menu1.setBackgroundResource(R.color.darkblue);
                    break;
                case R.id.nav_xiaoxi:
                    mFragmentTransaction.show(mFragment[1]).commit();
                    mPtrFrame1.autoRefresh();
                    ll_menu2.setBackgroundResource(R.color.darkblue);
                    break;
                case R.id.nav_zuji:
                    mFragmentTransaction.show(mFragment[2]).commit();
                    mPtrFrame2.autoRefresh();
                    ll_menu3.setBackgroundResource(R.color.darkblue);
                    break;
                case R.id.nav_shouchang:
                    mFragmentTransaction.show(mFragment[3]).commit();
                    mPtrFrame3.autoRefresh();
                    ll_menu4.setBackgroundResource(R.color.darkblue);
                    break;
                case R.id.nav_jifen:
                    mFragmentTransaction.show(mFragment[4]).commit();
                    ll_menu5.setBackgroundResource(R.color.darkblue);
                    break;
                case R.id.nav_set:
                    mFragmentTransaction.show(mFragment[5]).commit();
                    ll_menu6.setBackgroundResource(R.color.darkblue);
                    break;
                case R.id.nav_proposal:
                    mFragmentTransaction.show(mFragment[6]).commit();
                    ll_menu7.setBackgroundResource(R.color.darkblue);
                    break;
                case R.id.iv_lefthead:
                    mFragmentTransaction.show(mFragment[5]).commit();
                    ll_menu6.setBackgroundResource(R.color.darkblue);
                    break;

            }
            mDrawerLayout.closeDrawers();
        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_head:
                mDrawerLayout.openDrawer(GravityCompat.START);//点击侧滑
                break;
            //搜索产品
            case R.id.sousuo:
                startActivity(new Intent(this, SousuoActivity.class));
                break;
        }

    }

    /**
     * 返回键两次退出程序
     */
    private long mExitTime;
    private boolean isLand;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isLand)
            return false;
        else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //保证app字体不受系统字体影响
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    //设置透明状态栏
    private void setSystemBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // KITKAT解决方案
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void ShowAginLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this,
                android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setMessage("该账号已在其他设备登陆！");
        builder.setPositiveButton("重新登录",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppContext.getInstance().cleanUserInfo();
                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("HuierpuUser", mContext.MODE_PRIVATE);
                        SharedPreferences.Editor usereditor = sharedPreferences.edit();
                        usereditor.putString("token", "");
                        usereditor.commit();
                        AppContext.token=null;
                        finish();
                        startActivity(new Intent(mContext, LoginActivity.class));
                    }
                });
        builder.setCancelable(false);//设置不可点击back关闭dialog
        builder.show();
    }
}
