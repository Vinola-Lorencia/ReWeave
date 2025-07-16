package com.example.reweave.ui.Donate;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.HashMap;
import java.util.Map;

public class DonateFragment extends Fragment {

    private EditText searchEditText;
    private Map<String, View> komunitasCards;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        searchEditText = view.findViewById(R.id.edit_search);
        komunitasCards = new HashMap<>();

        komunitasCards.put("komunitas a", view.findViewById(R.id.card_komunitas_a));
        komunitasCards.put("komunitas b", view.findViewById(R.id.card_komunitas_b));
        komunitasCards.put("komunitas c", view.findViewById(R.id.card_komunitas_c));
        komunitasCards.put("komunitas d", view.findViewById(R.id.card_komunitas_d));
        komunitasCards.put("komunitas e", view.findViewById(R.id.card_komunitas_e));
        komunitasCards.put("komunitas f", view.findViewById(R.id.card_komunitas_f));

        // Setup tombol join per komunitas
        setupJoin(view, R.id.btn_join_komunitas_a, "Komunitas A", "Jl. Mawar No.1", "0811111111", "08:00 - 16:00", "• Baju Anak\n• Jaket\n• Celana");
        setupJoin(view, R.id.btn_join_komunitas_b, "Komunitas B", "Jl. Melati No.2", "0822222222", "09:00 - 17:00", "• Baju Dewasa\n• Sepatu");
        setupJoin(view, R.id.btn_join_komunitas_c, "Komunitas C", "Jl. Anggrek No.3", "0833333333", "07:00 - 15:00", "• Jaket\n• Celana\n• Sepatu");
        setupJoin(view, R.id.btn_join_komunitas_d, "Komunitas D", "Jl. Kenanga No.4", "0844444444", "10:00 - 18:00", "• Semua Jenis Pakaian");
        setupJoin(view, R.id.btn_join_komunitas_e, "Komunitas E", "Jl. Dahlia No.5", "0855555555", "11:00 - 19:00", "• Baju Anak\n• Baju Dewasa");
        setupJoin(view, R.id.btn_join_komunitas_f, "Komunitas F", "Jl. Sakura No.6", "0866666666", "08:00 - 17:00", "• Baju Dewasa\n• Jaket\n• Sepatu");

        setupSearch();

        return view;
    }

    private void setupJoin(View view, int btnId, String nama, String alamat, String kontak, String jam, String jenisPakaian) {
        Button btn = view.findViewById(btnId);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), DetailKomunitasActivity.class);
            intent.putExtra("namaKomunitas", nama);
            intent.putExtra("alamat", alamat);
            intent.putExtra("kontak", kontak);
            intent.putExtra("jamBuka", jam);
            intent.putExtra("jenisPakaian", jenisPakaian);
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
                for (Map.Entry<String, View> entry : komunitasCards.entrySet()) {
                    entry.getValue().setVisibility(entry.getKey().contains(keyword) ? View.VISIBLE : View.GONE);
                }
            }
        });
    }
}
