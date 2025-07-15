package com.example.reweave;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import com.example.reweave.Adapter.RedeemHistoryAdapter;
import com.example.reweave.Model.RedeemHistory;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RiwayatPoinActivity extends AppCompatActivity {

    private Realm realm;
    private ListView listView;
    private RedeemHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_poin);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.iv_back);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(RiwayatPoinActivity.this, ChangePoinActivity.class);
            startActivity(intent);
            finish();
        });

        listView = findViewById(R.id.listview);

        loadHistory();
    }

    private void loadHistory() {
        // Ambil email user yang login dari SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        String email = prefs.getString("user_email", "");

        // Ambil data histori penukaran dari Realm berdasarkan email
        RealmResults<RedeemHistory> historyList = realm.where(RedeemHistory.class)
                .equalTo("email", email)
                .findAll();

        // Tampilkan ke ListView pakai adapter custom
        adapter = new RedeemHistoryAdapter(this, historyList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
