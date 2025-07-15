package com.example.reweave.ui.Marketplace;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reweave.Adapter.FlashSaleAdapter;
import com.example.reweave.CheckOutActivity;
import com.example.reweave.KeranjangActivity;
import com.example.reweave.Model.FlashSale;
import com.example.reweave.R;
import com.example.reweave.TampilanProdukActivity;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MarketplaceFragment extends Fragment {

    ListView listViewFlashSale;
    ImageView imagekeranjang;


    ArrayList<FlashSale> FlashSaleArrayList;
    CardView crdfashion, crdhat, crdtototebag, crddecor, crdseeall;
    FlashSaleAdapter adapter;
    private MarketplaceViewModel mViewModel;

    public static MarketplaceFragment newInstance() {
        return new MarketplaceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);
        listViewFlashSale = view.findViewById(R.id.listViewFlashSale);

        crdfashion = view.findViewById(R.id.crdfashion);
        crdhat = view.findViewById(R.id.crdhat);
        crdtototebag = view.findViewById(R.id.crdtototebag);
        crddecor = view.findViewById(R.id.crddecor);
        crdseeall = view.findViewById(R.id.crdseelall);
        imagekeranjang = view.findViewById(R.id.imagekeranjang);



        crdfashion.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TampilanProdukActivity.class);
            intent.putExtra("kategori", "fashion");
            startActivity(intent);
        });

        crdhat.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TampilanProdukActivity.class);
            intent.putExtra("kategori", "hat");
            startActivity(intent);
        });

        crdtototebag.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TampilanProdukActivity.class);
            intent.putExtra("kategori", "totebag");
            startActivity(intent);
        });

        crddecor.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TampilanProdukActivity.class);
            intent.putExtra("kategori", "decor");
            startActivity(intent);
        });

        imagekeranjang.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), KeranjangActivity.class);

            startActivity(intent);
        });


        tambahDataDummyFlashSale();
        FlashSaleArrayList = getAllProduk();

        adapter = new FlashSaleAdapter(FlashSaleArrayList, requireContext());
        listViewFlashSale.setAdapter(adapter);

        listViewFlashSale.setOnItemClickListener((parent, view1, position, id) -> {
            FlashSale item = adapter.getItem(position);

            Intent intent = new Intent(requireContext(), CheckOutActivity.class);
            intent.putExtra("tipe", "flashsale");
            intent.putExtra("nama", item.getNamaBarangFlashSale());
            intent.putExtra("harga", Integer.parseInt(item.getHargaDiskon()));
            intent.putExtra("hargaAwal", item.getHargaAwal());
            intent.putExtra("kuantitas", 1); // default 1
            intent.putExtra("gambarResId", item.getGambarProduk());

            startActivity(intent);
        });
        crdseeall.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TampilanProdukActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MarketplaceViewModel.class);
    }

    private ArrayList<FlashSale> getAllProduk() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<FlashSale> results = realm.where(FlashSale.class).findAll();
        return new ArrayList<>(results);
    }

    private void tambahDataDummyFlashSale() {
        Realm realm = Realm.getDefaultInstance();

        // Hapus semua data lama dulu
        realm.executeTransaction(r -> r.delete(FlashSale.class));

        // Tambah data baru setelah dihapus
        realm.executeTransaction(r -> {
            FlashSale item1 = r.createObject(FlashSale.class, 1);
            item1.setNamaBarangFlashSale("Upcycled Denim Bag");
            item1.setDetail("Tas berbahan denim daur ulang");
            item1.setHargaAwal("250000");
            item1.setHargaDiskon("50000");
            item1.setGambarProduk(R.drawable.tassatu);

            FlashSale item2 = r.createObject(FlashSale.class, 2);
            item2.setNamaBarangFlashSale("Eco Tote Bag");
            item2.setDetail("Tote bag ramah lingkungan");
            item2.setHargaAwal("180000");
            item2.setHargaDiskon("40000");
            item2.setGambarProduk(R.drawable.tasdua);

            FlashSale item3 = r.createObject(FlashSale.class, 3);
            item3.setNamaBarangFlashSale("Canvas Bag Limited");
            item3.setDetail("Tas kanvas edisi terbatas");
            item3.setHargaAwal("300000");
            item3.setHargaDiskon("20000");
            item3.setGambarProduk(R.drawable.tas3);


        });

        realm.close();
    }

}
