package com.minsheng.app.xunchedai.message.adapters;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.loan.bean.Loan;
import com.minsheng.app.xunchedai.message.bean.Message;

import java.util.List;
/**
 * Created by Administrator on 2016/8/15.
 * 消息列表适配器
 */
public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<Message> list;
    public MessageAdapter(List<Message> list, Context context)
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
            convertView = inflater.inflate(R.layout.fragment_msg_item, parent, false);
            holder=new ViewHolder();
            holder.iv_logo= (ImageView) convertView.findViewById(R.id.iv_logo);
            holder.tv_type= (TextView) convertView.findViewById(R.id.tv_type);
            holder.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        switch (list.get(position).getType())
        {
            case 0:
                holder.tv_type.setText("贷款跟进");
                holder.iv_logo.setImageResource(R.drawable.icon_genjing);
                break;
            case 1:
                holder.tv_type.setText("余额变动");
                holder.iv_logo.setImageResource(R.drawable.icon_yue);
                break;
            case 2:
                holder.tv_type.setText("消息通知");
                holder.iv_logo.setImageResource(R.drawable.icon_news);
                break;
        }
        holder.tv_content.setText(list.get(position).getTheme());
        holder.tv_time.setText(list.get(position).getTime());
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
        ImageView iv_logo;
        TextView tv_type;
        TextView tv_time;
        TextView tv_content;
    }
}
