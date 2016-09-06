package com.minsheng.app.xunchedai.mine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.base.BaseActivity;
import com.minsheng.app.xunchedai.utils.NetWorkUtils;
import com.minsheng.app.xunchedai.utils.PreferenceUtils;
import com.minsheng.app.xunchedai.utils.T;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;

/**
 * 主界面
 * **/
public class AddBankActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_title, tv_comment_sure;
    private EditText et_bank_num, et_bank_name, et_bank_phone;
    private String str_bank_num, str_bank_name, str_bank_phone;
    private HttpUtils httpUtils;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {

        iv_back= (ImageView) findViewById(R.id.activity_concat_back);
        tv_title= (TextView) findViewById(R.id.tv_activity_concat_title);
        tv_comment_sure = (TextView) findViewById(R.id.comment_sure);
        et_bank_num = (EditText) findViewById(R.id.bank_num);
        et_bank_name = (EditText) findViewById(R.id.bank_name);
        et_bank_phone = (EditText) findViewById(R.id.bank_phone);
        httpUtils = new HttpUtils();


        tv_comment_sure.setText("保存");
        tv_title.setText("绑定银行卡");
        iv_back.setVisibility(View.VISIBLE);
        tv_comment_sure.setVisibility(View.VISIBLE);
        tv_comment_sure.setOnClickListener(this);

    }

    //检查输入格式是否正确
    private Boolean checkData() {
        str_bank_num = et_bank_num.getText().toString();
        str_bank_name = et_bank_name.getText().toString();
        str_bank_phone = et_bank_phone.getText().toString();

        if (TextUtils.isEmpty(str_bank_num)) {
            T.show(this, "银行卡号不得为空");
            return false;
        }

        if (TextUtils.isEmpty(str_bank_name)) {
            T.show(this, "银行名称不得为空");
            return false;
        }

        if (TextUtils.isEmpty(str_bank_phone)) {
            T.show(this, "预留手机号不得为空");
            return false;
        }

        return true;
    }

    //绑定银行卡
    private void bandBankCard() {
        //如果网络存在
        if (NetWorkUtils.isNetworkAvailable(this)) {

            RequestParams params = new RequestParams();
            params.addBodyParameter("staffid", PreferenceUtils.loadUser(this, PreferenceUtils.STAFFID));
            params.addBodyParameter("banknum", str_bank_num);
            params.addBodyParameter("bankname", str_bank_name);
            params.addBodyParameter("bankphone", str_bank_phone);
            httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL + WebServiceUrl.BANDBANK_URL, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String json = responseInfo.result;
                    Log.i("123", "json:" + json);
                    int result = -1;
                    JSONObject obj = JSON.parseObject(json);
                    result = obj.getInteger("result");

                    if (result == 1) {

                        Intent intent = new Intent(AddBankActivity.this, MyBankActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("banknum", str_bank_num);
                        bundle.putString("bankname", str_bank_name);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                    } else {
                        T.show(AddBankActivity.this,obj.getString("message"));
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    T.show(AddBankActivity.this,"服务器错误："+s);
                }
            });

        }
        //否则设置网络
        else {
            NetWorkUtils.noNetDialog(this);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.comment_sure:
                if (checkData()) {
                    bandBankCard();
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
