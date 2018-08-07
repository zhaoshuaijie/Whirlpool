package lcsd.com.whirlpool.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.adapter.GridImageAdapter;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.manager.ActivityManager;
import lcsd.com.whirlpool.manager.FullyGridLayoutManager;
import lcsd.com.whirlpool.util.L;
import lcsd.com.whirlpool.util.StringUtils;
import lcsd.com.whirlpool.view.CleanableEditText;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private Context mContext;
    private CleanableEditText mEditText, mEditTextTitle;
    private TextView mTv_fk;
    private RecyclerView mRecyclerView;
    private GridImageAdapter mAdapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String cate_id, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        mContext = this;
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            cate_id = getIntent().getStringExtra("cate_id");
        }
        initView();
        initData();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
        mEditText = findViewById(R.id.f6_ed);
        mEditTextTitle = findViewById(R.id.f6_ed_title);
        mTv_fk = findViewById(R.id.f6_tv_fk);
        mRecyclerView = findViewById(R.id.recycler);
        mTv_fk.setOnClickListener(this);
    }

    private void initData() {
        tv_title.setText(title+"建议反馈");
        //改变默认的单行模式
        mEditText.setSingleLine(false);
        //水平滚动设置为False
        mEditText.setHorizontallyScrolling(false);

        mAdapter = new GridImageAdapter(mContext, onAddPicClickListener);
        mAdapter.setList(selectList);
        mAdapter.setSelectMax(3);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                PictureSelector.create((Activity) mContext).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    mAdapter.setList(selectList);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create((Activity) mContext)
                    .openGallery(PictureMimeType.ofImage())
                    .theme(R.style.picture_default_style)
                    .maxSelectNum(3)
                    .minSelectNum(1)
                    .selectionMode(PictureConfig.MULTIPLE)
                    .previewImage(true)
                    .isCamera(true)
                    .enableCrop(false)
                    .compress(true)
                    .glideOverride(160, 160)
                    .previewEggs(true)
                    .hideBottomControls(false)
                    .minimumCompressSize(200)// 小于200kb的图片不压缩
                    .isGif(false)
                    .selectionMedia(selectList)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_back:
                this.finish();
                break;
            case R.id.titlebar_home:
                ActivityManager.getActivityManager().finishAll();
                break;
            case R.id.f6_tv_fk:
                request_updata();
                break;
        }
    }

    private void request_updata() {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(mEditTextTitle.getText().toString())) {
            Toast.makeText(mContext, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(mEditText.getText().toString())) {
            Toast.makeText(mContext, "请输入描述", Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("c", "usercp");
        map.put("f", "feedback");
        map.put("title", mEditTextTitle.getText().toString());
        map.put("cate_id", cate_id);
        map.put("content", mEditText.getText().toString());
        if (selectList.size() > 0) {
            File[] files = new File[selectList.size()];
            for (int i = 0; i < selectList.size(); i++) {
                File file = new File(selectList.get(i).getCompressPath());
                files[i] = file;
            }
            map.put("pictures[]", files);
        }
        ApiClient.requestNetHandle(mContext, AppConfig.Sy, "上传中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.d("TAG", json);
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        int ststus = jsonObject.getInt("status");
                        if (ststus == 1) {
                            clearCache();
                        } else if (ststus == 2) {
                            ShowAginLoginDialog();
                        }else {
                            Toast.makeText(mContext, jsonObject.getString("info"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "上传失败,请稍候重试！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearCache() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions((Activity) mContext);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(mContext);
                } else {
                    Toast.makeText(mContext,
                            "读取内存卡权限被拒绝", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                finish();
            }

            @Override
            public void onComplete() {
                finish();
            }
        });
    }
}
