package lcsd.com.whirlpool.http;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import lcsd.com.whirlpool.entity.UserInfo;
import lcsd.com.whirlpool.manager.Storage;
import lcsd.com.whirlpool.util.StringUtils;

import com.mob.MobSDK;
import com.tencent.smtt.sdk.QbSdk;

import java.util.List;


/**
 * Created by Administrator on 2016/1/25.
 */
public class AppContext extends Application {
    public static AppContext appContext;
    private static Context context;
    public static String token = null;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        context = this.getApplicationContext();

        QbSdk.initX5Environment(context, null);

        MobSDK.init(this);

        SharedPreferences sharedPreferences = getSharedPreferences("HuierpuUser", MODE_PRIVATE);
        if (sharedPreferences.getString("token", "") != null && sharedPreferences.getString("token", "").toString().length() > 1) {
            token = sharedPreferences.getString("token", "");
        }

        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        Log.d("极光", "[ExampleApplication] onCreate      " + JPushInterface.getRegistrationID(context));
    }

    public static AppContext getInstance() {
        if (appContext == null)
            return new AppContext();
        return appContext;
    }

    public static Context getContext() {
        return context;
    }



    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 保存缓存用户信息
     *
     * @param user
     */
    public void saveUserInfo(final UserInfo user) {
        if (user != null) {
            Storage.ClearUserInfo();
            Storage.saveUsersInfo(user);
        }
    }


    /**
     * 获取缓存用户信息
     *
     * @return
     */
    public UserInfo getUserInfo() {
        return Storage.GetUserInfo() == null ? new UserInfo() : Storage
                .GetUserInfo();
    }

    /**
     * 清除缓存用户信息
     *
     * @param
     */
    public void cleanUserInfo() {
        Storage.ClearUserInfo();
    }

    /**
     * 用户存在是ture 否则是false
     *
     * @return
     */
    public boolean checkUser() {
        if (StringUtils.isEmpty(getUserInfo().getUser_id())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }


    /**
     * 获取应用程序版本号-------------------针对图片上传
     *
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 获取当前应用包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取显示在最顶端的activity名称
     *
     * @param context
     * @return
     */
    public static String getTopActivityName(Context context) {
        String topActivityClassName = null;
        ActivityManager activityManager =
                (ActivityManager) (context.getSystemService(android.content.Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            topActivityClassName = f.getClassName();
        }
        return topActivityClassName;
    }

    /**
     * 判断是否运行在前台
     *
     * @param context
     * @return
     */
    public static boolean isRunningForeground(Context context) {
        String packageName = getPackageName(context);
        String topActivityClassName = getTopActivityName(context);
        if (packageName != null && topActivityClassName != null && topActivityClassName.startsWith(packageName)) {
            return true;
        } else {
            return false;
        }
    }
}
