package com.example.reweave;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Model.Keranjang;
import com.example.reweave.Model.Pemesanan;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class CheckOutActivity extends AppCompatActivity {

    TextView txtOrderPrice, txtDelivery, txtTotal;
    CheckBox cbGift;
    Button btnPayNow;
    ImageView imgProduct;

    Realm realm;
    int deliveryCost = 18000; // ongkir Rp 18.000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        txtOrderPrice = findViewById(R.id.txtOrderPrice);
        txtDelivery = findViewById(R.id.txtDelivery);
        txtTotal = findViewById(R.id.txtTotal);
        cbGift = findViewById(R.id.cbGift);
        btnPayNow = findViewById(R.id.btnPayNow);
        imgProduct = findViewById(R.id.imgProduct); // TAMBAHKAN INI

        // Ambil data dari Intent
        String namaProduk = getIntent().getStringExtra("nama");
        int hargaProduk = getIntent().getIntExtra("harga", 0);
        int kuantitas = getIntent().getIntExtra("kuantitas", 0);
        int gambarResId = getIntent().getIntExtra("gambarResId", -1);

        boolean isBuyNow = (namaProduk != null); // Cek apakah mode BuyNow
        int totalOrder = 0;

        if (isBuyNow) {
            // Hitung total dari satu produk
            totalOrder = hargaProduk * kuantitas;

            // Tampilkan gambar jika ada
            if (gambarResId != -1) {
                imgProduct.setImageResource(gambarResId);
            }
        } else {
            // Hitung total semua isi keranjang
            RealmResults<Keranjang> keranjangList = realm.where(Keranjang.class).findAll();
            for (Keranjang item : keranjangList) {
                totalOrder += item.getHarga() * item.getKuantitas();
            }

            // Jika mode keranjang, kamu bisa menyembunyikan gambar atau ubah layout
            imgProduct.setImageResource(R.drawable.f1); // default image
        }

        int totalBayar = totalOrder + deliveryCost;

        // Format rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        txtOrderPrice.setText(formatRupiah.format(totalOrder));
        txtDelivery.setText(formatRupiah.format(deliveryCost));
        txtTotal.setText(formatRupiah.format(totalBayar));

        btnPayNow.setOnClickListener(v -> {
            realm.executeTransaction(r -> {
                Pemesanan pemesanan = r.createObject(Pemesanan.class, UUID.randomUUID().toString());
                pemesanan.setJumlah(totalBayar);
                pemesanan.setGift(cbGift.isChecked());

                if (isBuyNow) {
                    pemesanan.setNamaProduk(namaProduk);
                    pemesanan.setHarga(hargaProduk);
                    pemesanan.setJumlah(kuantitas);
                }
            });

            Toast.makeText(this, "Pembayaran berhasil!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}
