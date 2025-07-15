package com.example.reweave;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Model.Pemesanan;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;

public class CheckOutActivity extends AppCompatActivity {

    TextView txtOrderPrice, txtDelivery, txtTotal, txtProductName, txtProductPrice;
    CheckBox cbGift;
    Button btnPayNow;
    ImageView imgProduct;

    Realm realm;
    int deliveryCost = 18000; // Ongkir tetap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        // Inisialisasi view
        txtOrderPrice = findViewById(R.id.txtOrderPrice);
        txtDelivery = findViewById(R.id.txtDelivery);
        txtTotal = findViewById(R.id.txtTotal);
        cbGift = findViewById(R.id.cbGift);
        btnPayNow = findViewById(R.id.btnPayNow);
        imgProduct = findViewById(R.id.imgProduct);
        txtProductName = findViewById(R.id.txtProductName);
        txtProductPrice = findViewById(R.id.txtProductPrice);

        // Ambil data dari intent
        String namaProduk = getIntent().getStringExtra("nama");
        int hargaProduk = getIntent().getIntExtra("harga", 0);
        int kuantitas = getIntent().getIntExtra("kuantitas", 1);
        int gambarResId = getIntent().getIntExtra("gambarResId", -1);

        int totalOrder = hargaProduk * kuantitas;
        int totalBayar = totalOrder + deliveryCost;

        // Set tampilan
        txtProductName.setText(namaProduk);
        txtProductPrice.setText("Rp " + hargaProduk);
        if (gambarResId != -1) {
            imgProduct.setImageResource(gambarResId);
        }

        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        txtOrderPrice.setText(rupiah.format(totalOrder));
        txtDelivery.setText(rupiah.format(deliveryCost));
        txtTotal.setText(rupiah.format(totalBayar));

        // Proses pembayaran
        btnPayNow.setOnClickListener(v -> {
            realm.executeTransaction(r -> {
                Pemesanan pemesanan = r.createObject(Pemesanan.class, UUID.randomUUID().toString());
                pemesanan.setNamaProduk(namaProduk);
                pemesanan.setHarga(hargaProduk);
                pemesanan.setJumlah(kuantitas);
                pemesanan.setGift(cbGift.isChecked());
                pemesanan.setTotal(totalBayar);
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
