package com.minsheng.app.xunchedai.mine.activities;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.minsheng.app.xunchedai.utils.PreferenceUtils;
import com.minsheng.app.xunchedai.utils.T;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;

/**
 * 主界面
 * **/
public class ChangePwdActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.activity_concat_back)
    private ImageView iv_back;
    @ViewInject(R.id.tv_activity_concat_title)
    private TextView tv_title;
    @ViewInject(R.id.btn_submit)
    private Button btn_submit;
    @ViewInject(R.id.et_old_pwd)
    private EditText et_old_pwd;
    @ViewInject(R.id.et_new_pwd)
    private EditText et_new_pwd;
    @ViewInject(R.id.et_confirm_pwd)
    private EditText et_confirm_pwd;
    @ViewInject(R.id.ll_pwd_bottom)
    private LinearLayout ll_pwd_bottom;
    private String old_pwd,new_pwd,confirm_pwd;
    private HttpUtils httpUtils;
    private int state=-1;//标记界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ViewUtils.inject(this);
        initView();
    }

    /**
     * 初始化修改登录密码
     */
    private void initView() {
        iv_back.setVisibility(View.VISIBLE);
        btn_submit.setOnClickListener(this);
        httpUtils = new HttpUtils();
        Intent intent = getIntent();
        state = intent.getIntExtra("state",-1);
        //显示的是密码界面
        switch (state) {
            case 1:
                tv_title.setText("修改登录密码");
                break;
            case 2:
                tv_title.setText("修改支付密码");
                break;
            case 3:
                tv_title.setText("初始化支付密码");
                ll_pwd_bottom.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


    //检查是否为空
    public boolean checkNull()
    {
        boolean isNull=true;
        old_pwd=et_old_pwd.getText().toString();
        new_pwd=et_new_pwd.getText().toString();
        confirm_pwd=et_confirm_pwd.getText().toString();
        if (TextUtils.isEmpty(old_pwd))
        {
            T.show(this,"请输入原始密码");
            isNull=false;
        }

        if (TextUtils.isEmpty(new_pwd))
        {
            T.show(this,"请输入新密码");
            isNull=false;
        }

        if (TextUtils.isEmpty(confirm_pwd))
        {
            T.show(this,"请输入确认密码");
            isNull=false;
        }else {

            if (!new_pwd.equals(confirm_pwd))
            {
                T.show(this,"两次密码输入不一致");
                isNull=false;
            }
        }
        return isNull;

    }


    //检查是否为空
    public boolean checkNull2()
    {
        boolean isNull=true;
        new_pwd=et_new_pwd.getText().toString();
        confirm_pwd=et_confirm_pwd.getText().toString();
        if (TextUtils.isEmpty(new_pwd))
        {
            T.show(this,"请输入原始密码");
            isNull=false;
        }

        if (TextUtils.isEmpty(confirm_pwd))
        {
            T.show(this,"请输入确认密码");
            isNull=false;
        }else {
            if (!new_pwd.equals(confirm_pwd))
            {
                T.show(this,"两次密码输入不一致");
                isNull=false;
            }else {
                String reg="^\\d{6}$";
                if (!new_pwd.matches(reg)){
                    T.show(this,"密码必须是六位数字");
                    isNull=false;
                }else {
                    if (new_pwd.equals("123456")||new_pwd.equals("000000"))
                    {
                        T.show(this,"密码设置不能太简单");
                        isNull=false;
                    }
                }
            }
        }
        return isNull;

    }

    //检查是否为空
    public boolean checkNull3()
    {
        boolean isNull=true;
        old_pwd=et_old_pwd.getText().toString();
        new_pwd=et_new_pwd.getText().toString();
        confirm_pwd=et_confirm_pwd.getText().toString();
        if (TextUtils.isEmpty(old_pwd))
        {
            T.show(this,"请输入原始密码");
            isNull=false;
        }

        if (TextUtils.isEmpty(new_pwd))
        {
            T.show(this,"请输入新密码");
            isNull=false;
        }

        if (TextUtils.isEmpty(confirm_pwd))
        {
            T.show(this,"请输入确认密码");
            isNull=false;
        }else {

            if (!new_pwd.equals(confirm_pwd))
            {
                T.show(this,"两次密码输入不一致");
                isNull=false;
            }else {
                String reg="^\\d{6}$";
                if (!new_pwd.matches(reg)){
                    T.show(this,"密码必须是六位数字");
                    isNull=false;
                }else {
                    if (new_pwd.equals("123456")||new_pwd.equals("000000"))
                    {
                        T.show(this,"密码设置不能太简单");
                        isNull=false;
                    }
                }
            }
        }
        return isNull;

    }

    //修改密码的方法
    public void updatePwd()
    {
        RequestParams params=new RequestParams();
        params.addBodyParameter("staffid", PreferenceUtils.loadUser(this,PreferenceUtils.STAFFID));
        params.addBodyParameter("oldpassword",old_pwd);
        params.addBodyParameter("password",new_pwd);
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL + WebServiceUrl.CHANGE_PWD_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                Log.i("123", "json:" + json);
                JSONObject obj= JSON.parseObject(json);
                int result=obj.getInteger("result");
                String message=obj.getString("message");
                T.show(ChangePwdActivity.this,message);
                if (result==1)
                {
                    T.show(ChangePwdActivity.this, "修改成功！");
                    finish();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                T.show(ChangePwdActivity.this,"服务器错误"+s);
                finish();
            }
        });
    }


    //修改支付密码的方法
    public void updatePayPwd()
    {
        RequestParams params=new RequestParams();
        params.addBodyParameter("staffid", PreferenceUtils.loadUser(this,PreferenceUtils.STAFFID));
        params.addBodyParameter("old_password",old_pwd);
        params.addBodyParameter("pay_password",new_pwd);
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL+WebServiceUrl.CHANGE_PAY_PWD_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                Log.i("123", "支付json:" + json);
                JSONObject obj= JSON.parseObject(json);
                int result=obj.getInteger("result");
                String message=obj.getString("message");
                T.show(ChangePwdActivity.this,message);
                if (result==1)
                {
                    T.show(ChangePwdActivity.this, "修改成功！");
                    finish();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                T.show(ChangePwdActivity.this,"服务器错误"+s);
                finish();
            }
        });
    }

    //初始化支付密码的方法
    public void initPayPwd()
    {
        RequestParams params=new RequestParams();
        params.addBodyParameter("staffid", PreferenceUtils.loadUser(this,PreferenceUtils.STAFFID));
        params.addBodyParameter("pay_password",new_pwd);
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL+WebServiceUrl.INIT_PAY_PWD_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                Log.i("123", "初始化支付密码:" + json);
                JSONObject obj= JSON.parseObject(json);
                int result=obj.getInteger("result");
                String message=obj.getString("message");
                T.show(ChangePwdActivity.this,message);
                if (result==1)
                {
                    T.show(ChangePwdActivity.this, "初始化成功！");
                    finish();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                T.show(ChangePwdActivity.this,"服务器错误"+s);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (state==1) {
                    if (checkNull()) {
                        updatePwd();
                    }
                }else  if(state==2){
                    if (checkNull3()) {
                        updatePayPwd();
                    }
                }else {
                    if (checkNull2()) {
                        initPayPwd();
                    }
                }
                break;
            default:
                break;
        }

    }

}
