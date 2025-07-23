package com.example.reweave;

import android.os.Bundle;

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

        // Init Realm
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        // Init RecyclerView
        recyclerView = findViewById(R.id.recycler_riwayat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Ambil data dari Realm
        RealmResults<Donasi> donasiList = realm.where(Donasi.class).findAll();

        // Pasang ke adapter
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