package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailKomunitasActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ImageView imageKomunitas;
    private TextView namaKomunitas, alamat, kontak, jamBuka, listJenisPakaian;
    private Button donasiSekarangButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_komunitas);

        backButton = findViewById(R.id.button_back);
        imageKomunitas = findViewById(R.id.image_komunitas);
        namaKomunitas = findViewById(R.id.text_nama_komunitas);
        alamat = findViewById(R.id.text_alamat);
        kontak = findViewById(R.id.text_kontak);
        jamBuka = findViewById(R.id.text_jam_buka);
        listJenisPakaian = findViewById(R.id.list_jenis_pakaian);
        donasiSekarangButton = findViewById(R.id.button_donasi_sekarang);

        backButton.setOnClickListener(view -> finish());

        Intent intent = getIntent();
        if (intent != null) {
            namaKomunitas.setText(intent.getStringExtra("namaKomunitas"));
            alamat.setText("Alamat: " + intent.getStringExtra("alamat"));
            kontak.setText("Kontak: " + intent.getStringExtra("kontak"));
            jamBuka.setText("Jam Buka: " + intent.getStringExtra("jamBuka"));
            listJenisPakaian.setText(intent.getStringExtra("jenisPakaian"));
        }

        donasiSekarangButton.setOnClickListener(v ->
                Toast.makeText(this, "Menuju halaman donasi...", Toast.LENGTH_SHORT).show()
        );
    }
}