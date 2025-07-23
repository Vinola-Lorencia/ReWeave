package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Adapter.KeranjangAdapter;
import com.example.reweave.Model.Keranjang;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class KeranjangActivity extends AppCompatActivity {

    ListView listViewCart;
    Button btnProceed;
    ArrayList<Keranjang> listKeranjang;
    KeranjangAdapter keranjangAdapter;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        listViewCart = findViewById(R.id.listViewCart);
        btnProceed = findViewById(R.id.btnProceed);

        RealmResults<Keranjang> results = realm.where(Keranjang.class).findAll();
        listKeranjang = new ArrayList<>(realm.copyFromRealm(results));

        keranjangAdapter = new KeranjangAdapter(this, listKeranjang);
        listViewCart.setAdapter(keranjangAdapter);

        btnProceed.setOnClickListener(v -> {
            if (listKeranjang.isEmpty()) {
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                return;
            }

            int totalHarga = 0;
            ArrayList<String> listIdKeranjang = new ArrayList<>();

            for (Keranjang k : listKeranjang) {
                totalHarga += k.getHarga() * k.getKuantitas();
                listIdKeranjang.add(k.getId()); // hanya kirim ID
            }

            Intent intent = new Intent(KeranjangActivity.this, CheckOutCartActivity.class);
            intent.putStringArrayListExtra("list_id_keranjang", listIdKeranjang);
            intent.putExtra("total", totalHarga);
            startActivity(intent);

            // Keranjang tidak langsung dihapus, tunggu pembayaran
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
