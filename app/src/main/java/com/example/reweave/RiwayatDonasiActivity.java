package com.example.reweave;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reweave.Adapter.RiwayatDonasiAdapter;
import com.example.reweave.Model.Donasi;

import io.realm.Realm;
import io.realm.RealmResults;

public class RiwayatDonasiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RiwayatDonasiAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_donasi);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        ImageView backButton = findViewById(R.id.iv_back);
        backButton.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recycler_riwayat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RealmResults<Donasi> donasiList = realm.where(Donasi.class).findAll();

        adapter = new RiwayatDonasiAdapter(donasiList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}