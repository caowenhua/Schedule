package me.schedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import me.schedule.R;
import me.schedule.bean.ScheduleBean;

/**
 * Created by caowenhua on 2015/11/16.
 */
public class MainListAdapter extends BaseAdapter {

    private List<ScheduleBean> scheduleBeanList;
    private Context context;

    public MainListAdapter(List<ScheduleBean> scheduleBeanList, Context context) {
        this.scheduleBeanList = scheduleBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return scheduleBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main_list, null);
            holder = new ViewHolder();
            holder.rlt = (RelativeLayout) convertView.findViewById(R.id.rlt);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvDetail = (TextView) convertView.findViewById(R.id.tv_detail);
            holder.tvRemark = (TextView) convertView.findViewById(R.id.tv_remark);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(scheduleBeanList.get(position).getName());
        holder.tvDetail.setText(scheduleBeanList.get(position).getDetail());
        holder.tvRemark.setText(scheduleBeanList.get(position).getRemark());

        switch (scheduleBeanList.get(position).getEvent()){
            case 0:
                holder.rlt.setBackgroundResource(R.drawable.bg_text_con);
                break;
            case 1:
                holder.rlt.setBackgroundResource(R.drawable.bg_green_con);
                break;
            case 2:
                holder.rlt.setBackgroundResource(R.drawable.bg_blue_con);
                break;
            case 3:
                holder.rlt.setBackgroundResource(R.drawable.bg_orange_con);
                break;
            case 4:
                holder.rlt.setBackgroundResource(R.drawable.bg_red_con);
                break;
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvDetail;
        TextView tvRemark;
        RelativeLayout rlt;
    }
}
