package com.minsheng.app.xunchedai.loan.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.base.BaseActivity;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;
import com.minsheng.app.xunchedai.views.ZoomImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/8/16.
 * 显示图片界面
 */
public class ShowPicActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_show_pic)
    private ImageView iv;
    @ViewInject(R.id.activity_concat_back )
    private ImageView iv_back;
    @ViewInject(R.id.tv_activity_concat_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_background)
    private RelativeLayout rl_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        ViewUtils.inject(this);
        init();
    }

    //初始化数据
    public void init()
    {
        Intent intent=getIntent();
        String imageUrl=intent.getStringExtra("imageUrl");
        Picasso.with(this).load(WebServiceUrl.IMAGE_URL+imageUrl).into(iv);
        tv_title.setText("");
        iv_back.setVisibility(View.VISIBLE);
        rl_background.setBackgroundColor(Color.BLACK);
    }

    @Override
    public void onClick(View v) {

    }
}
