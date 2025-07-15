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

public class CheckOutAdapter extends ArrayAdapter<Keranjang> {

    private Context context;
    private ArrayList<Keranjang> itemList;

    public CheckOutAdapter(Context context, ArrayList<Keranjang> items) {
        super(context, R.layout.layout_item_checkout, items);
        this.context = context;
        this.itemList = items;
    }

    private static class ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtProductQuantity, txtProductPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Keranjang item = getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            // Gunakan layout yang benar
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_checkout, parent, false);
            holder.imgProduct = convertView.findViewById(R.id.imgProduct);
            holder.txtProductName = convertView.findViewById(R.id.txtProductName);
            holder.txtProductQuantity = convertView.findViewById(R.id.txtProductQuantity);
            holder.txtProductPrice = convertView.findViewById(R.id.txtProductPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (item != null) {
            holder.imgProduct.setImageResource(item.getGambar());
            holder.txtProductName.setText(item.getNamaProduk());
            holder.txtProductQuantity.setText("Qty: " + item.getKuantitas());

            int totalHarga = item.getHarga() * item.getKuantitas();
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
            holder.txtProductPrice.setText(formatRupiah.format(totalHarga));
        }

        return convertView;
    }
}
