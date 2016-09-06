package com.minsheng.app.xunchedai.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.minsheng.app.xunchedai.main.MainActivity;
import com.minsheng.app.xunchedai.utils.PreferenceUtils;
import com.minsheng.app.xunchedai.utils.ScreenUtils;
import com.minsheng.app.xunchedai.utils.T;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Administrator on 2016/8/8.
 * 签名页界面
 */
public class SignActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.tv_activity_concat_title)
    private TextView tv_title;
    @ViewInject(R.id.linear_show)
    private LinearLayout linear_show;
    @ViewInject(R.id.ck_choose)
    private CheckBox ck_choose;
    @ViewInject(R.id.iv_sign)
    private ImageView ivSign;
    @ViewInject(R.id.btn_sign_submit)
    private Button btn_submit;
    @ViewInject(R.id.btn_false_submit)
    private Button btn_false;
    private HttpUtils httpUtils;
    private File img;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ViewUtils.inject(this);
        init();
    }

    //初始化数据
    public void init()
    {
        tv_title.setText("文件签署页");
        btn_submit.setOnClickListener(this);
        ck_choose.setOnClickListener(this);
        httpUtils=new HttpUtils();
    }

    //签名方法
    public void sign()
    {
        RequestParams params=new RequestParams();
        params.addQueryStringParameter("staffid", PreferenceUtils.loadUser(this,PreferenceUtils.STAFFID));
        params.addBodyParameter("photo", img);
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL+WebServiceUrl.SIGN_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                int result=-1;
                JSONObject obj= JSON.parseObject(json);
                result=obj.getInteger("result");
                String message=obj.getString("message");
                if (result==1)
                {
                    //签名成功，保存签名状态
                    PreferenceUtils.saveLoginUserBool(SignActivity.this, PreferenceUtils.IS_SIGNED, true);
                    Intent intent=new Intent(SignActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    //签名失败，保存签名状态
                    PreferenceUtils.saveLoginUserBool(SignActivity.this, PreferenceUtils.IS_SIGNED, false);
                    T.show(SignActivity.this,message);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
             T.show(SignActivity.this,"服务器错误"+s);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==2&&requestCode==2) {
            String imageUrl = data.getStringExtra("imageUrl");
            img=new File(imageUrl);
            Picasso.with(this).load(img).resize(ScreenUtils.getScreenWidth(this), 300).centerInside().into(ivSign);
            linear_show.setVisibility(View.VISIBLE);
            btn_false.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);
        } else {
            ck_choose.setChecked(false);
            btn_false.setVisibility(View.VISIBLE);
            btn_submit.setVisibility(View.GONE);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_submit:
                sign();
                break;
            case R.id.ck_choose:
                if (ck_choose.isChecked()) {
                    Intent intent = new Intent(SignActivity.this, WritePadActivity.class);
                    startActivityForResult(intent, 2);
                }
                break;
            default:
                break;
        }
    }
}
