package com.example.reweave.ui.Donate;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reweave.DetailKomunitasActivity;
import com.example.reweave.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonateFragment extends Fragment {

    private EditText searchEditText;
//    private Map<String, View> komunitasCards;
    private List<Pair<String, View>> komunitasCards;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        searchEditText = view.findViewById(R.id.edit_search);

        komunitasCards = new ArrayList<>();

        komunitasCards.add(new Pair<>("Pakaian Peduli", view.findViewById(R.id.card_komunitas_a)));
        komunitasCards.add(new Pair<>("Berbagi Hangat", view.findViewById(R.id.card_komunitas_b)));
        komunitasCards.add(new Pair<>("Komunitas Sandang", view.findViewById(R.id.card_komunitas_c)));
        komunitasCards.add(new Pair<>("Hangat Sesama", view.findViewById(R.id.card_komunitas_d)));
        komunitasCards.add(new Pair<>("Wardrobe Amal", view.findViewById(R.id.card_komunitas_e)));
        komunitasCards.add(new Pair<>("Gerakan Pakaian Layak", view.findViewById(R.id.card_komunitas_f)));


        // Setup tombol join per komunitas
        setupJoin(view, R.id.btn_join_komunitas_a, "Pakaian Peduli", "Jl. Mawar No.1", "0811111111", "08:00 - 16:00", "• Baju Anak\n• Jaket\n• Celana", R.drawable.community);
        setupJoin(view, R.id.btn_join_komunitas_b, "Berbagi Hangat", "Jl. Melati No.2", "0822222222", "09:00 - 17:00", "• Baju Dewasa\n• Sepatu", R.drawable.communitytwo);
        setupJoin(view, R.id.btn_join_komunitas_c, "Komunitas Sandang", "Jl. Anggrek No.3", "0833333333", "07:00 - 15:00", "• Jaket\n• Celana\n• Sepatu", R.drawable.communitythree);
        setupJoin(view, R.id.btn_join_komunitas_d, "Hangat Sesama", "Jl. Kenanga No.4", "0844444444", "10:00 - 18:00", "• Semua Jenis Pakaian", R.drawable.communityfour);
        setupJoin(view, R.id.btn_join_komunitas_e, "Wardrobe Amal", "Jl. Dahlia No.5", "0855555555", "11:00 - 19:00", "• Baju Anak\n• Baju Dewasa", R.drawable.communityfive);
        setupJoin(view, R.id.btn_join_komunitas_f, "Gerakan Pakaian Layak", "Jl. Sakura No.6", "0866666666", "08:00 - 17:00", "• Baju Dewasa\n• Jaket\n• Sepatu", R.drawable.communitysix);

        setupSearch();

        return view;
    }


    private void setupJoin(View view, int btnId, String nama, String alamat, String kontak, String jam, String jenisPakaian, int imageResId) {
        Button btn = view.findViewById(btnId);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), DetailKomunitasActivity.class);
            intent.putExtra("namaKomunitas", nama);
            intent.putExtra("alamat", alamat);
            intent.putExtra("kontak", kontak);
            intent.putExtra("jamBuka", jam);
            intent.putExtra("jenisPakaian", jenisPakaian);
            intent.putExtra("imageResId", imageResId); // ini penting
            startActivity(intent);
        });
    }


    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString().toLowerCase();
                for (Pair<String, View> entry : komunitasCards) {
                    boolean match = entry.first.toLowerCase().contains(keyword);
                    entry.second.setVisibility(match ? View.VISIBLE : View.GONE);
                }
            }
        });
    }


}
