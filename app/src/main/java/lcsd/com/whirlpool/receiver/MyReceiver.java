package lcsd.com.whirlpool.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import cn.jpush.android.api.JPushInterface;
import lcsd.com.whirlpool.activity.BaodianContentActivity;
import lcsd.com.whirlpool.activity.WebActivity;
import lcsd.com.whirlpool.activity.WelcomeActivity;
import lcsd.com.whirlpool.activity.ZixunContentActivity;
import lcsd.com.whirlpool.entity.Tuisong;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.http.AppContext;

/**
 * Created by Administrator on 2017/9/7.
 */
public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("TAG", "onReceive - " + intent.getAction());

        String text = bundle.getString(JPushInterface.EXTRA_EXTRA);

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d("TAG", "[MyReceiver] 接收Registration Id : " + regId);
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d("TAG", "收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d("TAG", "收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d("TAG", "用户点击打开了通知");
            if (AppContext.isRunningForeground(context)) {
                try {
                    Tuisong tuisong= JSON.parseObject(text,Tuisong.class);
                    if(tuisong.getType().equals("1")){
                        context.startActivity(new Intent(context, ZixunContentActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("id", tuisong.getId()).putExtra("title", tuisong.getTitle()).putExtra("url", AppConfig.Sy + "?id=" + tuisong.getId()).putExtra("img", ""));
                    }else if(tuisong.getType().equals("2")){
                        context.startActivity(new Intent(context, BaodianContentActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("id", tuisong.getId()).putExtra("title", tuisong.getTitle()));
                    }else if(tuisong.getType().equals("3")){
                        context.startActivity(new Intent(context, WebActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("url",tuisong.getId()).putExtra("title",tuisong.getTitle()));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                Intent i = new Intent(context, WelcomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    Tuisong tuisong= JSON.parseObject(text,Tuisong.class);
                    i.putExtra("type", tuisong.getType());
                    i.putExtra("id",tuisong.getId());
                    i.putExtra("title", tuisong.getTitle());
                    i.putExtra("tuisong","2");
                    context.startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    context.startActivity(i);
                }
            }
        } else {
            Log.d("TAG", "Unhandled intent - " + intent.getAction());
        }
    }
}
