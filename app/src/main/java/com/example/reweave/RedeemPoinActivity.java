package com.example.reweave;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.reweave.Model.Point;
import com.example.reweave.Model.RedeemHistory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class RedeemPoinActivity extends AppCompatActivity {
    private TextView tvPoints, tvQuantity, tvPromo, tvTotalPoint;
    private ImageView ivPromo;
    private ImageButton btnMinus, btnPlus;
    private AppCompatButton btnRedeem;

    private int quantity = 1;
    private String promoTitle;
    private int promoPoints;
    private int promoImage;

    private Realm realm;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_poin);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userEmail = prefs.getString("user_email", "");

        promoTitle = getIntent().getStringExtra("promo_title");
        promoPoints = getIntent().getIntExtra("promo_points", 0);
        promoImage = getIntent().getIntExtra("promo_image", 0);

        initializeViews();
        setupClickListeners();
        updateDisplay();
    }

    private void initializeViews() {
        tvPoints = findViewById(R.id.tv_point);
        tvQuantity = findViewById(R.id.tv_quantity);
        tvPromo = findViewById(R.id.tv_promo);
        ivPromo = findViewById(R.id.iv_promo);
        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);
        btnRedeem = findViewById(R.id.btn_redeem);
        tvTotalPoint = findViewById(R.id.tv_total_point);

        tvPromo.setText(promoTitle);
        ivPromo.setImageResource(promoImage);
        tvPoints.setText(promoPoints + " Point");
        updatePointsDisplay();
    }

    private void setupClickListeners() {
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateDisplay();
            }
        });

        btnPlus.setOnClickListener(v -> {
            int totalPoints = promoPoints * (quantity + 1);
            int currentPoints = getCurrentPoints();

            if (totalPoints <= currentPoints) {
                quantity++;
                updateDisplay();
            } else {
                Toast.makeText(this, "Poin tidak mencukupi", Toast.LENGTH_SHORT).show();
            }
        });

        btnRedeem.setOnClickListener(v -> redeemPoints());
    }

    private void updateDisplay() {
        tvQuantity.setText(String.valueOf(quantity));
        updatePointsDisplay();
    }

    private void updatePointsDisplay() {
        int currentPoints = getCurrentPoints();
        tvTotalPoint.setText(currentPoints + " Points");
    }

    private int getCurrentPoints() {
        Point point = realm.where(Point.class).equalTo("email", userEmail).findFirst();
        return point != null ? point.getPoints() : 0;
    }

    private void redeemPoints() {
        int totalPoints = promoPoints * quantity;
        int currentPoints = getCurrentPoints();

        if (totalPoints <= currentPoints) {
            realm.executeTransaction(r -> {
                // Kurangi poin
                Point point = r.where(Point.class).equalTo("email", userEmail).findFirst();
                if (point != null) {
                    point.setPoints(point.getPoints() - totalPoints);
                }

                // Simpan ke riwayat
                RedeemHistory history = r.createObject(RedeemHistory.class, System.currentTimeMillis());
                history.setEmail(userEmail);
                history.setTitle(promoTitle);
                history.setQuantity(quantity);
                history.setTotalPoints(totalPoints);

                // Simpan ke notifikasi
                com.example.reweave.Model.PoinNotification notif = r.createObject(
                        com.example.reweave.Model.PoinNotification.class,
                        java.util.UUID.randomUUID().toString()
                );
                notif.setEmail(userEmail);
                notif.setPromoTitle(promoTitle);
                notif.setPointsRedeemed(totalPoints);
                notif.setDate(new java.util.Date());
            });

            // Setelah sukses transaksi Realm
            Toast.makeText(this, "Congrats, You have redeemed " + quantity + "x " + promoTitle, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Poin tidak cukup
            Toast.makeText(this, "Point isn't enough", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
