package com.example.reweave.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reweave.Model.Donasi;
import com.example.reweave.R;

import java.util.List;

public class RiwayatDonasiAdapter extends RecyclerView.Adapter<RiwayatDonasiAdapter.ViewHolder> {

    private final List<Donasi> donasiList;

    public RiwayatDonasiAdapter(List<Donasi> donasiList) {
        this.donasiList = donasiList;
    }

    @NonNull
    @Override
    public RiwayatDonasiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_riwayat_donasi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatDonasiAdapter.ViewHolder holder, int position) {
        Donasi donasi = donasiList.get(position);

        holder.tvBarang.setText(donasi.getBrand());
        holder.tvTanggal.setText(donasi.getCallTime()); // atau bisa nanti pakai date
        holder.tvStatus.setText("Dikirim"); // kamu bisa nanti pakai field status kalau ditambahkan
        holder.tvPoint.setText("+ 100 Poin"); // ini juga bisa pakai field poin kalau mau dinamis
    }

    @Override
    public int getItemCount() {
        return donasiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBarang, tvTanggal, tvStatus, tvPoint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBarang = itemView.findViewById(R.id.tv_barang);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvPoint = itemView.findViewById(R.id.tv_point);
        }
    }
}