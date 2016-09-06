package com.minsheng.app.xunchedai.loan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.loan.activities.AddLoanActivity;
import com.minsheng.app.xunchedai.loan.adapters.BasicFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 * 贷款申请Fragment
 */
public class LoanFragment extends Fragment implements View.OnClickListener {
    private View convertView;
    private ViewPager vp;
    private TextView tv_vp_left, tv_vp_right;
    private LinearLayout ll_vp_selected;
    private LoanMainFragment leftFragment;
    private LoanDraftFragment rightFragment;
    private TextView tv_title;
    private TextView tv_add;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_loan, container, false);
            initView(convertView);
        }
        ViewGroup viewParent = (ViewGroup) convertView.getParent();
        if (viewParent != null) {
            viewParent.removeView(convertView);
        }
        return convertView;
    }

    //初始化控件
    private void initView(View v) {
        tv_vp_left = (TextView) v.findViewById(R.id.vp_left);
        tv_vp_right  = (TextView) v.findViewById(R.id.vp_right);
        ll_vp_selected = (LinearLayout) v.findViewById(R.id.vp_selected);
        vp = (ViewPager) v.findViewById(R.id.view_pager);

        tv_title= (TextView) v.findViewById(R.id.tv_activity_concat_title);
        tv_add= (TextView) v.findViewById(R.id.comment_sure);


        leftFragment = new LoanMainFragment();
        rightFragment = new LoanDraftFragment();

        fragmentList.add(leftFragment);
        fragmentList.add(rightFragment);

        tv_title.setText("贷款申请");
        tv_add.setText("新增");
        tv_add.setVisibility(View.VISIBLE);
        tv_add.setOnClickListener(this);


        tv_vp_left.setOnClickListener(this);
        tv_vp_right.setOnClickListener(this);

        vp.setAdapter(new BasicFragmentPagerAdapter(getChildFragmentManager(), fragmentList));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ll_vp_selected.setGravity(Gravity.LEFT);
                } else {
                    ll_vp_selected.setGravity(Gravity.RIGHT);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vp_left:
                if (vp.getCurrentItem() == 1) {
                    vp.setCurrentItem(0);
                }
                break;
            case R.id.vp_right:
                if (vp.getCurrentItem() == 0) {
                    vp.setCurrentItem(1);
                }
                break;
            case R.id.comment_sure:
                Intent intent=new Intent(getActivity(), AddLoanActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
