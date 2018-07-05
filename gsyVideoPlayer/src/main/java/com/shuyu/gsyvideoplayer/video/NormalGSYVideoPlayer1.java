package com.shuyu.gsyvideoplayer.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.R;

/**
 * Created by Administrator on 2017/10/17.
 */
//无进度条，带积分图标
public class NormalGSYVideoPlayer1  extends StandardGSYVideoPlayer1 {

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public NormalGSYVideoPlayer1(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public NormalGSYVideoPlayer1(Context context) {
        super(context);
    }

    public NormalGSYVideoPlayer1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout_normal;
    }

    @Override
    protected void updateStartImage() {
        ImageView imageView = (ImageView) mStartButton;
        if (mCurrentState == CURRENT_STATE_PLAYING) {
            imageView.setImageResource(R.drawable.video_click_pause_selector);
        } else if (mCurrentState == CURRENT_STATE_ERROR) {
            imageView.setImageResource(R.drawable.video_click_play_selector);
        } else {
            imageView.setImageResource(R.drawable.video_click_play_selector);
        }
    }
}
