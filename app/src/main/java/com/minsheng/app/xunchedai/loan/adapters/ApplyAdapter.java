package com.minsheng.app.xunchedai.loan.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.loan.bean.Loan;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 * 贷款申请的列表适配器
 */
public class ApplyAdapter extends BaseAdapter {
    private Context context;
    private List<Loan> list;
    public ApplyAdapter(List<Loan>list,Context context)
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
            convertView = inflater.inflate(R.layout.fragment_loan_left_item, parent, false);
            holder=new ViewHolder();
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_laon_item_name);
            holder.tv_state= (TextView) convertView.findViewById(R.id.tv_laon_item_state);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
         int state=list.get(position).getState();
         String stateText="";
         holder.tv_name.setText(list.get(position).getName());
        switch (state)
        {
            case 0:
                stateText="待审核";
                holder.tv_state.setTextColor(Color.parseColor("#8bc013"));
                break;
            case 1:
                stateText="审核通过";
                holder.tv_state.setTextColor(Color.parseColor("#8bc013"));
                break;
            case 2:
                stateText="审核驳回";
                holder.tv_state.setTextColor(Color.parseColor("#ffb6a6"));
                break;
            case 3:
                stateText="拒绝申请";
                holder.tv_state.setTextColor(Color.parseColor("#ffb6a6"));
                break;
            case 4:
                stateText="打款完成";
                holder.tv_state.setTextColor(Color.parseColor("#8bc013"));
                break;
            case 5:
                stateText="还款完成";
                holder.tv_state.setTextColor(Color.parseColor("#8bc013"));
                break;
            case 6:
                stateText="逾期未处理";
                holder.tv_state.setTextColor(Color.parseColor("#ffb6a6"));
                break;
            case 7:
                stateText="逾期已处理";
                holder.tv_state.setTextColor(Color.parseColor("#5a8fd0"));
                break;
        }
        holder.tv_state.setText(stateText);
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
        TextView tv_name;
        TextView tv_state;

    }
}
