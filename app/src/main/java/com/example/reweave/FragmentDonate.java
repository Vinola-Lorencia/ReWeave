package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reweave.R;

import java.util.HashMap;
import java.util.Map;

public class FragmentDonate extends Fragment {

    private EditText searchEditText;
    private Map<String, View> komunitasCards;

    private Button btna,btnb,btnc,btnd,btne,btnf;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_donate, container, false);
        searchEditText = view.findViewById(R.id.edit_search);
        btna=view.findViewById(R.id.btn_join_komunitas_a);
        btnb=view.findViewById(R.id.btn_join_komunitas_b);
        btnc=view.findViewById(R.id.btn_join_komunitas_c);
        btnd=view.findViewById(R.id.btn_join_komunitas_d);
        btne=view.findViewById(R.id.btn_join_komunitas_e);
        btnf=view.findViewById(R.id.btn_join_komunitas_f);

        komunitasCards = new HashMap<>();

        btna. setOnClickListener(v -> startActivity(new Intent(requireContext(), DonationActivity.class)));
        btnb. setOnClickListener(v -> startActivity(new Intent(requireContext(), DonationActivity.class)));
        btnc. setOnClickListener(v -> startActivity(new Intent(requireContext(), DonationActivity.class)));
        btnd. setOnClickListener(v -> startActivity(new Intent(requireContext(), DonationActivity.class)));
        btne. setOnClickListener(v -> startActivity(new Intent(requireContext(), DonationActivity.class)));
        btnf. setOnClickListener(v -> startActivity(new Intent(requireContext(), DonationActivity.class)));

        // Hubungkan id Card Layout
        komunitasCards.put("komunitas a", view.findViewById(R.id.card_komunitas_a));
        komunitasCards.put("komunitas b", view.findViewById(R.id.card_komunitas_b));
        komunitasCards.put("komunitas c", view.findViewById(R.id.card_komunitas_c));
        komunitasCards.put("komunitas d", view.findViewById(R.id.card_komunitas_d));
        komunitasCards.put("komunitas e", view.findViewById(R.id.card_komunitas_e));
        komunitasCards.put("komunitas f", view.findViewById(R.id.card_komunitas_f));

        // Setup tombol join per komunitas
        setupJoinButton(view, R.id.btn_join_komunitas_a, "Komunitas A", "Jl. Mawar No. 1", "0811111111", "08:00 - 16:00", "• Baju Anak\n• Jaket\n• Celana");
        setupJoinButton(view, R.id.btn_join_komunitas_b, "Komunitas B", "Jl. Melati No. 2", "0822222222", "09:00 - 17:00", "• Baju Dewasa\n• Sepatu");
        setupJoinButton(view, R.id.btn_join_komunitas_c, "Komunitas C", "Jl. Anggrek No. 3", "0833333333", "07:00 - 15:00", "• Jaket\n• Celana\n• Sepatu");
        setupJoinButton(view, R.id.btn_join_komunitas_d, "Komunitas D", "Jl. Kenanga No. 4", "0844444444", "10:00 - 18:00", "• Semua Jenis Pakaian");
        setupJoinButton(view, R.id.btn_join_komunitas_e, "Komunitas E", "Jl. Dahlia No. 5", "0855555555", "11:00 - 19:00", "• Baju Anak\n• Baju Dewasa");
        setupJoinButton(view, R.id.btn_join_komunitas_f, "Komunitas F", "Jl. Sakura No. 6", "0866666666", "08:00 - 17:00", "• Baju Dewasa\n• Jaket\n• Sepatu");

        setupSearchListener();

        return view;
    }

    private void setupJoinButton(View view, int buttonId, String komunitasName, String alamat, String kontak, String jamBuka, String jenisPakaian) {
        Button joinButton = view.findViewById(buttonId);
        joinButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), DetailKomunitasActivity.class);
            intent.putExtra("namaKomunitas", komunitasName);
            intent.putExtra("alamat", alamat);
            intent.putExtra("kontak", kontak);
            intent.putExtra("jamBuka", jamBuka);
            intent.putExtra("jenisPakaian", jenisPakaian);
            startActivity(intent);
        });
    }

    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                filterKomunitas(s.toString().toLowerCase());
            }
        });
    }

    private void filterKomunitas(String keyword) {
        for (Map.Entry<String, View> entry : komunitasCards.entrySet()) {
            if (entry.getKey().contains(keyword)) {
                entry.getValue().setVisibility(View.VISIBLE);
            } else {
                entry.getValue().setVisibility(View.GONE);
            }
        }
    }
}