package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Model.Keranjang;

import java.util.UUID;

import io.realm.Realm;

public class DetailProdukActivity extends AppCompatActivity {

    ImageView imgProdukDetail, btnKeranjang;
    TextView txtNamaProdukDetail, txtHargaProdukDetail, txtDetailProduk;
    TextView txtQuantity;
    Button btnMinus, btnPlus, btnBuyNow;

    int quantity = 1;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        // Inisialisasi Realm
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        // Inisialisasi view
        imgProdukDetail = findViewById(R.id.imgProdukDetail);
        txtNamaProdukDetail = findViewById(R.id.txtNamaProdukDetail);
        txtHargaProdukDetail = findViewById(R.id.txtHargaProdukDetail);
        txtDetailProduk = findViewById(R.id.txtDetailProduk);
        txtQuantity = findViewById(R.id.txtQuantity);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnKeranjang = findViewById(R.id.btnkeranjang);

        // Ambil data dari Intent
        int gambar = getIntent().getIntExtra("gambar", R.drawable.tp5);
        String nama = getIntent().getStringExtra("nama");
        int harga = getIntent().getIntExtra("harga", 0);
        String detail = getIntent().getStringExtra("detail");

        // Set ke tampilan
        imgProdukDetail.setImageResource(gambar);
        txtNamaProdukDetail.setText(nama);
        txtHargaProdukDetail.setText("Rp " + harga);
        txtDetailProduk.setText(detail);
        txtQuantity.setText(String.valueOf(quantity));

        // Aksi tombol plus
        btnPlus.setOnClickListener(v -> {
            quantity++;
            txtQuantity.setText(String.valueOf(quantity));
        });

        // Aksi tombol minus
        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                txtQuantity.setText(String.valueOf(quantity));
            }
        });

        // Aksi tombol Buy Now => Ke halaman checkout
        btnBuyNow.setOnClickListener(v -> {
            Intent intent = new Intent(DetailProdukActivity.this, CheckOutActivity.class); // FIX: Nama class CheckoutActivity harus sesuai nama file .java
            intent.putExtra("nama", nama);
            intent.putExtra("harga", harga);
            intent.putExtra("gambarResId", gambar);
            intent.putExtra("kuantitas", quantity);
            startActivity(intent);
        });

        // Aksi tombol Tambahkan ke Keranjang
        btnKeranjang.setOnClickListener(v -> {
            realm.executeTransaction(r -> {
                Keranjang keranjang = r.createObject(Keranjang.class, UUID.randomUUID().toString());
                keranjang.setNamaProduk(nama);
                keranjang.setHarga(harga);
                keranjang.setGambar(gambar);
                keranjang.setKuantitas(quantity);
            });

            Toast.makeText(this, "Ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();

            // Lanjut ke halaman keranjang
            Intent intent = new Intent(DetailProdukActivity.this, KeranjangActivity.class);
            startActivity(intent);
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
