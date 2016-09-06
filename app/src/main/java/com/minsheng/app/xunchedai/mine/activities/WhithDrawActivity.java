package com.minsheng.app.xunchedai.mine.activities;

import android.app.Notification;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
 * 提现界面
 * **/
public class WhithDrawActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.activity_concat_back)
    private ImageView iv_back;
    @ViewInject(R.id.tv_activity_concat_title)
    private TextView tv_title;
    @ViewInject(R.id.btn_tixian)
    private Button btn_sub;
    @ViewInject(R.id.tv_remain_money)
    private TextView tv_money;
    @ViewInject(R.id.tv_level)
    private TextView tv_level;
    @ViewInject(R.id.et_input_money)
    private EditText et_money;
    @ViewInject(R.id.tv_bank)
    private TextView tv_bank;
    private HttpUtils httpUtils;
    private String money,pay_password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        ViewUtils.inject(this);
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        httpUtils=new HttpUtils();
        tv_title.setText("提现");

        iv_back.setVisibility(View.VISIBLE);
        btn_sub.setOnClickListener(this);
        String bankNum=PreferenceUtils.loadStr(this, PreferenceUtils.BANK_NUM);
        if (!TextUtils.isEmpty(bankNum)) {
            tv_bank.setText(PreferenceUtils.loadStr(this, PreferenceUtils.BANK_NAME + "(" + bankNum.substring(bankNum.length() - 4, bankNum.length()) + ")"));
        }
        getWithDraw();
    }

    //获取提现相关数据的方法
    public void getWithDraw()
    {
        RequestParams params=new RequestParams();
        params.addBodyParameter("staffid", PreferenceUtils.loadUser(this, PreferenceUtils.STAFFID));
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL + WebServiceUrl.GET_EXTRACT_INFO_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                Log.i("123", "json:" + json);
                JSONObject obj = JSON.parseObject(json);
                int result = obj.getInteger("result");
                if (result == 1) {
                    if (!TextUtils.isEmpty(obj.getString("remainder"))) {
                        tv_money.setText("￥" + obj.getString("remainder"));
                    } else {
                        tv_money.setText("￥0");
                    }
                    tv_level.setText("每次最低提现" + obj.getString("level") + "元");
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    //提现申请方法
    public void apply()
    {
        RequestParams params=new RequestParams();
        params.addBodyParameter("staffid",PreferenceUtils.loadUser(this,PreferenceUtils.STAFFID));
        params.addBodyParameter("money",money);
        params.addBodyParameter("pay_password",pay_password);
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL + WebServiceUrl.APPLY_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                JSONObject obj=JSON.parseObject(json);
                Log.i("123","提现："+json);
                int result=obj.getInteger("result");
                String message=obj.getString("message");
                T.show(WhithDrawActivity.this,message);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                T.show(WhithDrawActivity.this,s);
            }
        });
    }

    /**
     设置添加屏幕的背景透明度
     @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    public void showPopuwindow(View rl)
    {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popuwindow_layout, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        final LinearLayout ll_edits= (LinearLayout) contentView.findViewById(R.id.ll_edits);
        ImageView iv_cancel= (ImageView) contentView.findViewById(R.id.iv_cancel);
        TextView tv_tixian= (TextView) contentView.findViewById(R.id.pay_password);
        tv_tixian.setText("￥"+money);
        backgroundAlpha(0.4f);
        // 设置好参数之后再show
        popupWindow.showAtLocation(rl, Gravity.CENTER,0,0);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        int index=-1;
        for (int i=0;i<ll_edits.getChildCount();i++)
        {
            index=i;
            final EditText editText= (EditText) ll_edits.getChildAt(index);
            final int finalIndex = index;
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if ((finalIndex + 1) < ll_edits.getChildCount()) {
                        if (editText.length() == 1) {
                            EditText editText2 = (EditText) ll_edits.getChildAt(finalIndex + 1);
                            editText2.setFocusable(true);
                            editText2.setFocusableInTouchMode(true);
                            editText2.requestFocus();
                        }
                    }else if (finalIndex==ll_edits.getChildCount()-1)
                    {
                        for (int j=0;j<ll_edits.getChildCount();j++)
                        {
                            pay_password+=((EditText)ll_edits.getChildAt(j)).getText().toString();
                        }
                        apply();
                        popupWindow.dismiss();
                        backgroundAlpha(1f);
                    }
                }
            });
        }

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               popupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });

    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_tixian:
                money=et_money.getText().toString();
                if (TextUtils.isEmpty(money))
                {
                    T.show(this,"请输入提现金额");
                    return;
                }else{
                    int number=Integer.parseInt(money);
                    if (number<1000)
                    {
                        T.show(this,"提现金额不能低于1000");
                        return;
                    }
                }
                showPopuwindow(v);
                break;
            default:
                break;
        }
        if (intent!=null) {
            startActivity(intent);
        }

    }

}
