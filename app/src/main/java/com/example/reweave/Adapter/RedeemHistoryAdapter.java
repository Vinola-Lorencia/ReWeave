package com.example.reweave.Adapter;

import android.content.Context;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reweave.Model.RedeemHistory;
import com.example.reweave.R;

import java.util.List;

public class RedeemHistoryAdapter extends BaseAdapter {
    private Context context;
    private List<RedeemHistory> list;

    public RedeemHistoryAdapter(Context context, List<RedeemHistory> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_historipoint, parent, false);
        }

        RedeemHistory item = list.get(i);

        TextView berita = view.findViewById(R.id.berita);
        TextView acara = view.findViewById(R.id.acara);
        ImageView icon = view.findViewById(R.id.imvpoint);

        berita.setText(item.getTitle());
        acara.setText("Used " + item.getTotalPoints() + " points for " + item.getQuantity() + " item(s)");
        icon.setImageResource(R.drawable.points); // Gambar tetap

        return view;
    }
}
