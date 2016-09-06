package com.minsheng.app.xunchedai.mine.activities;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import com.minsheng.app.xunchedai.loan.bean.Loan;
import com.minsheng.app.xunchedai.mine.adapters.DealAdapter;
import com.minsheng.app.xunchedai.mine.bean.Deal;
import com.minsheng.app.xunchedai.pullrefresh.OnRefreshListener;
import com.minsheng.app.xunchedai.pullrefresh.PullRefreshLayout;
import com.minsheng.app.xunchedai.utils.PreferenceUtils;
import com.minsheng.app.xunchedai.utils.T;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/8/11.
 * 交易记录界面
 */
public class DealActivity extends BaseActivity implements OnRefreshListener,AdapterView.OnItemClickListener,View.OnClickListener {
    @ViewInject(R.id.refresh_layout)
    private PullRefreshLayout mRefreshLayout;
    @ViewInject(R.id.activity_customer_lv)
    private ListView lv;
    @ViewInject(R.id.tv_activity_concat_title)
    private TextView tv_title;
    @ViewInject(R.id.comment_sure)
    private TextView tv_add;
    @ViewInject(R.id.layout_emptyview)
    private View empty_view;
    @ViewInject(R.id.layout_tv_emptyview)
    private TextView tv_empty;
    @ViewInject(R.id.pb_emptyview)
    private ProgressBar pb_emptyview;
    @ViewInject(R.id.activity_concat_back)
    private ImageView iv_back;
    private DealAdapter adapter;
    private List<Deal>data=new ArrayList<>();
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mRefreshLayout.onRefreshComplete();
        }
    };
    private HttpUtils httpUtils;
    private int page=1;
    private boolean isHas=true;
    private String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);
        ViewUtils.inject(this);
        init();
    }

    //初始化控件
    public void init()
    {
        tv_title.setText("交易记录");
        tv_add.setText("提现");
        tv_add.setVisibility(View.VISIBLE);
        iv_back.setVisibility(View.VISIBLE);
        mRefreshLayout.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
        tv_add.setOnClickListener(this);
        httpUtils=new HttpUtils();
        Intent intent=getIntent();
        money=intent.getStringExtra("money");
        getDealList();
    }

   // 获取交易列表的方法
    public void getDealList()
    {
        RequestParams params=new RequestParams();
        params.addBodyParameter("staffid", PreferenceUtils.loadUser(this, PreferenceUtils.STAFFID));
        params.addBodyParameter("page",page+"");
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL+WebServiceUrl.RECORD_ALL_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                Log.i("123", "交易记录:" + json);
                JSONObject obj= JSON.parseObject(json);
                int result=obj.getInteger("result");
                String message=obj.getString("message");
                if (result==1)
                {
                    JSONArray array=obj.getJSONArray("list");
                    List<Deal> list=JSON.parseArray(array.toJSONString(),Deal.class);
                    if (page>1)
                    {
                        if (list.size()==0)
                        {
                            isHas=false;
                        }
                    }else if (page==1)
                    {
                        data.clear();
                    }
                    data.addAll(list);
                    if (data.size()==0)
                    {
                        pb_emptyview.setVisibility(View.GONE);
                        tv_empty.setText("暂无数据");
                        tv_empty.setVisibility(View.VISIBLE);
                    }else {
                        empty_view.setVisibility(View.GONE);
                    }
                    adapter = new DealAdapter(data,DealActivity.this,money);
                    lv.setAdapter(adapter);
                }else {
                    pb_emptyview.setVisibility(View.GONE);
                    tv_empty.setText(message);
                    tv_empty.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                pb_emptyview.setVisibility(View.GONE);
                tv_empty.setText("服务器错误："+s);
            }
        });
    }


    @Override
    public void onPullDownRefresh() {
        page=1;
        getDealList();
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onPullUpRefresh() {
        if (!isHas)
        {
            T.show(this,"没有更多数据了");
        }else {
            page++;
            getDealList();
        }
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }


    //listview的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.comment_sure:
                if (TextUtils.isEmpty(PreferenceUtils.loadStr(this,PreferenceUtils.BANK_NAME))){
                    Intent intent=new Intent(this,AddBankActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(this, WhithDrawActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}
