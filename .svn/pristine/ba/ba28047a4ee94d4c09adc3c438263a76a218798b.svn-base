package com.minsheng.app.xunchedai.mine.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.minsheng.app.xunchedai.mine.activities.DealActivity;
import com.minsheng.app.xunchedai.mine.activities.MyBankActivity;
import com.minsheng.app.xunchedai.mine.activities.SettingActivity;
import com.minsheng.app.xunchedai.mine.activities.TeamActivity;
import com.minsheng.app.xunchedai.mine.activities.TeamSimpleActivity;
import com.minsheng.app.xunchedai.mine.bean.Team;
import com.minsheng.app.xunchedai.utils.PreferenceUtils;
import com.minsheng.app.xunchedai.utils.T;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;
/**
 * Created by Administrator on 2016/8/5.
 * 我的Fragment
 */
public class MineFragment extends Fragment implements View.OnClickListener{

    private View convertView;
    private View emptyView;
    private TextView tv_title;
    private LinearLayout ll_my_bank, ll_setting,ll_money,ll_team;
    private TextView tv_name,tv_phone,tv_achievement,tv_profit;
    private HttpUtils httpUtils;
    private String str_bankname, str_banknum,position;
    private Team team;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_mine_main, container, false);
            initView(convertView);
        }
        ViewGroup viewParent = (ViewGroup) convertView.getParent();
        if (viewParent != null) {
            viewParent.removeView(convertView);
        }
        return convertView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    //初始化控件
    public void initView(View v)
    {
        tv_title= (TextView) v.findViewById(R.id.tv_activity_concat_title);
        ll_my_bank = (LinearLayout) v.findViewById(R.id.my_bank);
        ll_setting = (LinearLayout) v.findViewById(R.id.setting);
        ll_money= (LinearLayout) v.findViewById(R.id.my_money);
        ll_team= (LinearLayout) v.findViewById(R.id.my_team);
        tv_name= (TextView) v.findViewById(R.id.tv_user_name);
        tv_phone= (TextView) v.findViewById(R.id.tv_user_phone);
        tv_achievement= (TextView) v.findViewById(R.id.tv_user_achievement);
        tv_profit= (TextView) v.findViewById(R.id.tv_user_profit);
        emptyView=v.findViewById(R.id.layout_emptyview);
        tv_title.setText("个人中心");
        httpUtils=new HttpUtils();

        ll_my_bank.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_money.setOnClickListener(this);
        ll_team.setOnClickListener(this);
    }

    //获取个人信息
    public void getUserInfo()
    {
        RequestParams params=new RequestParams();
        params.addBodyParameter("staffid", PreferenceUtils.loadUser(getActivity(), PreferenceUtils.STAFFID));
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL + WebServiceUrl.USER_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                JSONObject obj= JSON.parseObject(json);
                Log.i("123","userinfo:"+json);
                tv_phone.setText(obj.getString("phone"));
                tv_name.setText(PreferenceUtils.loadUser(getActivity(), PreferenceUtils.USER_NAME));
                tv_profit.setText(obj.getString("profit"));
                tv_achievement.setText(obj.getString("achievement"));
                str_bankname = obj.getString("bankname");
                str_banknum = obj.getString("banknum");
                position=obj.getString("position");
                PreferenceUtils.saveLoginUser(getActivity(), PreferenceUtils.POSITION, position);
                PreferenceUtils.saveStr(getActivity(),PreferenceUtils.BANK_NAME,str_bankname);
                PreferenceUtils.saveStr(getActivity(),PreferenceUtils.BANK_NUM,str_banknum);
                emptyView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                T.show(getActivity(),"服务器错误："+s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.my_bank:
                intent = new Intent(getActivity(), MyBankActivity.class);
                bundle.putString("banknum", str_banknum);
                bundle.putString("bankname", str_bankname);
                intent.putExtras(bundle);
                break;
            case R.id.setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                break;
            case R.id.my_money:
                intent = new Intent(getActivity(), DealActivity.class);
                intent.putExtra("money",tv_profit.getText().toString());
                break;
            case R.id.my_team:
                    if (PreferenceUtils.loadUser(getActivity(),PreferenceUtils.POSITION).contains("督训长")||PreferenceUtils.loadUser(getActivity(), PreferenceUtils.POSITION).contains("分部经理"))
                    {
                        intent = new Intent(getActivity(), TeamActivity.class);
                    }else if (PreferenceUtils.loadUser(getActivity(), PreferenceUtils.POSITION).contains("客户经理")||PreferenceUtils.loadUser(getActivity(), PreferenceUtils.POSITION).contains("副经理"))
                    {
                        intent = new Intent(getActivity(), TeamSimpleActivity.class);
                    }else {
                        T.show(getActivity(),"暂无团队信息");
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
