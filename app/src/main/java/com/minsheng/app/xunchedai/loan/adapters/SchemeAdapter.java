package com.minsheng.app.xunchedai.loan.adapters;

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
 * 精彩阅读文章评论适配器
 */
public class SchemeAdapter extends BaseAdapter {
    private Context context;
    private List<Scheme>list;

    public SchemeAdapter(List<Scheme> list, Context context)
    {
        this.list = list;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Scheme scheme = getItem(position);
        final ViewHolder holder;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_scheme, parent, false);
            holder = new ViewHolder();
            holder.tv_scheme_name = (TextView) convertView.findViewById(R.id.scheme_name);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tv_scheme_name.setText(scheme.getInstallment() + "个月，利率：" + scheme.getInterest() * 100 + "%");
        return convertView;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Scheme getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tv_scheme_name;
    }


}
