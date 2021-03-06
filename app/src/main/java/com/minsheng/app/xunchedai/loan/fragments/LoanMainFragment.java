package com.minsheng.app.xunchedai.loan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.loan.activities.AddLoanActivity;
import com.minsheng.app.xunchedai.loan.adapters.ApplyAdapter;
import com.minsheng.app.xunchedai.loan.bean.Loan;
import com.minsheng.app.xunchedai.pullrefresh.OnRefreshListener;
import com.minsheng.app.xunchedai.pullrefresh.PullRefreshLayout;
import com.minsheng.app.xunchedai.utils.PreferenceUtils;
import com.minsheng.app.xunchedai.utils.T;
import com.minsheng.app.xunchedai.utils.WebServiceUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 * Fragment
 */
public class LoanMainFragment extends Fragment implements OnRefreshListener,AdapterView.OnItemClickListener {
    private View convertView;
    private PullRefreshLayout mRefreshLayout;
    private ListView lv;

    private View empty_view;
    private TextView tv_empty;
    private ImageView iv_empty;
    private ProgressBar pb_emptyview;
    private ApplyAdapter adapter;
    private List<Loan>data=new ArrayList<>();
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mRefreshLayout.onRefreshComplete();
        }
    };
    private HttpUtils httpUtils;
    private int page=1;
    private boolean isHas=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_loan_main, container, false);
            initView(convertView);
            getLoanList();
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
        getLoanList();
    }

    //初始化控件
    public void initView(View v)
    {
        mRefreshLayout= (PullRefreshLayout) v.findViewById(R.id.refresh_layout);
        lv= (ListView) v.findViewById(R.id.activity_customer_lv);

        empty_view=v.findViewById(R.id.layout_emptyview);
        tv_empty= (TextView) v.findViewById(R.id.layout_tv_emptyview);
        iv_empty= (ImageView) v.findViewById(R.id.layout_iv_emptyview);
        pb_emptyview= (ProgressBar) v.findViewById(R.id.pb_emptyview);

        mRefreshLayout.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);

        httpUtils=new HttpUtils();
    }

    //获取申请列表的方法
    public void getLoanList()
    {
        RequestParams params=new RequestParams();
        params.addBodyParameter("staffid", PreferenceUtils.loadUser(getActivity(),PreferenceUtils.STAFFID));
        params.addBodyParameter("page",page+"");
        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL+WebServiceUrl.LOAN_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                Log.i("123", "json:" + json);
                JSONObject obj= JSON.parseObject(json);
                int result=obj.getInteger("result");
                String message=obj.getString("message");
                if (result==1)
                {
                    JSONArray array=obj.getJSONArray("list");
                    List<Loan> list=JSON.parseArray(array.toJSONString(),Loan.class);
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
                        iv_empty.setVisibility(View.VISIBLE);
                    }else {
                        empty_view.setVisibility(View.GONE);
                    }
                    adapter = new ApplyAdapter(data, getActivity());
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
                tv_empty.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onPullDownRefresh() {
        page=1;
        getLoanList();
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onPullUpRefresh() {
        if (!isHas)
        {
            T.show(getActivity(),"没有更多数据了");
        }else {
            page++;
            getLoanList();
        }
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }


    //listview的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), AddLoanActivity.class);
        intent.putExtra("loan_id", String.valueOf(data.get(position).getId()));
        startActivity(intent);
    }


}
