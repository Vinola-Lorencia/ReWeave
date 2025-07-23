package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reweave.Adapter.ProdukAdapter;
import com.example.reweave.Model.Produk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmResults;

public class TampilanProdukActivity extends AppCompatActivity {

    GridView gridViewProduk;
    EditText edtSearchProduk;
    Button btnFilterOnSale, btnFilterPrice;
    ArrayList<Produk> ProdukArrayList;
    ProdukAdapter adapter;

    private boolean isFilterPriceActive = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tampilan_produk);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi UI
        gridViewProduk = findViewById(R.id.gridViewProduk);
        edtSearchProduk = findViewById(R.id.edtSearchProduk);
        btnFilterOnSale = findViewById(R.id.btnFilterOnSale);
        btnFilterPrice = findViewById(R.id.btnFilterPrice);





        tambahDataDummyProduk();
        // Ambil data produk dari Realm
        ProdukArrayList = getAllProduk();

        // Inisialisasi adapter
        adapter = new ProdukAdapter(this, ProdukArrayList);
        gridViewProduk.setAdapter(adapter);

        String kategori = getIntent().getStringExtra("kategori");
        if (kategori != null && !kategori.isEmpty()) {
            adapter.filterByKategori(kategori);
        }

        // Filter hanya produk diskon
        btnFilterOnSale.setOnClickListener(v -> {
            adapter.filterOnSaleOnly();

            // Ubah warna tombol
            btnFilterOnSale.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
            btnFilterPrice.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));

            // Reset toggle harga
            isFilterPriceActive = false;
        });


        // Sortir berdasarkan harga
        btnFilterPrice.setOnClickListener(v -> {
            if (!isFilterPriceActive) {
                // Sortir dari harga termurah ke termahal
                Collections.sort(ProdukArrayList, Comparator.comparingInt(Produk::getHarga));
                adapter = new ProdukAdapter(this, ProdukArrayList);
                gridViewProduk.setAdapter(adapter);

                btnFilterPrice.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
                btnFilterOnSale.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));

                isFilterPriceActive = true;
            } else {
                // Reset ke data awal (tanpa urutan)
                ProdukArrayList = getAllProduk();
                adapter = new ProdukAdapter(this, ProdukArrayList);
                gridViewProduk.setAdapter(adapter);

                btnFilterPrice.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                isFilterPriceActive = false;
            }
        });



        // Cari berdasarkan nama produk
        edtSearchProduk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterByName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Klik item produk
        gridViewProduk.setOnItemClickListener((parent, view, position, id) -> {
            Produk p = adapter.getItem(position);
            if (p != null) {
                // Kirim data produk ke DetailProdukActivity
                Intent intent = new Intent(TampilanProdukActivity.this, DetailProdukActivity.class);
                intent.putExtra("gambar", p.getGambar());
                intent.putExtra("nama", p.getNamaProduk());
                intent.putExtra("harga", p.getHarga());
                intent.putExtra("rating", p.getRating());
                intent.putExtra("like", p.getLike());
                intent.putExtra("detail", p.getDetail());

                startActivity(intent);
            }
        });

    }

    // Ambil semua produk dari Realm
    private ArrayList<Produk> getAllProduk() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Produk> results = realm.where(Produk.class).findAll();
        ArrayList<Produk> list = new ArrayList<>(realm.copyFromRealm(results));
        realm.close();
        return list;
    }

    // 🔍 Fitur pencarian nama produk
    private void filterByName(String keyword) {
        if (keyword.isEmpty()) {
            adapter.resetFilter();
            return;
        }

        ArrayList<Produk> filteredList = new ArrayList<>();
        for (Produk p : ProdukArrayList) {
            if (p.getNamaProduk().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(p);
            }
        }

        adapter = new ProdukAdapter(this, filteredList);
        gridViewProduk.setAdapter(adapter);
    }
    private void tambahDataDummyProduk() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            r.delete(Produk.class);
        });
        if (realm.where(Produk.class).findAll().isEmpty()) {
            realm.executeTransaction(r -> {
                Produk p1 = r.createObject(Produk.class, 1);
                p1.setNamaProduk("UpCycled Jeans Dress");
                p1.setHarga(300000);
                p1.setGambar(R.drawable.f1);
                p1.setOnSale(false);
                p1.setKategori("fashion");
                p1.setRating(4.2);
                p1.setLike(3.8);
                p1.setDetail("Modern cut dress made from recycled jeans.");

                Produk p2 = r.createObject(Produk.class, 2);
                p2.setNamaProduk("Denim Jacket 1");
                p2.setHarga(180000);
                p2.setGambar(R.drawable.f2);
                p2.setOnSale(true);
                p2.setKategori("fashion");
                p2.setRating(4.0);
                p2.setLike(3.5);
                p2.setDetail("Classic denim jacket that's comfy and eco-friendly.");

                Produk p3 = r.createObject(Produk.class, 3);
                p3.setNamaProduk("Upcycled Denim Shirt");
                p3.setHarga(95000);
                p3.setGambar(R.drawable.f3);
                p3.setOnSale(true);
                p3.setKategori("fashion");
                p3.setRating(4.5);
                p3.setLike(4.1);
                p3.setDetail("Recycled denim shirt with a stylish design.");

                Produk p4 = r.createObject(Produk.class, 4);
                p4.setNamaProduk("Outer Denim 1");
                p4.setHarga(200000);
                p4.setGambar(R.drawable.f4);
                p4.setOnSale(true);
                p4.setKategori("fashion");
                p4.setRating(4.1);
                p4.setLike(3.7);
                p4.setDetail("Denim outerwear suitable for any occasion.");

                Produk p5 = r.createObject(Produk.class, 5);
                p5.setNamaProduk("Outer Denim 2");
                p5.setHarga(210000);
                p5.setGambar(R.drawable.f5);
                p5.setOnSale(false);
                p5.setKategori("fashion");
                p5.setRating(4.3);
                p5.setLike(3.9);
                p5.setDetail("Simple denim outerwear with a modern cut.");

                Produk p6 = r.createObject(Produk.class, 6);
                p6.setNamaProduk("Outer Denim 3");
                p6.setHarga(1200000);
                p6.setGambar(R.drawable.f6);
                p6.setOnSale(true);
                p6.setKategori("fashion");
                p6.setRating(4.7);
                p6.setLike(4.5);
                p6.setDetail("Premium outerwear with unique embroidered details.");

                Produk p7 = r.createObject(Produk.class, 7);
                p7.setNamaProduk("Denim Skirt");
                p7.setHarga(320000);
                p7.setGambar(R.drawable.f7);
                p7.setOnSale(false);
                p7.setKategori("fashion");
                p7.setRating(4.0);
                p7.setLike(3.6);
                p7.setDetail("Upcycled denim skirt for a feminine look.");

                Produk p8 = r.createObject(Produk.class, 8);
                p8.setNamaProduk("Denim Pants");
                p8.setHarga(110000);
                p8.setGambar(R.drawable.f8);
                p8.setOnSale(true);
                p8.setKategori("fashion");
                p8.setRating(4.4);
                p8.setLike(3.8);
                p8.setDetail("Casual denim pants for daily activities.");

                Produk p9 = r.createObject(Produk.class, 9);
                p9.setNamaProduk("Upcycled Owl Decoration");
                p9.setHarga(200000);
                p9.setGambar(R.drawable.d1);
                p9.setOnSale(false);
                p9.setKategori("decor");
                p9.setRating(3.9);
                p9.setLike(3.3);
                p9.setDetail("Unique owl-shaped wall decoration.");

                Produk p10 = r.createObject(Produk.class, 10);
                p10.setNamaProduk("Phone Pouch");
                p10.setHarga(100000);
                p10.setGambar(R.drawable.dr2);
                p10.setOnSale(true);
                p10.setKategori("decor");
                p10.setRating(4.2);
                p10.setLike(3.9);
                p10.setDetail("Cute phone pouch made from old denim.");

                Produk p11 = r.createObject(Produk.class, 11);
                p11.setNamaProduk("Denim Pocket");
                p11.setHarga(230000);
                p11.setGambar(R.drawable.dr3);
                p11.setOnSale(false);
                p11.setKategori("decor");
                p11.setRating(4.0);
                p11.setLike(3.5);
                p11.setDetail("Small storage made from denim.");

                Produk p12 = r.createObject(Produk.class, 12);
                p12.setNamaProduk("Denim Storage");
                p12.setHarga(220000);
                p12.setGambar(R.drawable.dr4);
                p12.setOnSale(true);
                p12.setKategori("decor");
                p12.setRating(4.1);
                p12.setLike(3.8);
                p12.setDetail("Multipurpose storage box made of denim.");

                Produk p13 = r.createObject(Produk.class, 13);
                p13.setNamaProduk("Pencil Holder");
                p13.setHarga(80000);
                p13.setGambar(R.drawable.dr5);
                p13.setOnSale(true);
                p13.setKategori("decor");
                p13.setRating(4.3);
                p13.setLike(4.0);
                p13.setDetail("Rustic-style pencil holder from denim.");

                Produk p14 = r.createObject(Produk.class, 14);
                p14.setNamaProduk("Lamp");
                p14.setHarga(300000);
                p14.setGambar(R.drawable.dr6);
                p14.setOnSale(false);
                p14.setKategori("decor");
                p14.setRating(3.8);
                p14.setLike(3.2);
                p14.setDetail("Desk lamp made of recycled denim and wood.");

                Produk p15 = r.createObject(Produk.class, 15);
                p15.setNamaProduk("Racket Clock");
                p15.setHarga(165000);
                p15.setGambar(R.drawable.dr7);
                p15.setOnSale(false);
                p15.setKategori("decor");
                p15.setRating(4.6);
                p15.setLike(4.4);
                p15.setDetail("Unique wall clock made from an old racket.");

                Produk p16 = r.createObject(Produk.class, 16);
                p16.setNamaProduk("ToteBag 1");
                p16.setHarga(300000);
                p16.setGambar(R.drawable.t3);
                p16.setOnSale(false);
                p16.setKategori("totebag");
                p16.setRating(4.0);
                p16.setLike(3.7);
                p16.setDetail("Simple tote bag with durable zipper.");

                Produk p17 = r.createObject(Produk.class, 17);
                p17.setNamaProduk("ToteBag 2");
                p17.setHarga(155000);
                p17.setGambar(R.drawable.t4);
                p17.setOnSale(true);
                p17.setKategori("totebag");
                p17.setRating(4.5);
                p17.setLike(4.1);
                p17.setDetail("Tote bag with tropical leaf patterns.");

                Produk p18 = r.createObject(Produk.class, 18);
                p18.setNamaProduk("ToteBag 3");
                p18.setHarga(99000);
                p18.setGambar(R.drawable.t5);
                p18.setOnSale(true);
                p18.setKategori("totebag");
                p18.setRating(4.3);
                p18.setLike(4.0);
                p18.setDetail("Tote bag with long strap and spacious room.");

                Produk p19 = r.createObject(Produk.class, 19);
                p19.setNamaProduk("ToteBag 4");
                p19.setHarga(210000);
                p19.setGambar(R.drawable.t6);
                p19.setOnSale(false);
                p19.setKategori("totebag");
                p19.setRating(4.1);
                p19.setLike(3.6);
                p19.setDetail("Elegant tote bag made of strong material.");

                Produk p20 = r.createObject(Produk.class, 20);
                p20.setNamaProduk("ToteBag 5");
                p20.setHarga(250000);
                p20.setGambar(R.drawable.t7);
                p20.setOnSale(false);
                p20.setKategori("totebag");
                p20.setRating(3.9);
                p20.setLike(3.3);
                p20.setDetail("Tote bag with neat stitches and trendy design.");

                Produk p21 = r.createObject(Produk.class, 21);
                p21.setNamaProduk("ToteBag 6");
                p21.setHarga(100000);
                p21.setGambar(R.drawable.t8);
                p21.setOnSale(true);
                p21.setKategori("totebag");
                p21.setRating(4.4);
                p21.setLike(4.2);
                p21.setDetail("Lightweight tote bag made of recycled fabric.");

                Produk p22 = r.createObject(Produk.class, 22);
                p22.setNamaProduk("ToteBag 7");
                p22.setHarga(235000);
                p22.setGambar(R.drawable.t9);
                p22.setOnSale(false);
                p22.setKategori("totebag");
                p22.setRating(4.2);
                p22.setLike(3.9);
                p22.setDetail("Tote bag transformed from old curtains into something beautiful.");

                Produk p23 = r.createObject(Produk.class, 23);
                p23.setNamaProduk("Hat 1");
                p23.setHarga(130000);
                p23.setGambar(R.drawable.tp);
                p23.setOnSale(true);
                p23.setKategori("hat");
                p23.setRating(4.0);
                p23.setLike(3.7);
                p23.setDetail("Straw hat with a natural design.");

                Produk p24 = r.createObject(Produk.class, 24);
                p24.setNamaProduk("Hat 2");
                p24.setHarga(145000);
                p24.setGambar(R.drawable.tp1);
                p24.setOnSale(false);
                p24.setKategori("hat");
                p24.setRating(3.8);
                p24.setLike(3.5);
                p24.setDetail("Casual hat with ethnic touch.");

                Produk p25 = r.createObject(Produk.class, 25);
                p25.setNamaProduk("Hat 3");
                p25.setHarga(160000);
                p25.setGambar(R.drawable.tp3);
                p25.setOnSale(false);
                p25.setKategori("hat");
                p25.setRating(4.1);
                p25.setLike(3.9);
                p25.setDetail("Knitted accent hat made from eco-friendly materials.");

                Produk p26 = r.createObject(Produk.class, 26);
                p26.setNamaProduk("Hat 4");
                p26.setHarga(200000);
                p26.setGambar(R.drawable.tp4);
                p26.setOnSale(false);
                p26.setKategori("hat");
                p26.setRating(3.9);
                p26.setLike(3.4);
                p26.setDetail("Unique hat suitable for summer.");

                Produk p27 = r.createObject(Produk.class, 27);
                p27.setNamaProduk("Hat 5");
                p27.setHarga(140000);
                p27.setGambar(R.drawable.tp5);
                p27.setOnSale(true);
                p27.setKategori("hat");
                p27.setRating(4.2);
                p27.setLike(4.0);
                p27.setDetail("Comfortable hat in neutral color for daily style.");
            });
        }

        realm.close();
    }

}
