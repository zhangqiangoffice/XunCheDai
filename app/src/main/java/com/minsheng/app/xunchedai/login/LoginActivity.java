package com.minsheng.app.xunchedai.login;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.minsheng.app.xunchedai.main.MainActivity;
import com.minsheng.app.xunchedai.utils.DateUtils;
import com.minsheng.app.xunchedai.utils.NetWorkUtils;
import com.minsheng.app.xunchedai.utils.PreferenceUtils;
import com.minsheng.app.xunchedai.utils.T;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Created by Administrator on 2016/8/8.
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.tv_activity_concat_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_login)
    private TextView tv_login;
    @ViewInject(R.id.login_edit_username)
    private EditText et_username;
    @ViewInject(R.id.login_edit_password)
    private EditText et_password;
    @ViewInject(R.id.linear_wran)
    private LinearLayout linear_wran;
    @ViewInject(R.id.tv_message)
    private TextView tv_message;
    @ViewInject(R.id.ck_login)
    private CheckBox ck_remeber;
    @ViewInject(R.id.tv_forget_pass)
    private TextView tv_forget_pass;
    @ViewInject(R.id.linear_hot_phone)
    private LinearLayout linear_hot_phone;
    private String username,password,mobilekey;
    private HttpUtils httpUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        //如果已经登录成功过了并且签名过了则跳转至主界面
        if (PreferenceUtils.loadUserBool(this,PreferenceUtils.IS_LOGIN)&&PreferenceUtils.loadUserBool(this,PreferenceUtils.IS_SIGNED))
        {
            if (NetWorkUtils.isNetworkAvailable(this))
            {
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else {
            init();
        }
    }

    /**
     * 初始化
     **/
    public void init()
    {
        tv_title.setText("登录");
        httpUtils=new HttpUtils();

        if (!PreferenceUtils.loadStr(this, PreferenceUtils.USER_NAME).isEmpty()) {
            et_username.setText(PreferenceUtils.loadStr(this, PreferenceUtils.USER_NAME));
        }

        if (!PreferenceUtils.loadStr(this, PreferenceUtils.LOGIN_DATE).isEmpty()) {
            //密码记住时间大于七天
            if (DateUtils.DateCompare(PreferenceUtils.loadStr(this, PreferenceUtils.LOGIN_DATE), DateUtils.formatPhotoDate(System.currentTimeMillis()))) {
                PreferenceUtils.saveStr(this, PreferenceUtils.LOGIN_DATE, "");
            }
            //否则记住密码
            else {
                if (!PreferenceUtils.loadStr(this, PreferenceUtils.PASSWORD).isEmpty()) {
                    et_password.setText(PreferenceUtils.loadStr(this, PreferenceUtils.PASSWORD));
                }
            }
        }

        tv_login.setOnClickListener(this);
        linear_hot_phone.setOnClickListener(this);
        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                }
                return false;
            }
        });

    }


    /**
     * 获取android设备唯一标识
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        String m_szLongID = szImei + "_" + m_szDevIDShort
                + "_" + m_szWLANMAC;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        byte p_md5Data[] = m.digest();
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            if (b <= 0xF)
                m_szUniqueID += "0";
            // 将数字转换成字符
            m_szUniqueID += Integer.toHexString(b);
        }   //转换成大写
        m_szUniqueID = m_szUniqueID.toUpperCase();
        return m_szUniqueID;
    }

    /**
     * 登录方法
     **/
    public void login()
    {
        username=et_username.getText().toString();
        password=et_password.getText().toString();
        mobilekey=getDeviceId(this);
        //如果网络存在
        if (NetWorkUtils.isNetworkAvailable(this)) {
            //用户名密码不为空
            if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)) {
                RequestParams params = new RequestParams();
                params.addBodyParameter("username", username);
                params.addBodyParameter("password", password);
                params.addBodyParameter("mobilekey", mobilekey);
                httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL + WebServiceUrl.LOGIN_URL, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String json = responseInfo.result;
                        int result = -1;
                        JSONObject obj = JSON.parseObject(json);
                        result = obj.getInteger("result");
                        String message=obj.getString("message");
                        //登录成功
                        if (result == 1) {
                            //保存当前的staffid
                            PreferenceUtils.saveLoginUser(LoginActivity.this, PreferenceUtils.STAFFID, obj.getString("staffid"));
                            //保存当前的用户名
                            PreferenceUtils.saveLoginUser(LoginActivity.this, PreferenceUtils.USER_NAME, obj.getString("name"));
                            PreferenceUtils.saveStr(LoginActivity.this, PreferenceUtils.USER_NAME, username);
                            //保存当前登录状态
                            PreferenceUtils.saveLoginUserBool(LoginActivity.this, PreferenceUtils.IS_LOGIN, true);

                            if (ck_remeber.isChecked()) {
                                PreferenceUtils.saveStr(LoginActivity.this, PreferenceUtils.PASSWORD, password);
                                PreferenceUtils.saveStr(LoginActivity.this, PreferenceUtils.LOGIN_DATE, DateUtils.formatPhotoDate(System.currentTimeMillis()));
                            } else {
                                PreferenceUtils.saveStr(LoginActivity.this, PreferenceUtils.PASSWORD, "");
                                PreferenceUtils.saveStr(LoginActivity.this, PreferenceUtils.LOGIN_DATE,"");
                            }

                            int signed = -1;
                            signed = obj.getInteger("signed");
                            //没有进行签名 则跳转至签名界面
                            if (signed == 0) {
                                //保存当前签名状态
                                PreferenceUtils.saveLoginUserBool(LoginActivity.this, PreferenceUtils.IS_SIGNED, false);
                                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            //已经进行签名
                            else {
                                //保存当前签名状态
                                PreferenceUtils.saveLoginUserBool(LoginActivity.this, PreferenceUtils.IS_SIGNED, true);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            tv_message.setText(message);
                            linear_wran.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                      T.show(LoginActivity.this,"服务器错误："+s);
                    }
                });
            }else
            {
                if (TextUtils.isEmpty(username))
                {
                    T.show(LoginActivity.this,"请输入用户名");
                }else if (TextUtils.isEmpty(password))
                {
                    T.show(LoginActivity.this,"请输入密码");
                }
            }
        }
        //否则设置网络
        else {
            NetWorkUtils.noNetDialog(this);
        }

    }

    @Override
    public void onClick(View v) {
     switch (v.getId())
     {
         //点击登录按钮事件
         case R.id.tv_login:
             login();
             break;
         //招呼密码界面
         case R.id.linear_hot_phone:
             Intent intent=new Intent(this,ForgetPassActivity.class);
             startActivity(intent);
             break;
         default:
             break;
     }

    }
}
