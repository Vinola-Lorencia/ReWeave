package com.example.reweave;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailProdukActivity extends AppCompatActivity {

    ImageView imgProdukDetail;
    TextView txtNamaProdukDetail, txtHargaProdukDetail, txtDetailProduk;
    TextView txtQuantity, txtRating, txtLike;
    Button btnMinus, btnPlus, btnBuyNow;

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        // Inisialisasi view
        imgProdukDetail = findViewById(R.id.imgProdukDetail);
        txtNamaProdukDetail = findViewById(R.id.txtNamaProdukDetail);
        txtHargaProdukDetail = findViewById(R.id.txtHargaProdukDetail);
        txtDetailProduk = findViewById(R.id.txtDetailProduk);
        txtQuantity = findViewById(R.id.txtQuantity);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnBuyNow = findViewById(R.id.btnBuyNow);

        // Tambahkan rating dan like secara dinamis jika ingin
        txtRating = new TextView(this);
        txtLike = new TextView(this);

        // Ambil data dari Intent
        int gambar = getIntent().getIntExtra("gambar", R.drawable.tp5);
        String nama = getIntent().getStringExtra("nama");
        int harga = getIntent().getIntExtra("harga", 0);
        double rating = getIntent().getDoubleExtra("rating", 0.0);
        double like = getIntent().getDoubleExtra("like", 0.0);
        String detail = getIntent().getStringExtra("detail");

        // Set ke tampilan
        imgProdukDetail.setImageResource(gambar);
        txtNamaProdukDetail.setText(nama);
        txtHargaProdukDetail.setText("Rp " + harga);
        txtDetailProduk.setText(detail);

        // Perbarui rating dan like jika kamu mau tampilkan dinamis
        // (bisa tambahkan TextView khusus jika mau ditaruh di layout)

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

        // Aksi tombol Buy Now
        btnBuyNow.setOnClickListener(v -> {
            // Bisa tambahkan aksi checkout atau simpan ke keranjang
            // Contoh:
            // Toast.makeText(this, "Membeli " + quantity + "x " + nama, Toast.LENGTH_SHORT).show();
        });
    }
}
