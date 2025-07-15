//package com.example.reweave.Adapter;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.reweave.Model.Produk;
//import com.example.reweave.R;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import java.util.ArrayList;
//
//public class ProdukAdapter extends ArrayAdapter<Produk> {
//
//    private final Context context;
//    private final ArrayList<Produk> allProdukList;    // semua data
//    private ArrayList<Produk> filteredProdukList;     // data yang sedang ditampilkan
//
//    public ProdukAdapter(Context context, ArrayList<Produk> produkList) {
//        super(context, R.layout.layout_produk, produkList);
//        this.context = context;
//        this.allProdukList = new ArrayList<>(produkList);
//        this.filteredProdukList = new ArrayList<>(produkList);
//    }
//
//    @Override
//    public int getCount() {
//        return filteredProdukList.size();
//    }
//
//    @Nullable
//    @Override
//    public Produk getItem(int position) {
//        return filteredProdukList.get(position);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Produk produk = getItem(position);
//        ViewHolder holder;
//
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.layout_produk, parent, false);
//            holder.imgProduk = convertView.findViewById(R.id.imgProduk);
//            holder.txtNamaProduk = convertView.findViewById(R.id.txtNamaProduk);
//            holder.txtHargaProduk = convertView.findViewById(R.id.txtHargaProduk);
//            holder.imgKeranjang = convertView.findViewById(R.id.imgKeranjang);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        if (produk != null) {
//            holder.imgProduk.setImageResource(produk.getGambar());
//            holder.txtNamaProduk.setText(produk.getNamaProduk());
//
//            if (produk.isOnSale()) {
//                holder.txtHargaProduk.setText("Diskon: Rp" + produk.getHarga());
//                holder.txtHargaProduk.setTextColor(Color.RED);
//            } else {
//                holder.txtHargaProduk.setText("Rp" + produk.getHarga());
//                holder.txtHargaProduk.setTextColor(Color.parseColor("#04AD4E"));
//            }
//
//            holder.imgKeranjang.setOnClickListener(v ->
//                    Toast.makeText(context, "Tambah ke keranjang: " + produk.getNamaProduk(), Toast.LENGTH_SHORT).show()
//            );
//        }
//
//        return convertView;
//    }
//
//    private static class ViewHolder {
//        ImageView imgProduk, imgKeranjang;
//        TextView txtNamaProduk, txtHargaProduk;
//    }
//
//    // 🔍 Tampilkan hanya produk yang onSale = true
//    public void filterOnSaleOnly() {
//        filteredProdukList.clear();
//        for (Produk p : allProdukList) {
//            if (p.isOnSale()) {
//                filteredProdukList.add(p);
//            }
//        }
//        notifyDataSetChanged();
//    }
//
//    // 🔄 Tampilkan semua kembali
//    public void resetFilter() {
//        filteredProdukList.clear();
//        filteredProdukList.addAll(allProdukList);
//        notifyDataSetChanged();
//    }
//
//}
package com.example.reweave.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reweave.Model.Produk;
import com.example.reweave.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProdukAdapter extends ArrayAdapter<Produk> {
    private final Context context;
    private final ArrayList<Produk> allProdukList;
    private ArrayList<Produk> filteredProdukList;

    public ProdukAdapter(Context context, ArrayList<Produk> produkList) {
        super(context, R.layout.layout_produk, produkList);
        this.context = context;
        this.allProdukList = new ArrayList<>(produkList);
        this.filteredProdukList = new ArrayList<>(produkList);
    }

    @Override
    public int getCount() {
        return filteredProdukList.size();
    }

    @Nullable
    @Override
    public Produk getItem(int position) {
        return filteredProdukList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Produk produk = getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_produk, parent, false);
            holder.imgProduk = convertView.findViewById(R.id.imgProduk);
            holder.txtNamaProduk = convertView.findViewById(R.id.txtNamaProduk);
            holder.txtHargaProduk = convertView.findViewById(R.id.txtHargaProduk);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (produk != null) {
            holder.imgProduk.setImageResource(produk.getGambar());
            holder.txtNamaProduk.setText(produk.getNamaProduk());

            if (produk.isOnSale()) {
                holder.txtHargaProduk.setText("On Sale: Rp" + produk.getHarga());
                holder.txtHargaProduk.setTextColor(Color.RED);
            } else {
                holder.txtHargaProduk.setText("Rp" + produk.getHarga());
                holder.txtHargaProduk.setTextColor(Color.parseColor("#04AD4E"));
            }



        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgProduk;
        TextView txtNamaProduk, txtHargaProduk;
    }

    public void filterOnSaleOnly() {
        filteredProdukList.clear();
        for (Produk p : allProdukList) {
            if (p.isOnSale()) {
                filteredProdukList.add(p);
            }
        }
        notifyDataSetChanged();
    }

    public void resetFilter() {
        filteredProdukList.clear();
        filteredProdukList.addAll(allProdukList);
        notifyDataSetChanged();
    }

    public void filterByKategori(String kategori) {
        filteredProdukList.clear();
        for (Produk p : allProdukList) {
            if (p.getKategori() != null && p.getKategori().equalsIgnoreCase(kategori)) {

                filteredProdukList.add(p);
            }
        }
        notifyDataSetChanged();
    }
}

