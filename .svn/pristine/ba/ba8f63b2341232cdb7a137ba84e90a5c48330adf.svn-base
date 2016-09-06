package com.minsheng.app.xunchedai.loan.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.loan.bean.Loan;
import com.minsheng.app.xunchedai.utils.DBManager;

import java.util.List;
/**
 * Created by Administrator on 2016/8/18.
 * 未提交贷款申请的列表适配器
 */
public class ApplyRightAdapter extends BaseAdapter {
    private Context context;
    private List<Loan> list;
    public ApplyRightAdapter(List<Loan> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_loan_right_item, parent, false);
            holder=new ViewHolder();
            holder.tv_date= (TextView) convertView.findViewById(R.id.tv_laon_item_date);
            holder.tv_wanzhengdu= (TextView) convertView.findViewById(R.id.tv_wanzhengdu);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tv_date.setText(list.get(position).getEdit_date());
        double result=Double.valueOf(DBManager.percent(list.get(position)));
        if (result>0&&result<=10)
        {
            holder.tv_wanzhengdu.setTextColor(ContextCompat.getColor(context, R.color.txt_red));
        }else if (result>10&&result<=30)
        {
            holder.tv_wanzhengdu.setTextColor(ContextCompat.getColor(context, R.color.txt_orange));
        }else if (result>30&&result<=50)
        {
            holder.tv_wanzhengdu.setTextColor(ContextCompat.getColor(context, R.color.txt_yellow));
        }else if (result>50&&result<=70){
            holder.tv_wanzhengdu.setTextColor(ContextCompat.getColor(context, R.color.txt_cheng));
        }else {
            holder.tv_wanzhengdu.setTextColor(ContextCompat.getColor(context, R.color.qing));
        }
        holder.tv_wanzhengdu.setText(DBManager.percent(list.get(position))+"%");
        return convertView;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tv_date;
        TextView tv_wanzhengdu;
    }
}
