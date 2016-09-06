package com.minsheng.app.xunchedai.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.base.BaseActivity;

/**
 * Created by Administrator on 2016/8/15.
 * 找回密码界面
 */
public class ForgetPassActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.tv_activity_concat_title)
    private TextView tv_title;
    @ViewInject(R.id.login_hot_phone)
    private TextView tv_hot_phone;
    @ViewInject(R.id.activity_concat_back)
    private ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        ViewUtils.inject(this);
        init();
    }

    //初始化数据
    public void init()
    {
        tv_title.setText("找回密码");
        iv_back.setVisibility(View.VISIBLE);
        tv_hot_phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login_hot_phone:
                String phone=tv_hot_phone.getText().toString();
                if (phone!=null) {
                    Intent phoneIntent = new Intent(
                            "android.intent.action.CALL", Uri.parse("tel:"
                            + phone));
                    // 启动
                    startActivity(phoneIntent);
                }
                break;
            default:
                break;
        }
    }
}
