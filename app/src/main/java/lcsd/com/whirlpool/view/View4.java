package lcsd.com.whirlpool.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.entity.UserInfo;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.http.AppContext;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.listener.SampleListener;
import lcsd.com.whirlpool.util.L;

import com.alibaba.fastjson.JSON;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer1;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15.
 */
public class View4 extends FrameLayout implements View.OnClickListener {
    private static View4 view4 = null;
    private NormalGSYVideoPlayer1 video;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private String url, id, hit;
    private Context context;
    private TextView tv_hit;

    public static View4 getInstance(Context context, String url, String id, String hit) {
        if (view4 == null)
            return new View4(context, url, id, hit);
        return view4;
    }

    public View4(Context context, String url, String id, String hit) {
        super(context);
        this.context = context;
        this.hit = hit;
        this.url = url;
        this.id = id;
        LayoutInflater.from(context).inflate(R.layout.view4, this);
        initView();
        initData();
    }

    private void initView() {

        tv_hit = (TextView) findViewById(R.id.view4_tv_hit);
        tv_hit.setText(hit);
        video = (NormalGSYVideoPlayer1) findViewById(R.id.view4_video);

        //关闭正常播放状态下的滑动监听
        video.setIsTouchWiget(false);
        //关闭全屏状态下的滑动监听
        video.setIsTouchWigetFull(false);
        //隐藏进度条默认就是true隐藏
        video.setmIssetbaryc(true);
        //显示积分
        video.setmIsJfbar(true);
    }

    private void initData() {
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils((Activity) context, video);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        //关闭自动旋转
        video.setRotateViewAuto(false);
        video.setShowFullAnimation(false);
        video.setNeedLockFull(true);
        video.getJf().setOnClickListener(this);
        video.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                video.startWindowFullscreen(context, true, true);
            }
        });
        video.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });
        video.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
        video.setUp(AppConfig.mainurl + url, false, null, "");

    }


    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!video.isIfCurrentIsFullscreen()) {
                    video.startWindowFullscreen(context, true, true);
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (video.isIfCurrentIsFullscreen()) {
                    StandardGSYVideoPlayer1.backFromWindowFull(context);
                }
                if (orientationUtils != null) {
                    orientationUtils.setEnable(true);
                }
            }
        }
    }

    public void changevideo(int i) {
        switch (i) {
            case 0:
                GSYVideoPlayer.releaseAllVideos();
                if (orientationUtils != null)
                    orientationUtils.releaseListener();
                break;
            case 1:
                video.onVideoPause();
                break;
            case 2:
                video.onVideoResume();
                break;
        }
    }

    private long mLasttime = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getpoints:
                if (video != null) {
                    if (System.currentTimeMillis() - mLasttime < 2000)//防止快速点击操作
                        return;
                    mLasttime = System.currentTimeMillis();
                    if (video.getJindu() == 100) {
                        addpoint();
                    } else if (video.getJindu() < 100 && video.getJindu() > 80) {
                        Toast.makeText(context, "还差一点哦", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "视频看完才能获取积分", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
    //给外层activity提供视频进度值
    public int getjindu() {
        return video.getJindu();
    }

    private void addpoint() {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "usercp");
        map.put("f", "add_point");
        map.put("id", id);
        ApiClient.requestNetHandle(context, AppConfig.Sy, "增加中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    try {
                        JSONObject object = new JSONObject(json);
                        String status = object.getString("status");
                        String info = object.getString("info");
                        updatexinxi();
                        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "增加积分异常，请稍后重试!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //更新积分信息
    private void updatexinxi() {
        ApiClient.requestNetHandle(context, AppConfig.Sy + "?c=usercp", "", null, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                L.d("个人信息--------", json);
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
}
