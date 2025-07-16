package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailKomunitasActivity extends AppCompatActivity {

    private ImageView imageKomunitas;
    private TextView namaKomunitas, alamat, kontak, jamBuka, listJenisPakaian;
    private Button donasiSekarangButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_komunitas);

        initViews();
        handleIntent();
        handleButtons();
    }

    private void initViews() {
        imageKomunitas = findViewById(R.id.image_komunitas);
        namaKomunitas = findViewById(R.id.text_nama_komunitas);
        alamat = findViewById(R.id.text_alamat);
        kontak = findViewById(R.id.text_kontak);
        jamBuka = findViewById(R.id.text_jam_buka);
        listJenisPakaian = findViewById(R.id.list_jenis_pakaian);
        donasiSekarangButton = findViewById(R.id.button_donasi_sekarang);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String nama = intent.getStringExtra("namaKomunitas");
            String alamatText = intent.getStringExtra("alamat");
            String kontakText = intent.getStringExtra("kontak");
            String jam = intent.getStringExtra("jamBuka");
            String jenisPakaian = intent.getStringExtra("jenisPakaian");

            namaKomunitas.setText(nama != null ? nama : "Nama Komunitas Tidak Ada");
            alamat.setText("Alamat: " + (alamatText != null ? alamatText : "-"));
            kontak.setText("Kontak: " + (kontakText != null ? kontakText : "-"));
            jamBuka.setText("Jam Buka: " + (jam != null ? jam : "-"));
            listJenisPakaian.setText(jenisPakaian != null ? jenisPakaian : "-");
        }
    }

    private void handleButtons() {
        donasiSekarangButton.setOnClickListener(v -> {
            Intent donationIntent = new Intent(DetailKomunitasActivity.this, DonationActivity.class);
            startActivity(donationIntent);
        });
    }
}
