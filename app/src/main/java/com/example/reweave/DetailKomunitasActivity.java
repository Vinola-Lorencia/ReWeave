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

    private String nama, alamatText; // disimpan sebagai field agar bisa dipakai untuk intent

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
            nama = intent.getStringExtra("namaKomunitas");
            alamatText = intent.getStringExtra("alamat");
            String kontakText = intent.getStringExtra("kontak");
            String jam = intent.getStringExtra("jamBuka");
            String jenisPakaian = intent.getStringExtra("jenisPakaian");
            int imageResId = intent.getIntExtra("imageResId", -1);


            namaKomunitas.setText(nama != null ? nama : "No Community Name");
            alamat.setText("Address: " + (alamatText != null ? alamatText : "-"));
            kontak.setText("Contact: " + (kontakText != null ? kontakText : "-"));
            jamBuka.setText("Opening Hours: " + (jam != null ? jam : "-"));
            listJenisPakaian.setText(jenisPakaian != null ? jenisPakaian : "-");

            if (imageResId != -1) {
                imageKomunitas.setImageResource(imageResId);
            }
        }
    }

    private void handleButtons() {
        donasiSekarangButton.setOnClickListener(v -> {
            Intent donationIntent = new Intent(DetailKomunitasActivity.this, DonationActivity.class);
            donationIntent.putExtra("community_name", nama);
            donationIntent.putExtra("community_address", alamatText);
            startActivity(donationIntent);
        });
    }
}
