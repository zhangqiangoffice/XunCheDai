package com.minsheng.app.xunchedai.mine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.base.BaseActivity;

/**
 * 主界面
 * **/
public class MyBankActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_back, iv_search, iv_bank_img;
    private TextView tv_title, tv_bank_name, tv_bank_num;
    private String str_bank_num, str_bank_name, str_bank_num_new;
    private int bank_img;
    private LinearLayout ll_bank_list;
    private RelativeLayout rl_no_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank);
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {

        iv_back= (ImageView) findViewById(R.id.activity_concat_back);
        iv_bank_img = (ImageView) findViewById(R.id.bank_img);
        tv_title= (TextView) findViewById(R.id.tv_activity_concat_title);
        iv_search= (ImageView) findViewById(R.id.comment_search);
        tv_bank_name = (TextView) findViewById(R.id.bank_name);
        tv_bank_num = (TextView) findViewById(R.id.bank_num);
        ll_bank_list = (LinearLayout) findViewById(R.id.bank_list);
        rl_no_data = (RelativeLayout) findViewById(R.id.no_data);

        Bundle bundle = getIntent().getExtras();
        str_bank_num = bundle.getString("banknum");
        str_bank_name = bundle.getString("bankname");

        tv_title.setText("我的银行卡");
        if (!TextUtils.isEmpty(str_bank_num)) {
            str_bank_num_new = TextUtils.substring(str_bank_num, 0, 4) + " **** **** **** " + TextUtils.substring(str_bank_num, str_bank_num.length() - 4, str_bank_num.length());
            tv_bank_num.setText(str_bank_num_new);
        }
        if (!TextUtils.isEmpty(str_bank_name)) {
            tv_bank_name.setText(bundle.getString("bankname"));
            switch (str_bank_name) {
                case "中国银行":
                    bank_img = R.mipmap.bank_zg;
                    break;
                case "工商银行":
                    bank_img = R.mipmap.bank_gs;
                    break;
                case "交通银行":
                    bank_img = R.mipmap.bank_jt;
                    break;
                case "建设银行":
                    bank_img = R.mipmap.bank_js;
                    break;
                case "农业银行":
                    bank_img = R.mipmap.bank_ny;
                    break;
                default:
                    bank_img = R.mipmap.bank_default;
                    break;
            }
        }

        if (TextUtils.isEmpty(str_bank_num) && TextUtils.isEmpty(str_bank_name)) {
            iv_search.setVisibility(View.VISIBLE);
            ll_bank_list.setVisibility(View.GONE);
            rl_no_data.setVisibility(View.VISIBLE);
            iv_search.setOnClickListener(this);
        }



        iv_bank_img.setImageResource(bank_img);
        iv_search.setImageResource(R.mipmap.icon_add);
        iv_back.setVisibility(View.VISIBLE);


    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.comment_search:
                intent = new Intent(this, AddBankActivity.class);
                break;
            default:
                break;
        }
        if (intent!=null) {
            startActivity(intent);
            finish();
        }

    }



}
