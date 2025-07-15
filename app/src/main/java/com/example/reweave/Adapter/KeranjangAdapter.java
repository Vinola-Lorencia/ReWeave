package com.example.reweave.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reweave.Model.Keranjang;
import com.example.reweave.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;

public class KeranjangAdapter extends ArrayAdapter<Keranjang> {

    private Context context;
    private ArrayList<Keranjang> keranjangList;
    private Realm realm;

    public KeranjangAdapter(Context context, ArrayList<Keranjang> keranjangList) {
        super(context, R.layout.layout_cart_item, keranjangList);
        this.context = context;
        this.keranjangList = keranjangList;
        realm = Realm.getDefaultInstance();
    }

    private static class ViewHolder {
        ImageView imgProduct, btnDelete;
        TextView txtName, txtPrice, txtQuantity, btnPlus, btnMinus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Keranjang item = getItem(position);
        final View result;
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_cart_item, parent, false);

            holder.imgProduct = convertView.findViewById(R.id.imgProduct);
            holder.txtName = convertView.findViewById(R.id.txtName);
            holder.txtPrice = convertView.findViewById(R.id.txtPrice);
            holder.txtQuantity = convertView.findViewById(R.id.txtQuantity);
            holder.btnPlus = convertView.findViewById(R.id.btnPlus);
            holder.btnMinus = convertView.findViewById(R.id.btnMinus);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        result = convertView;

        // Set data ke tampilan
        holder.imgProduct.setImageResource(item.getGambar());
        holder.txtName.setText(item.getNamaProduk());

        // Hitung total harga berdasarkan kuantitas
        int totalHarga = item.getHarga() * item.getKuantitas();

        // Format harga (contoh: Rp 15.000)
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        holder.txtPrice.setText(formatRupiah.format(totalHarga));

        holder.txtQuantity.setText(String.valueOf(item.getKuantitas()));

        // Tombol tambah kuantitas
        holder.btnPlus.setOnClickListener(v -> {
            realm.executeTransaction(r -> item.setKuantitas(item.getKuantitas() + 1));
            notifyDataSetChanged();
        });

        // Tombol kurangi kuantitas
        holder.btnMinus.setOnClickListener(v -> {
            if (item.getKuantitas() > 1) {
                realm.executeTransaction(r -> item.setKuantitas(item.getKuantitas() - 1));
                notifyDataSetChanged();
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            // Hapus dari Realm (jika object valid Realm, tapi harus hati-hati)
            realm.executeTransaction(r -> {
                Keranjang obj = r.where(Keranjang.class).equalTo("id", item.getId()).findFirst();
                if (obj != null && obj.isValid()) {
                    obj.deleteFromRealm();
                }
            });

            // Hapus dari list yang ditampilkan
            keranjangList.remove(position);
            notifyDataSetChanged();
        });


        return result;
    }
}
