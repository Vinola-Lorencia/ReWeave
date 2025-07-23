package com.example.reweave.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reweave.Model.PoinNotification;
import com.example.reweave.R;

import java.util.List;

public class PoinNotificationAdapter extends BaseAdapter {

    private Context context;
    private List<PoinNotification> notificationList;
    private LayoutInflater inflater;

    public PoinNotificationAdapter(Context context, List<PoinNotification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView judul, isi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_notif_poin, parent, false);
            holder = new ViewHolder();
            holder.judul = convertView.findViewById(R.id.judul);
            holder.isi = convertView.findViewById(R.id.isi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PoinNotification item = notificationList.get(position);
        if (item.isAddition()) {
            holder.judul.setText("You got " + item.getPointsRedeemed() + " coins");
            holder.isi.setText("Congrats! You've earned " + item.getPointsRedeemed() + " points from " + item.getPromoTitle());
        } else {
            holder.judul.setText("Your coins have been redeemed");
            holder.isi.setText("You used " + item.getPointsRedeemed() + " points for " + item.getPromoTitle());
        }

        return convertView;
    }

}