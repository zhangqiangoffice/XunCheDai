package com.minsheng.app.xunchedai.message.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.loan.activities.AddLoanActivity;
import com.minsheng.app.xunchedai.message.activities.MessageDetailActivity;
import com.minsheng.app.xunchedai.message.bean.Message;
import com.minsheng.app.xunchedai.utils.PreferenceUtils;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/8/15.
 *
 * 短信推送服务类，在后台长期运行，每个一段时间就向服务器发送一次请求
 *
 */
public class PushSmsService extends Service {
    private NotificationManager manager;
    private Notification notification;
    private NotificationCompat.Builder mBuilder;
    private HttpUtils httpUtils;
    private List<Message>data=new ArrayList<>();
    private boolean flag = true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("123","oncreate()");
        httpUtils=new HttpUtils();
//        getMsgList();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        this.flag = false;
        super.onDestroy();
    }

    private void notification(Message message) {
        // 获取系统的通知管理器
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.tickerText="新消息";
        notification.icon = R.drawable.icon_news;
        notification.defaults = Notification.DEFAULT_ALL; // 使用默认设置，比如铃声、震动、闪灯
        notification.flags = Notification.FLAG_AUTO_CANCEL; // 但用户点击消息后，消息自动在通知栏自动消失
        notification.flags |= Notification.FLAG_NO_CLEAR;// 点击通知栏的删除，消息不会依然不会被删除

        notification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知
        mBuilder = new NotificationCompat.Builder(this);
        String strType="";
        Intent resultIntent =null;
        if (message.getType()==0)
        {
            strType="贷款跟进";
            resultIntent = new Intent(this,AddLoanActivity.class);
            resultIntent.putExtra("isDetail",true);
            resultIntent.putExtra("loan_id", message.getId());
        }else if (message.getType()==1)
        {
            strType="余额变动";
            resultIntent = new Intent(this,AddLoanActivity.class);
        }else {
            resultIntent = new Intent(this,MessageDetailActivity.class);
            resultIntent.putExtra("message",message);
            strType="消息通知";
        }
        //点击的意图ACTION是跳转到Intent
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContentTitle(strType)
                .setContentText(message.getTheme())
                .setContentIntent(pendingIntent)
                .setTicker("点击查看")//通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                .setSmallIcon(R.drawable.icon_news)
                .setWhen(System.currentTimeMillis());

        manager.notify(0, mBuilder.build());

    }

    //获取消息列表的方法
    public void getMsgList()
    {
           RequestParams params=new RequestParams();
           params.addBodyParameter("staffid", PreferenceUtils.loadUser(getApplicationContext(), PreferenceUtils.STAFFID));
           httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL + WebServiceUrl.MESSAGE_URL, params, new RequestCallBack<String>() {
               @Override
               public void onSuccess(ResponseInfo<String> responseInfo) {
                   String json = responseInfo.result;
                   Log.i("123", "消息json:" + json);
                   JSONObject obj = JSON.parseObject(json);
                   int result = obj.getInteger("result");
                   String message = obj.getString("message");
                   if (result == 1) {
                       JSONArray array = obj.getJSONArray("list");
                       data = JSON.parseArray(array.toJSONString(), Message.class);
                       if (data.size()>0)
                       {
                        for (int i=0;i<data.size();i++)
                        {
                            notification(data.get(i));
                        }
                       }
                   }

               }

               @Override
               public void onFailure(HttpException e, String s) {
               }
           });
    }
}
