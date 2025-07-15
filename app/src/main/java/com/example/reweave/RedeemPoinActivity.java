package com.example.reweave;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class RedeemPoinActivity extends AppCompatActivity {
    // Deklarasi variabel komponen antarmuka pengguna
    private TextView tvPoints;
    private TextView tvQuantity;
    private TextView tvPromo;
    private TextView tvTotalPoint;
    private ImageView ivPromo;
    private ImageButton btnMinus;
    private ImageButton btnPlus;
    private AppCompatButton btnRedeem;

    // Variabel untuk menyimpan data promo yang akan ditukarkan
    private int quantity = 1;
    private String promoTitle;
    private int promoPoints;
    private int promoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_poin);

        // Mengambil data promo dari intent
        promoTitle = getIntent().getStringExtra("promo_title");
        promoPoints = getIntent().getIntExtra("promo_points", 0);
        promoImage = getIntent().getIntExtra("promo_image", 0);

        // Inisialisasi komponen dan menyiapkan tampilan
        initializeViews();
        setupClickListeners();
        updateDisplay();
    }

    private void initializeViews() {
        // Menghubungkan variabel dengan komponen pada layout
        tvPoints = findViewById(R.id.tv_point);
        tvQuantity = findViewById(R.id.tv_quantity);
        tvPromo = findViewById(R.id.tv_promo);
        ivPromo = findViewById(R.id.iv_promo);
        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);
        btnRedeem = findViewById(R.id.btn_redeem);
        tvTotalPoint = findViewById(R.id.tv_total_point);

        // Menginisialisasi tampilan dengan data promo
        tvPromo.setText(promoTitle);
        ivPromo.setImageResource(promoImage);
        tvPoints.setText(promoPoints + " Point");
        updatePointsDisplay();
    }

    private void setupClickListeners() {
        // Implementasi tombol kembali
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        // Implementasi tombol pengurangan kuantitas
        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateDisplay();
            }
        });

        // Implementasi tombol penambahan kuantitas
        btnPlus.setOnClickListener(v -> {
            int totalPoints = promoPoints * (quantity + 1);
            int currentPoints = ChangePoinActivity.getCurrentPoints(this);

            // Validasi kecukupan poin
            if (totalPoints <= currentPoints) {
                quantity++;
                updateDisplay();
            } else {
                Toast.makeText(this, "Poin tidak mencukupi", Toast.LENGTH_SHORT).show();
            }
        });

        // Implementasi tombol penukaran poin
        btnRedeem.setOnClickListener(v -> redeemPoints());
    }

    private void updateDisplay() {
        // Memperbarui tampilan kuantitas
        tvQuantity.setText(String.valueOf(quantity));
        updatePointsDisplay();
    }

    private void updatePointsDisplay() {
        // Memperbarui tampilan total poin
        int currentPoints = ChangePoinActivity.getCurrentPoints(this);
        tvTotalPoint.setText(currentPoints + " Points");
    }

    private void redeemPoints() {
        // Menghitung total poin yang diperlukan
        int totalPoints = promoPoints * quantity;
        int currentPoints = ChangePoinActivity.getCurrentPoints(this);

        if (totalPoints <= currentPoints) {
            // Mengurangi poin pengguna setelah penukaran
            int remainingPoints = currentPoints - totalPoints;
            ChangePoinActivity.setPoints(this, remainingPoints);

            // Menampilkan notifikasi keberhasilan
            Toast.makeText(this, "Berhasil menukar " + quantity + " " + promoTitle, Toast.LENGTH_SHORT).show();

            // Menutup aktivitas setelah penukaran berhasil
            finish();
        } else {
            // Menampilkan notifikasi kegagalan
            Toast.makeText(this, "Poin tidak mencukupi", Toast.LENGTH_SHORT).show();
        }
    }
}