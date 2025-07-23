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

        // Tampilkan nama komunitas dan poin
        holder.tvNamaKomunitas.setText(donasi.getKomunitas());
        holder.tvTambahPoin.setText("+ " + donasi.getPoint() + " Poin");
    }

    @Override
    public int getItemCount() {
        return donasiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKomunitas, tvTambahPoin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKomunitas = itemView.findViewById(R.id.namaKomunitas);
            tvTambahPoin = itemView.findViewById(R.id.tambahPoin);
        }
    }
}
