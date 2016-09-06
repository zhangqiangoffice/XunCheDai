package com.minsheng.app.xunchedai.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.home.bean.Scheme;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 * 贷款试算方案的列表适配器
 */
public class SchemeListAdapter extends BaseAdapter {
    private Context context;
    private List<Scheme> list;
    private double sum;
    public SchemeListAdapter(List<Scheme> list, Context context, double sum)
    {
        this.list = list;
        this.context=context;
        this.sum = sum;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_scheme_list, parent, false);
            holder=new ViewHolder();
            holder.tv_installment_item = (TextView) convertView.findViewById(R.id.installment_item);
            holder.tv_installment_capital = (TextView) convertView.findViewById(R.id.installment_capital);
            holder.tv_installment_interest = (TextView) convertView.findViewById(R.id.installment_interest);
            holder.tv_installment_sum = (TextView) convertView.findViewById(R.id.installment_sum);

            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        Scheme scheme = list.get(position);
        holder.tv_installment_item.setText(scheme.getInstallment() + "期");
        holder.tv_installment_capital.setText("￥" + String.format("%.2f",scheme.countCapital(sum)));
        holder.tv_installment_interest.setText("￥" + String.format("%.2f", scheme.countInterest(sum)));
        holder.tv_installment_sum.setText("￥" + String.format("%.2f", scheme.countTotal(sum)));
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
        TextView tv_installment_item, tv_installment_capital, tv_installment_interest, tv_installment_sum;

    }
}
