package com.example.reweave.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reweave.Model.FlashSale;
import com.example.reweave.R;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class FlashSaleAdapter extends ArrayAdapter<FlashSale> {
    private ArrayList<FlashSale> FlashSaleArrayList;
    Context context;

    public FlashSaleAdapter(ArrayList<FlashSale> FlashSaleArrayList, Context context){
        super(context, R.layout.layout_flashsale, FlashSaleArrayList);
        this.context = context;
    }
    private static class MyViewHolder {
        ImageView imvProduk;
        TextView txvnamaProdukFlashSale, txvDetail, harga_awal, harga_diskon;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        FlashSale flashsale = getItem(position);
        final View result;
        MyViewHolder myViewHolder;

        if(convertView ==null) {
            myViewHolder = new MyViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_flashsale, parent, false);

            myViewHolder.imvProduk = (ImageView) convertView.findViewById(R.id.imvProduk);
            myViewHolder.txvnamaProdukFlashSale = (TextView) convertView.findViewById(R.id.txvNamaProdukFlashSale);
            myViewHolder.txvDetail = (TextView) convertView.findViewById(R.id.txvDetail);
            myViewHolder.harga_awal = (TextView) convertView.findViewById(R.id.harga_awal);
            myViewHolder.harga_diskon = (TextView) convertView.findViewById(R.id.harga_diskon);

            convertView.setTag(myViewHolder);
        }else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        result = convertView;
        myViewHolder.imvProduk.setImageResource(flashsale.getGambarProduk());
        myViewHolder.txvnamaProdukFlashSale.setText( flashsale.getNamaBarangFlashSale());
        myViewHolder.txvDetail.setText(flashsale.getDetail());
        myViewHolder.harga_awal.setText(flashsale.getHargaAwal());
        myViewHolder.harga_diskon.setText(flashsale.getHargaDiskon());
        return result;
    }
}
