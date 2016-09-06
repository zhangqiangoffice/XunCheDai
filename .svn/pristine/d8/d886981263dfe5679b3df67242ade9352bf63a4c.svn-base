package com.minsheng.app.xunchedai.message.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.loan.activities.AddLoanActivity;
import com.minsheng.app.xunchedai.message.activities.MessageDetailActivity;
import com.minsheng.app.xunchedai.message.adapters.MessageAdapter;
import com.minsheng.app.xunchedai.message.bean.Message;
import com.minsheng.app.xunchedai.mine.activities.DealActivity;
import com.minsheng.app.xunchedai.utils.DBManager;
import com.minsheng.app.xunchedai.utils.PreferenceUtils;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/8/15.
 * 消息Fragment
 */
public class MessageFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener {
    private View convertView;
    private ListView lv;
    private TextView tv_title;
    private TextView tv_add;
    private View empty_view;
    private TextView tv_empty;
    private ImageView iv_empty;
    private ProgressBar pb_emptyview;
    private MessageAdapter adapter;
    private List<Message>data=new ArrayList<>();
    private HttpUtils httpUtils;
    private DbUtils dbUtils;
    private int money;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_msg_main, container, false);
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
        getMsgList();
    }

    //初始化控件
    public void initView(View v)
    {
        lv= (ListView) v.findViewById(R.id.activity_customer_lv);
        tv_title= (TextView) v.findViewById(R.id.tv_activity_concat_title);
        tv_add= (TextView) v.findViewById(R.id.comment_sure);
        empty_view=v.findViewById(R.id.layout_emptyview);
        tv_empty= (TextView) v.findViewById(R.id.layout_tv_emptyview);
        iv_empty= (ImageView) v.findViewById(R.id.layout_iv_emptyview);
        pb_emptyview= (ProgressBar) v.findViewById(R.id.pb_emptyview);
        tv_title.setText("消息");
        lv.setOnItemClickListener(this);
        tv_add.setOnClickListener(this);
        httpUtils=new HttpUtils();
        dbUtils= DBManager.dbUtils(getActivity());
        getWithDraw();
        getMsgList();
    }

    //获取消息列表的方法
    public void getMsgList()
    {
        RequestParams params=new RequestParams();
        params.addBodyParameter("staffid", PreferenceUtils.loadUser(getActivity(), PreferenceUtils.STAFFID));
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL + WebServiceUrl.MESSAGE_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                JSONObject obj = JSON.parseObject(json);
                int result = obj.getInteger("result");
                String message = obj.getString("message");
                if (result == 1) {
                    JSONArray array = obj.getJSONArray("list");
                    data = JSON.parseArray(array.toJSONString(), Message.class);
                    //加上原先推送的，但是未读的
                    data.addAll(DBManager.getMsgs(dbUtils));
                    if (data.size() == 0) {
                        pb_emptyview.setVisibility(View.GONE);
                        iv_empty.setImageResource(R.drawable.icon_message_tip);
                        iv_empty.setVisibility(View.VISIBLE);
                    } else {
                        DBManager.saveMsg(dbUtils, data);
                        empty_view.setVisibility(View.GONE);
                    }
                    adapter = new MessageAdapter(data, getActivity());
                    lv.setAdapter(adapter);
                } else {
                    pb_emptyview.setVisibility(View.GONE);
                    tv_empty.setText(message);
                    tv_empty.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                pb_emptyview.setVisibility(View.GONE);
                tv_empty.setText("服务器错误：" + s);
                tv_empty.setVisibility(View.VISIBLE);
            }
        });
    }

    //获取提现相关数据的方法
    public void getWithDraw()
    {
        RequestParams params=new RequestParams();
        params.addBodyParameter("staffid", PreferenceUtils.loadUser(getActivity(), PreferenceUtils.STAFFID));
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL + WebServiceUrl.GET_EXTRACT_INFO_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                Log.i("123", "tixianjson:" + json);
                JSONObject obj = JSON.parseObject(json);
                int result = obj.getInteger("result");
                if (result == 1) {
                    if (!TextUtils.isEmpty(obj.getString("remainder"))) {
                        money=Integer.parseInt(obj.getString("remainder"));
                    }else
                    {
                        money=0;
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    //listview的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        if (data.get(position).getType()==0) {
            intent = new Intent(getActivity(), AddLoanActivity.class);
            intent.putExtra("isDetail",true);
            intent.putExtra("loan_id", data.get(position).getId());
        }else if (data.get(position).getType()==1)
        {
            intent = new Intent(getActivity(), DealActivity.class);
            intent.putExtra("money",money);
        } else {
            intent = new Intent(getActivity(), MessageDetailActivity.class);
            intent.putExtra("message", data.get(position));
        }
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
    }

}
