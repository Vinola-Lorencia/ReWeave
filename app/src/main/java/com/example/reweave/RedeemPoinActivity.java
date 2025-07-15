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

        // Init Realm
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        // Get user email from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userEmail = prefs.getString("user_email", "");

        // Ambil data dari intent
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
                Point point = r.where(Point.class).equalTo("email", userEmail).findFirst();
                if (point != null) {
                    point.setPoints(point.getPoints() - totalPoints);
                }
            });

            Toast.makeText(this, "Congrats, You have redeem the point " + quantity + " " + promoTitle, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Point isn't enough", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
