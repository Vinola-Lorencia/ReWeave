package com.example.reweave;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Adapter.CheckOutAdapter;
import com.example.reweave.Model.Keranjang;
import com.example.reweave.Model.Pemesanan;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class CheckOutCartActivity extends AppCompatActivity {

    Realm realm;
    ListView listView;
    TextView txtOrderPrice, txtDelivery, txtTotal;
    CheckBox cbGift;
    Button btnPayNow;

    int deliveryCost = 18000;
    ArrayList<Keranjang> daftarKeranjang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_cart);

        realm = Realm.getDefaultInstance();

        listView = findViewById(R.id.listViewCheckoutItems);
        txtOrderPrice = findViewById(R.id.txtOrderPrice);
        txtDelivery = findViewById(R.id.txtDelivery);
        txtTotal = findViewById(R.id.txtTotal);
        cbGift = findViewById(R.id.cbGift);
        btnPayNow = findViewById(R.id.btnPayNow);

        daftarKeranjang = new ArrayList<>();
        ArrayList<String> idList = getIntent().getStringArrayListExtra("list_id_keranjang");
        int totalOrder = 0;

        if (idList != null && !idList.isEmpty()) {
            for (String id : idList) {
                Keranjang item = realm.where(Keranjang.class).equalTo("id", id).findFirst();
                if (item != null) {
                    daftarKeranjang.add(realm.copyFromRealm(item));
                    totalOrder += item.getHarga() * item.getKuantitas();
                }
            }
        }

        int totalBayar = totalOrder + deliveryCost;

        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        txtOrderPrice.setText(rupiah.format(totalOrder));
        txtDelivery.setText(rupiah.format(deliveryCost));
        txtTotal.setText(rupiah.format(totalBayar));

        CheckOutAdapter adapter = new CheckOutAdapter(this, daftarKeranjang);
        listView.setAdapter(adapter);

        btnPayNow.setOnClickListener(v -> {
            try {
                realm.executeTransaction(r -> {
                    Pemesanan pemesanan = r.createObject(Pemesanan.class, UUID.randomUUID().toString());
                    pemesanan.setJumlah(totalBayar);
                    pemesanan.setGift(cbGift.isChecked());
                });

                realm.executeTransaction(r -> {
                    RealmResults<Keranjang> semua = r.where(Keranjang.class).findAll();
                    semua.deleteAllFromRealm();
                });

                Toast.makeText(this, "Pembayaran berhasil dilakukan!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Gagal memproses pembayaran: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
