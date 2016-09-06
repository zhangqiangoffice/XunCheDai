package com.minsheng.app.xunchedai.mine.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.mine.bean.Deal;
import java.util.List;
/**
 * Created by Administrator on 2016/8/5.
 * 贷款申请的列表适配器
 */
public class DealAdapter extends BaseAdapter {
    private Context context;
    private List<Deal> list;
    private String money;
    public DealAdapter(List<Deal> list, Context context,String money)
    {
        this.list=list;
        this.context=context;
        this.money=money;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_deal_item, parent, false);
            holder=new ViewHolder();
            holder.tv_money= (TextView) convertView.findViewById(R.id.tv_deal_money);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_deal_name);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_deal_date);
            holder.tv_type= (TextView) convertView.findViewById(R.id.tv_deal_type);
            holder.ll_header= (LinearLayout) convertView.findViewById(R.id.ll_deal_header);
            holder.tv_total= (TextView) convertView.findViewById(R.id.tv_deal_total);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        if (position==0)
        {
            holder.ll_header.setVisibility(View.VISIBLE);
            holder.tv_total.setText("￥"+money);
        }

        if (list.get(position).getType()==2)
        {
            holder.tv_type.setText("提现");
            holder.tv_money.setText(" - "+ list.get(position).getMoney());
        }else if (list.get(position).getType()==3){
            holder.tv_type.setText("佣金");
            holder.tv_money.setText(" + "+ list.get(position).getMoney());
        }
        holder.tv_time.setText(list.get(position).getTime());
        holder.tv_name.setText(list.get(position).getName());
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
        TextView tv_type;
        TextView tv_money;
        TextView tv_time;
        TextView tv_name;
        TextView tv_total;
        LinearLayout ll_header;
    }
}
