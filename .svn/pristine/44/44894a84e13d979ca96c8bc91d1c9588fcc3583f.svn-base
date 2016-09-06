package com.minsheng.app.xunchedai.message.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.base.BaseActivity;
import com.minsheng.app.xunchedai.message.bean.Message;
import com.minsheng.app.xunchedai.utils.DBManager;

/**
 * Created by Administrator on 2016/8/15.
 * 消息详情界面
 */
public class MessageDetailActivity extends BaseActivity {
    @ViewInject(R.id.activity_concat_back)
    private ImageView iv_back;
    @ViewInject(R.id.tv_activity_concat_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_theme)
    private TextView tv_theme;
    @ViewInject(R.id.tv_time)
    private TextView tv_time;
    @ViewInject(R.id.tv_content)
    private TextView tv_content;
    private Message message;
    private DbUtils dbUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ViewUtils.inject(this);
        init();
    }

    public void init()
    {
        iv_back.setVisibility(View.VISIBLE);
        tv_title.setText("消息详情");
        Intent intent=getIntent();
        message= (Message)intent.getSerializableExtra("message");
        dbUtils=DBManager.dbUtils(this);
        tv_theme.setText(message.getTheme());
        tv_time.setText(message.getTime());
        tv_content.setText(message.getContent());

    }

    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        DBManager.deleteMsg(dbUtils, message.getId());
        finish();
    }
}
