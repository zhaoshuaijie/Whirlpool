package lcsd.com.whirlpool.http;

import android.content.Context;
import android.widget.Toast;

import lcsd.com.whirlpool.listener.ResultListener;
import lcsd.com.whirlpool.util.L;
import lcsd.com.whirlpool.util.StringUtils;
import com.mingle.widget.ShapeLoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/1/26.
 */
public class ApiClient {

    public static ShapeLoadingDialog progressDialog = null;


    /**
     * 请求网络数据接口
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetHandle(final Context context, String url, String log, Map<String, Object> mapP, final ResultListener listener) {
        requestNetHandle(context, url, log, mapP, listener, false);
    }

    /**
     * 请求网络数据接口
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetHandle(final Context context, final String url, String log, Map<String, Object> mapP, final ResultListener listener, boolean isCache) {
        if (!AppContext.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            if (!StringUtils.isEmpty(log)) {
                progressDialog = null;
                progressDialog=new ShapeLoadingDialog.Builder(context).loadText(log).build();
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

            }
            if(AppContext.token!=null){
                if(mapP==null){
                    mapP=new HashMap<>();
                }
                mapP.put("token",AppContext.token);
            }
            if (null != mapP && mapP.size() > 0) {
                L.d("TAG", "上传的参数:" + mapP.toString() + "请求地址:" + url);
            }
            OKHttpUtils.getInstance().post(url, mapP, new OKHttpUtils.HttpCallBack() {
                @Override
                public void onResult(boolean success, String response) {
                    if (success) {
                        if (null != progressDialog && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        L.d("回调数据:" + response + "----" + url);
                        if (StringUtils.isEmpty(response)) {
                            listener.onFailure("服务器返回空数据");
                            return;
                        }
                        //成功
//                        ServerData serverData = JSON.parseObject(response, ServerData.class);

//                        if (null != serverData) {
//                            if (serverData.getError() == 0) {
//                                //  L.d("回调数据:" + serverData.getData());
//                                listener.onSuccess(response);
//                            } else {
//                                listener.onFailure(response);
//                            }
//                        } else {
//                            listener.onFailure("请求数据失败");
//                        }
                        listener.onSuccess(response);
                    } else {
                        listener.onFailure(response);
                        if (null != progressDialog && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }
            }, isCache);
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }


}
