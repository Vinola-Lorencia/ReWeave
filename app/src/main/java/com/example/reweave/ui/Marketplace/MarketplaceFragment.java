package com.example.reweave.ui.Marketplace;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reweave.Adapter.FlashSaleAdapter;
import com.example.reweave.Model.FlashSale;
import com.example.reweave.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MarketplaceFragment extends Fragment {

    ListView listViewFlashSale;
    ArrayList<FlashSale> FlashSaleArrayList;
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

        tambahDataDummyFlashSale(); // Tambah data dummy sekali
        FlashSaleArrayList = getAllProduk();

        adapter = new FlashSaleAdapter(FlashSaleArrayList, requireContext());
        listViewFlashSale.setAdapter(adapter);

        listViewFlashSale.setOnItemClickListener((parent, view1, position, id) -> {
            Toast.makeText(requireContext(),
                    "Dipilih: " + adapter.getItem(position).getNamaBarangFlashSale(),
                    Toast.LENGTH_SHORT).show();
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
        if (realm.where(FlashSale.class).findAll().isEmpty()) {
            realm.executeTransaction(r -> {
                FlashSale item1 = realm.createObject(FlashSale.class, 1);
                item1.setNamaBarangFlashSale("Upcycled Denim Bag");
                item1.setDetail("Tas berbahan denim daur ulang");
                item1.setHargaAwal("250000");
                item1.setHargaDiskon("150000");
                item1.setGambarProduk(R.drawable.tassatu);

                FlashSale item2 = realm.createObject(FlashSale.class, 2);
                item2.setNamaBarangFlashSale("Eco Tote Bag");
                item2.setDetail("Tote bag ramah lingkungan");
                item2.setHargaAwal("180000");
                item2.setHargaDiskon("120000");
                item2.setGambarProduk(R.drawable.tasdua);

                FlashSale item3 = realm.createObject(FlashSale.class, 3);
                item3.setNamaBarangFlashSale("Canvas Bag Limited");
                item3.setDetail("Tas kanvas edisi terbatas");
                item3.setHargaAwal("300000");
                item3.setHargaDiskon("200000");
                item3.setGambarProduk(R.drawable.tas3);
            });
        }
        realm.close();
    }
}
