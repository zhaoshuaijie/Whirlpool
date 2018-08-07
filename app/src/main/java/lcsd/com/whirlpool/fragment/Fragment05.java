package lcsd.com.whirlpool.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.tools.PictureFileUtils;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.LoginActivity;
import lcsd.com.whirlpool.activity.MainActivity;
import lcsd.com.whirlpool.dialog.ActionSheetDialog1;
import lcsd.com.whirlpool.entity.UserInfo;
import lcsd.com.whirlpool.http.ApiClient;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.http.AppContext;
import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.util.L;
import lcsd.com.whirlpool.view.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wei on 2017/6/7.
 */
public class Fragment05 extends Fragment {
    private Context context;
    private TextView spinner1, spinner2, spinner3, spinner4, tv_xm, tv_jf, tv_bc, tv_tc;
    private EditText et_nc, et_qm;
    private CircleImageView civ;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private File headFile;
    private ActionSheetDialog1 dialog;
    private static final int PICK_PHOTO = 1;
    private ArrayList<String> mPhotoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_main_05, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        initData();
    }

    private void initView() {
        spinner1 = (TextView) getActivity().findViewById(R.id.spinner1);
        spinner2 = (TextView) getActivity().findViewById(R.id.spinner2);
        spinner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSex();
            }
        });
        spinner3 = (TextView) getActivity().findViewById(R.id.spinner3);
        spinner4 = (TextView) getActivity().findViewById(R.id.spinner4);
        civ = (CircleImageView) getActivity().findViewById(R.id.f5_civ);
        civ.setOnClickListener(tonclick);
        tv_bc = (TextView) getActivity().findViewById(R.id.f5_tv_bc);
        tv_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_qm.getText().toString().length() < 1 && et_nc.getText().toString().length() < 1) {
                    Toast.makeText(context, "请输入要修改的昵称或签名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_qm.getText().toString().length() > 1 && et_nc.getText().toString().length() < 1) {
                    request_xgall(1, et_qm.getText().toString(), null, null);
                } else if (et_qm.getText().toString().length() < 1 && et_nc.getText().toString().length() > 1) {
                    request_xgall(0, et_nc.getText().toString(), null, null);
                } else if (et_qm.getText().toString().length() > 1 && et_nc.getText().toString().length() > 1) {
                    request_xgall(1, et_qm.getText().toString(), null, null);
                    request_xgall(0, et_nc.getText().toString(), null, null);
                }
            }
        });
        tv_xm = (TextView) getActivity().findViewById(R.id.f5_tv_xm);
        tv_jf = (TextView) getActivity().findViewById(R.id.f5_tv_jf);
        et_nc = (EditText) getActivity().findViewById(R.id.f5_et_nc);
        et_qm = (EditText) getActivity().findViewById(R.id.f5_et_qm);
        if (AppContext.getInstance().checkUser()) {
            spinner1.setText(" 头衔：" + AppContext.getInstance().getUserInfo().getHonor());
            if (AppContext.getInstance().getUserInfo().getGender().equals("1")) {
                spinner2.setText(" 性别：男");
            } else {
                spinner2.setText(" 性别：女");
            }
            spinner3.setText(" 所属公司：" + AppContext.getInstance().getUserInfo().getDepartment());
            spinner4.setText(" 职务：" + AppContext.getInstance().getUserInfo().getPosition());
            tv_xm.setText(" 姓名：" + AppContext.getInstance().getUserInfo().getFullname());
            tv_jf.setText(" 积分：" + AppContext.getInstance().getUserInfo().getPoint());
        }
        tv_tc = (TextView) getActivity().findViewById(R.id.f5_tv_tc);
        tv_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectlogout();
            }
        });
    }

    private void selectlogout() {
        dialog = new ActionSheetDialog1(context);
        dialog.builder().setTitle("退出登录").addSheetItem("确定", null, new ActionSheetDialog1.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                request_tc();
            }
        }).show();
    }

    private void request_tc() {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "logout");
        ApiClient.requestNetHandle(context, AppConfig.Sy, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.d("退出登录-----------", json);
                    try {
                        JSONObject object = new JSONObject(json);
                        int status = object.getInt("status");
                        if (status == 1) {
                            AppContext.getInstance().cleanUserInfo();
                            SharedPreferences sharedPreferences = context.getSharedPreferences("HuierpuUser", context.MODE_PRIVATE);
                            SharedPreferences.Editor usereditor = sharedPreferences.edit();
                            usereditor.putString("token", "");
                            usereditor.commit();
                            context.startActivity(new Intent(context, LoginActivity.class));
                            getActivity().finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            JSONObject object = new JSONObject(json);
                            if (object.getString("status").equals("2")) {
                                ((MainActivity) getActivity()).ShowAginLoginDialog();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void request_xgall(int i, String s, String s1, String s2) {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "usercp");
        if (i == 0) {
            map.put("f", "nickname");
            map.put("nickname", s);
        } else if (i == 1) {
            map.put("f", "special");
            map.put("special", s);
        } else if (i == 2) {
            map.put("f", "info");
            map.put("gender", s);
            map.put("fullname", s1);
            map.put("department_id", s2);
        }

        ApiClient.requestNetHandle(context, AppConfig.Sy, "修改中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.d("修改信息：----------", json);
                    try {
                        JSONObject object = new JSONObject(json);
                        String info = object.getString("info");
                        int status = object.getInt("status");
                        if (status == 1) {
                            updateUI();
                        } else {
                            Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            JSONObject object = new JSONObject(json);
                            if (object.getString("status").equals("2")) {
                                ((MainActivity) getActivity()).ShowAginLoginDialog();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            Toast.makeText(context, "修改异常，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                L.d("修改信息异常：----------", msg);
            }
        });
    }

    private void initData() {
        if (AppContext.getInstance().checkUser()) {
            if (AppContext.getInstance().getUserInfo().getAvatar().length() < 1) {
                civ.setImageResource(R.drawable.img_head);
            } else {
                RequestOptions options = new RequestOptions();
                options.fitCenter();
                Glide.with(this).load(AppConfig.mainurl + AppContext.getInstance().getUserInfo().getAvatar()).apply(options).into(civ);
            }
        }
    }

    private View.OnClickListener tonclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showPhotoDialog();
        }
    };

    private void showPhotoDialog() {
        dialog = new ActionSheetDialog1(context);
        dialog.builder().setTitle("修改头像").addSheetItem("相册", null, new ActionSheetDialog1.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
// 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(Fragment05.this)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .previewImage(true)
                        .isCamera(true)
                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        .enableCrop(true)
                        //压缩图片
                        .compress(true)
                        .isGif(true)
                        .glideOverride(160, 160)
                        .withAspectRatio(1, 1)
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                        .minimumCompressSize(200)// 小于200kb的图片不压缩
                        .isDragFrame(false)// 是否可拖动裁剪框(固定)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    if (PictureSelector.obtainMultipleResult(data).size() > 0) {
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                        File file = new File(PictureSelector.obtainMultipleResult(data).get(0).getCompressPath());
                        if (file.exists()) {
                            request_photo(file);
                        }
                    }
                    break;
            }
        }
    }

    private void selectSex() {
        dialog = new ActionSheetDialog1(context);
        dialog.builder().setTitle("选择性别").addSheetItem("男", null, new ActionSheetDialog1.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                spinner2.setText(" 性别：男");
                request_xgall(2, "1", AppContext.getInstance().getUserInfo().getFullname(), AppContext.getInstance().getUserInfo().getDepartment_id());
            }
        }).addSheetItem("女", null, new ActionSheetDialog1.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                // 检查权限
                spinner2.setText(" 性别：女");
                request_xgall(2, "0", AppContext.getInstance().getUserInfo().getFullname(), AppContext.getInstance().getUserInfo().getDepartment_id());

            }
        }).show();
    }


    private void request_photo(File file) {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "upload");
        map.put("f", "save");
        map.put("upfile", file);
        ApiClient.requestNetHandle(context, AppConfig.Sy, "正在上传...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.d("图片上传：----------", json);
                    try {
                        JSONObject object = new JSONObject(json);
                        String info = object.getString("info");
                        int status = object.getInt("status");
                        if (status == 1) {
                            request_xg(info);
                        } else {
                            Toast.makeText(context, "上传失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                        //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
                        PictureFileUtils.deleteCacheDirFile(getActivity());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            JSONObject object = new JSONObject(json);
                            if (object.getString("status").equals("2")) {
                                ((MainActivity) getActivity()).ShowAginLoginDialog();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            Toast.makeText(context, "上传异常，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                L.d("图片上传异常：----------", msg);
            }
        });
    }

    private void request_xg(final String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("c", "usercp");
        map.put("f", "avatar");
        map.put("avatar", path);
        ApiClient.requestNetHandle(context, AppConfig.Sy, "正在修改...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    L.d("图片保存：----------", json);
                    try {
                        JSONObject object = new JSONObject(json);
                        String info = object.getString("info");
                        int status = object.getInt("status");
                        if (status == 1) {
                            AppContext.getInstance().getUserInfo().setAvatar(path);
                            updateUI();
                        } else {
                            Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            JSONObject object = new JSONObject(json);
                            if (object.getString("status").equals("2")) {
                                ((MainActivity) getActivity()).ShowAginLoginDialog();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            Toast.makeText(context, "上传异常，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                L.d("图片保存异常：----------", msg);
            }
        });
    }

    private void updateUI() {
        ApiClient.requestNetHandle(context, AppConfig.Sy + "?c=usercp", "", null, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                L.d("个人信息--------", json);
                try {
                    UserInfo userInfo = JSON.parseObject(json, UserInfo.class);
                    if (userInfo != null) {
                        if (AppContext.getInstance().checkUser()) {
                            AppContext.getInstance().cleanUserInfo();
                        }
                        AppContext.getInstance().saveUserInfo(userInfo);
                        RequestOptions options = new RequestOptions();
                        options.fitCenter();
                        Glide.with(context).load(AppConfig.mainurl + AppContext.getInstance().getUserInfo().getAvatar()).apply(options).into(civ);
                        MainActivity activity = (MainActivity) getActivity();
                        Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                        activity.updataui();
                    }
                } catch (Exception e) {
                    try {
                        JSONObject object = new JSONObject(json);
                        if (object.getString("status").equals("2")) {
                            ((MainActivity) getActivity()).ShowAginLoginDialog();
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                L.d("个人信息异常：---", msg);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppContext.getInstance().checkUser()) {
            tv_jf.setText(" 积分：" + AppContext.getInstance().getUserInfo().getPoint());
        }
    }
}