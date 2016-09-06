package com.minsheng.app.xunchedai.mine.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.mine.bean.Person;

import java.util.List;
/**
 * Created by Administrator on 2016/8/17.
 * 团队的列表适配器
 */
public class TeamAdapter extends BaseAdapter {
    private Context context;
    private List<Person> list;
    private String money;
    public TeamAdapter(List<Person> list, Context context)
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
            convertView = inflater.inflate(R.layout.activity_team_item, parent, false);
            holder=new ViewHolder();
            holder.tv_user_name= (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tv_day_yj= (TextView) convertView.findViewById(R.id.tv_day_yj);
            holder.tv_month_yj= (TextView) convertView.findViewById(R.id.tv_month_yj);
            holder.tv_day_position= (TextView) convertView.findViewById(R.id.tv_day_position);
            holder.tv_user_phone= (TextView) convertView.findViewById(R.id.tv_user_phone);
            holder.tv_total_yj= (TextView) convertView.findViewById(R.id.tv_total_yj);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tv_user_name.setText(list.get(position).getName());
        holder.tv_day_yj.setText(list.get(position).getDay_achievement());
        holder.tv_day_position.setText(list.get(position).getPosition());
        holder.tv_month_yj.setText(list.get(position).getMonth_achievement());
        holder.tv_user_phone.setText(list.get(position).getPhone());
        holder.tv_total_yj.setText(list.get(position).getTotal_achievement());
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
        TextView tv_user_name;
        TextView tv_day_yj;
        TextView tv_day_position;
        TextView tv_month_yj;
        TextView tv_user_phone;
        TextView tv_total_yj;
    }
}
