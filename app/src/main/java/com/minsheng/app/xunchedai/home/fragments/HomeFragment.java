package com.minsheng.app.xunchedai.home.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.base.whell.TimePopupWindow;
import com.minsheng.app.xunchedai.home.adapters.SchemeListAdapter;
import com.minsheng.app.xunchedai.home.bean.Scheme;
import com.minsheng.app.xunchedai.loan.activities.AddLoanActivity;
import com.minsheng.app.xunchedai.utils.APIUtils;
import com.minsheng.app.xunchedai.utils.LoanUtils;
import com.minsheng.app.xunchedai.utils.PopupWindowUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 * 首页主页Fragment
 */
public class HomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{

    private View convertView;
    private TextView tv_title, tv_installment_date, tv_loan;
    private Button btn_count;
    private LinearLayout ll_count_result;
    private List<Scheme> list_scheme;
    private ListView lv_schemes;
    private SchemeListAdapter adapter;
    private EditText et_installment_sum;
    private Double sum;
    private TimePopupWindow pwTime;
    private String str_loan, str_sum, str_date;
    private ImageView iv_bg_car;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_home_main, container, false);
            initView(convertView);
        }
        ViewGroup viewParent = (ViewGroup) convertView.getParent();
        if (viewParent != null) {
            viewParent.removeView(convertView);
        }
        return convertView;
    }

    //初始化控件
    public void initView(View v)
    {
        tv_title = (TextView) v.findViewById(R.id.tv_activity_concat_title);
        tv_loan = (TextView) v.findViewById(R.id.loan);
        btn_count = (Button) v.findViewById(R.id.count);
        ll_count_result = (LinearLayout) v.findViewById(R.id.count_result);
        lv_schemes = (ListView) v.findViewById(R.id.schemes);
        et_installment_sum = (EditText) v.findViewById(R.id.installment_sum);
        tv_installment_date = (TextView) v.findViewById(R.id.installment_date);
        iv_bg_car = (ImageView) v.findViewById(R.id.bg_car);
        list_scheme = new ArrayList<>();
        pwTime = new TimePopupWindow(getActivity(), TimePopupWindow.Type.YEAR_MONTH_DAY, getActivity());

        tv_title.setText("主页");

        APIUtils.getScheme(getActivity());

        //时间选择后回
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tv_installment_date.setText(getTime(date));
            }
        });

        btn_count.setOnClickListener(this);
        tv_installment_date.setOnClickListener(this);
    }

    //时间转换
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    //检查数据有效性
    public boolean checkData() {
        str_sum = et_installment_sum.getText().toString();
        str_date = tv_installment_date.getText().toString();
        if (TextUtils.isEmpty(str_sum) || "请选择保险起期".equals(str_date)) {
            return false;
        } else {
            sum = Double.parseDouble(str_sum);
            if (sum > 0) {
                str_sum = String.format("%.2f", sum);
                str_loan = LoanUtils.count(str_sum, str_date);

                et_installment_sum.setText(str_sum);
                return true;
            } else {
                et_installment_sum.setText("");
                tv_installment_date.setText("请选择保险起期");
                return false;
            }
        }
    }

    //试算贷款方案并显示
    public void countScheme() {
        list_scheme = APIUtils.list_scheme;
        int len = list_scheme.size();
        tv_loan.setText(str_loan);
        if (len > 0) {
            adapter = new SchemeListAdapter(list_scheme, getActivity() , Double.parseDouble(str_loan));
            lv_schemes.setAdapter(adapter);
            lv_schemes.setOnItemClickListener(this);
            ll_count_result.setVisibility(View.VISIBLE);
            iv_bg_car.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            //点击登录按钮事件
            case R.id.count:
                PopupWindowUtils.closeKeyBoard(getActivity());
                ll_count_result.setVisibility(View.GONE);
                iv_bg_car.setVisibility(View.VISIBLE);
                if (checkData()) {
                    countScheme();
                }
                break;
            case R.id.installment_date:
                pwTime.showAtLocation(getActivity(),v, new Date());
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //选择不同的分期方案
        Intent intent = new Intent(getActivity(), AddLoanActivity.class);
        Bundle bundle = new Bundle();
        Scheme scheme = list_scheme.get(position);
        String str_installment = String.valueOf(scheme.getInstallment());
        bundle.putString("installment", str_installment);
        bundle.putString("insurance", str_sum);
        bundle.putString("date", str_date);
        bundle.putString("loan", str_loan);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
