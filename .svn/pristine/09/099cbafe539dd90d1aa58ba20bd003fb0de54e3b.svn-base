package com.minsheng.app.xunchedai.mine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.base.BaseActivity;
import com.minsheng.app.xunchedai.login.LoginActivity;
import com.minsheng.app.xunchedai.main.MainActivity;
import com.minsheng.app.xunchedai.utils.PopupWindowUtils;
import com.minsheng.app.xunchedai.utils.PreferenceUtils;
import com.minsheng.app.xunchedai.utils.T;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;

/**
 * 设置界面
 * **/
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.activity_concat_back)
    private ImageView iv_back;
    @ViewInject(R.id.tv_activity_concat_title)
    private TextView tv_title;
    @ViewInject(R.id.change_pwd)
    private LinearLayout ll_change_pwd;
    @ViewInject(R.id.clear_cache)
    private LinearLayout ll_clear_cache;
    @ViewInject(R.id.change_pay_pwd)
    private LinearLayout ll_change_pay_pwd;
    @ViewInject(R.id.logout)
    private Button btn_logout;
    private HttpUtils httpUtils;
    private PopupWindow popupWindow;
    private int init=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ViewUtils.inject(this);
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        httpUtils=new HttpUtils();
        tv_title.setText("设置");

        iv_back.setVisibility(View.VISIBLE);
        ll_change_pwd.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        ll_clear_cache.setOnClickListener(this);
        ll_change_pay_pwd.setOnClickListener(this);
        getInit();
    }

    public void getInit()
    {
        RequestParams params=new RequestParams();
        params.addBodyParameter("staffid", PreferenceUtils.loadUser(this, PreferenceUtils.STAFFID));
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL + WebServiceUrl.INIT_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                JSONObject obj = JSON.parseObject(json);
                int result = obj.getInteger("result");
                Log.i("123", "是否初始化：" + json);
                if (result == 1) {
                    init = obj.getInteger("init");
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                T.show(SettingActivity.this, "服务器错误" + s);
                finish();
            }
        });
    }

    /**
     * 登出
     */
    public void logout() {
        PreferenceUtils.clearLoginUser(SettingActivity.this);
        //登出成功则跳转至登录界面
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        MainActivity.instance.MainFinish();
    }
    /**
     * 询问是否退出登录
     */
    public void initPopupWindowLogout(View v) {
        popupWindow = PopupWindowUtils.newPop(this, R.layout.pop_setting_activity_logout , v);
        TextView tv_logout = (TextView) popupWindow.getContentView().findViewById(R.id.logout_pop);
        TextView tv_cancel = (TextView) popupWindow.getContentView().findViewById(R.id.cancel);

        tv_logout.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.change_pwd:
                intent = new Intent(this, ChangePwdActivity.class);
                intent.putExtra("state",1);
                break;
            case R.id.clear_cache:
                T.show(this, "缓存已清除！");
                break;
            case R.id.logout:
                initPopupWindowLogout(v);
                break;
            case R.id.logout_pop:
                logout();
                PopupWindowUtils.destroy(popupWindow);
                break;
            case R.id.cancel:
                PopupWindowUtils.destroy(popupWindow);
                break;
            case R.id.change_pay_pwd:
                intent = new Intent(this, ChangePwdActivity.class);
                //没有初始化支付密码
                if (init==0)
                {
                    intent.putExtra("state",3);
                }
                //已经初始化支付密码
                else if (init==1)
                {
                    intent.putExtra("state",2);
                }
                break;
            default:
                break;
        }
        if (intent!=null) {
            startActivity(intent);
        }

    }

}
