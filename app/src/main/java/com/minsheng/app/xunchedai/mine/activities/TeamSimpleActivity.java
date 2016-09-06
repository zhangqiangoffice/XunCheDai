package com.minsheng.app.xunchedai.mine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.minsheng.app.xunchedai.mine.adapters.TeamAdapter;
import com.minsheng.app.xunchedai.mine.bean.Team;
import com.minsheng.app.xunchedai.utils.NetWorkUtils;
import com.minsheng.app.xunchedai.utils.PreferenceUtils;
import com.minsheng.app.xunchedai.utils.T;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/8/17.
 * 我的团队界面
 */
public class TeamSimpleActivity extends BaseActivity {
    @ViewInject(R.id.activity_concat_back)
    private ImageView iv_back;
    @ViewInject(R.id.tv_activity_concat_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_user_name)
    private TextView tv_user_name;
    @ViewInject(R.id.tv_user_position)
    private TextView tv_user_position;
    @ViewInject(R.id.tv_day_yj)
    private TextView tv_day_yj;
    @ViewInject(R.id.tv_month_yj)
    private TextView tv_month_yj;
    @ViewInject(R.id.tv_total_yj)
    private TextView tv_total_yj;
    @ViewInject(R.id.layout_emptyview)
    private View emptyView;
    private HttpUtils httpUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        ViewUtils.inject(this);
        init();
    }

    //初始化
    public void init()
    {
        tv_title.setText("我的团队");
        iv_back.setVisibility(View.VISIBLE);
        httpUtils=new HttpUtils();
        getTeamList();
    }

    //获取团队列表的方法
    public void getTeamList()
    {
        if (NetWorkUtils.isNetworkAvailable(this))
        {
            RequestParams params=new RequestParams();
            params.addBodyParameter("staffid", PreferenceUtils.loadUser(this, PreferenceUtils.STAFFID));
            httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL+WebServiceUrl.TEAM_URL, params
                    , new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String json=responseInfo.result;
                    JSONObject obj= JSON.parseObject(json);
                    Log.i("123", "json:" + json);
                    int result=obj.getInteger("result");
                    if (result==1)
                    {
                        Team team=JSON.parseObject(obj.toJSONString(), Team.class);
                        tv_user_name.setText(team.getUser_name());
                        tv_user_position.setText(team.getUser_duxunqu()+" "+team.getUser_position());
                        tv_day_yj.setText(String.format("%.2f", Float.valueOf(team.getUser_day_achievement())));
                        tv_month_yj.setText(String.format("%.2f", Float.valueOf(team.getUser_month_achievement())));
                        tv_total_yj.setText(String.format("%.2f", Float.valueOf(team.getUser_total_achievement())));
                        emptyView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    T.show(TeamSimpleActivity.this, "服务器错误：" + s);
                    emptyView.setVisibility(View.GONE);
                }
            });
        }else {
            NetWorkUtils.noNetDialog(this);
        }
    }
}
