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

import io.realm.Realm;
import io.realm.RealmResults;
import com.example.reweave.Model.Point;
import com.example.reweave.ui.Donate.DonateFragment;

public class ChangePoinActivity extends AppCompatActivity {
    private TextView tvPoints;
    private RecyclerView rvPromos;
    private PromoItemAdapter adapter;
    private TextView btnRiwayat, btnTambahPoin;
    private ArrayList<PromoItem> promoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_poin);

        Realm.init(this);

        ImageView ivBack = findViewById(R.id.iv_back);
        tvPoints = findViewById(R.id.tv_point);
        rvPromos = findViewById(R.id.rv_promos);
        btnRiwayat = findViewById(R.id.btn_riwayat);
        btnTambahPoin = findViewById(R.id.btn_tambah_poin);

        ivBack.setOnClickListener(v -> finish());

        btnRiwayat.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePoinActivity.this, RiwayatPoinActivity.class);
            startActivity(intent);
        });

        btnTambahPoin.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePoinActivity.this, DonateFragment.class);
            startActivity(intent);
        });

        setupRecyclerView();
        updatePointsDisplay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePointsDisplay();
    }

    private void setupRecyclerView() {
        promoList = new ArrayList<>();
        adapter = new PromoItemAdapter(this, promoList);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvPromos.setLayoutManager(layoutManager);

        int spacing = 20;
        rvPromos.addItemDecoration(new GridSpacingItemDecoration(2, spacing, true));

        rvPromos.setAdapter(adapter);

        addSamplePromos();

        adapter.setOnItemClickListener(promo -> {
            Intent intent = new Intent(ChangePoinActivity.this, RedeemPoinActivity.class);
            intent.putExtra("promo_title", promo.getTitle());
            intent.putExtra("promo_points", Integer.parseInt(promo.getPoints().replaceAll("[^0-9]", "")));
            intent.putExtra("promo_image", promo.getImageResource());
            startActivity(intent);
        });
    }

    private void addSamplePromos() {
        promoList.add(new PromoItem(1, "Kode Promo Grab", "200", R.drawable.poin1));
        promoList.add(new PromoItem(2, "Kode Promo Gojek", "200", R.drawable.poin2));
        promoList.add(new PromoItem(3, "Tas Belanja Eco", "300", R.drawable.poin3));
        promoList.add(new PromoItem(4, "Komposter Mini", "400", R.drawable.poin4));
        promoList.add(new PromoItem(5, "Sedotan stainless steel", "200", R.drawable.poin5));
        promoList.add(new PromoItem(6, "Makanan Eco", "100", R.drawable.poin6));
        adapter.notifyDataSetChanged();
    }

    private void updatePointsDisplay() {
        String email = getUserEmail();
        Realm realm = Realm.getDefaultInstance();
        Point pointData = realm.where(Point.class).equalTo("email", email).findFirst();
        int currentPoints = pointData != null ? pointData.getPoints() : 0;
        tvPoints.setText(currentPoints + " Points");
    }

    private String getUserEmail() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        return prefs.getString("user_email", "");
    }
}
