package com.example.reweave;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reweave.testing.GridSpacingItemDecoration;
import com.example.reweave.testing.PromoItemAdapter;
import com.example.reweave.testing.PromoItem;

import java.util.ArrayList;

public class ChangePoinActivity extends AppCompatActivity {
    // Konstanta untuk penyimpanan data poin pengguna menggunakan SharedPreferences
    private static final String PREF_NAME = "ReweavePrefs";
    private static final String KEY_POINTS = "user_points";
    private static final int DEFAULT_POINTS = 2000;
    private TextView tvPoints;
    private RecyclerView rvPromos;
    private PromoItemAdapter adapter;
    private TextView btnRiwayat, btnTambahPoin;
    private ArrayList<PromoItem> promoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_poin);

        // Inisialisasi komponen antarmuka pengguna
        ImageView ivBack = findViewById(R.id.iv_back);
        tvPoints = findViewById(R.id.tv_point);
        rvPromos = findViewById(R.id.rv_promos);
        btnRiwayat = findViewById(R.id.btn_riwayat);
        btnTambahPoin = findViewById(R.id.btn_tambah_poin);

        // Implementasi tombol kembali untuk menutup aktivitas
        ivBack.setOnClickListener(v -> finish());

        btnRiwayat.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePoinActivity.this, RiwayatPoinActivity.class);
            startActivity(intent);
        });

        btnTambahPoin.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePoinActivity.this, MainUIActivity.class);
            startActivity(intent);
        });

        // Menyiapkan tampilan daftar promo dalam bentuk grid
        setupRecyclerView();

        // Memperbarui tampilan jumlah poin pengguna
        updatePointsDisplay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Memperbarui tampilan poin saat aktivitas dilanjutkan
        updatePointsDisplay();
    }

    private void setupRecyclerView() {
        // Inisialisasi daftar promo dan adapter
        promoList = new ArrayList<>();
        adapter = new PromoItemAdapter(this, promoList);
        
        // Mengatur tata letak grid dengan 2 kolom
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvPromos.setLayoutManager(layoutManager);
        
        // Menambahkan dekorasi jarak antar item
        int spacing = 20;
        rvPromos.addItemDecoration(new GridSpacingItemDecoration(2, spacing, true));
        
        rvPromos.setAdapter(adapter);

        // Menambahkan data promo contoh
        addSamplePromos();

        // Menangani kejadian klik pada item promo
        adapter.setOnItemClickListener(promo -> {
            Intent intent = new Intent(ChangePoinActivity.this, RedeemPoinActivity.class);
            intent.putExtra("promo_title", promo.getTitle());
            intent.putExtra("promo_points", Integer.parseInt(promo.getPoints().replaceAll("[^0-9]", "")));
            intent.putExtra("promo_image", promo.getImageResource());
            startActivity(intent);
        });
    }

    private void addSamplePromos() {
        // Menambahkan data promo sementara untuk pengujian
        promoList.add(new PromoItem(1, "Kode Promo Grab", "200", R.drawable.poin1));
        promoList.add(new PromoItem(2, "Kode Promo Gojek", "200", R.drawable.poin2));
        promoList.add(new PromoItem(3, "Tas Belanja Eco", "300", R.drawable.poin3));
        promoList.add(new PromoItem(4, "Komposter Mini", "400", R.drawable.poin4));
        promoList.add(new PromoItem(5, "Sedotan stainless steel", "200", R.drawable.poin5));
        promoList.add(new PromoItem(6, "Makanan Eco", "100", R.drawable.poin6));
        adapter.notifyDataSetChanged();
    }

    private void updatePointsDisplay() {
        // Memperbarui tampilan jumlah poin pada antarmuka
        int currentPoints = getCurrentPoints();
        tvPoints.setText(currentPoints + " Points");
    }

    // Metode untuk mengambil jumlah poin dari penyimpanan lokal
    public static int getCurrentPoints(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_POINTS, DEFAULT_POINTS);
    }

    public int getCurrentPoints() {
        return getCurrentPoints(this);
    }

    // Metode untuk menyimpan jumlah poin ke penyimpanan lokal
    public static void setPoints(Context context, int points) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_POINTS, points).apply();
    }
} 