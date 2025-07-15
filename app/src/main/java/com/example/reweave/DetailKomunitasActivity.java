package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailKomunitasActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ImageView imageKomunitas;
    private TextView namaKomunitas, alamat, kontak, jamBuka, listJenisPakaian;
    private Button donasiSekarangButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_komunitas);

        // Inisialisasi komponen UI
        backButton = findViewById(R.id.button_back);
        imageKomunitas = findViewById(R.id.image_komunitas);
        namaKomunitas = findViewById(R.id.text_nama_komunitas);
        alamat = findViewById(R.id.text_alamat);
        kontak = findViewById(R.id.text_kontak);
        jamBuka = findViewById(R.id.text_jam_buka);
        listJenisPakaian = findViewById(R.id.list_jenis_pakaian);
        donasiSekarangButton = findViewById(R.id.button_donasi_sekarang);

        // Tombol kembali
        backButton.setOnClickListener(view -> finish());

        // Ambil data dari Intent
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            namaKomunitas.setText(receivedIntent.getStringExtra("namaKomunitas"));
            alamat.setText("Alamat: " + receivedIntent.getStringExtra("alamat"));
            kontak.setText("Kontak: " + receivedIntent.getStringExtra("kontak"));
            jamBuka.setText("Jam Buka: " + receivedIntent.getStringExtra("jamBuka"));
            listJenisPakaian.setText(receivedIntent.getStringExtra("jenisPakaian"));
        }

        // Aksi tombol "Donasi Sekarang"
        donasiSekarangButton.setOnClickListener(v -> {
            Intent donationIntent = new Intent(DetailKomunitasActivity.this, DonationActivity.class);
            startActivity(donationIntent);
        });
    }
}
