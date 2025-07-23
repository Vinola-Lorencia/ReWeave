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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reweave.DetailKomunitasActivity;
import com.example.reweave.R;
import com.example.reweave.RiwayatDonasiActivity;

import java.util.ArrayList;
import java.util.List;

public class DonateFragment extends Fragment {

    private EditText searchEditText;
    private List<Pair<String, View>> komunitasCards;
    ImageView icon_history3;

    private final int[] cardIds = {
            R.id.card_komunitas_a,
            R.id.card_komunitas_b,
            R.id.card_komunitas_c,
            R.id.card_komunitas_d,
            R.id.card_komunitas_e,
            R.id.card_komunitas_f
    };

    private final int[] buttonIds = {
            R.id.btn_join_komunitas_a,
            R.id.btn_join_komunitas_b,
            R.id.btn_join_komunitas_c,
            R.id.btn_join_komunitas_d,
            R.id.btn_join_komunitas_e,
            R.id.btn_join_komunitas_f
    };

    private final int[] imageResIds = {
            R.drawable.community,
            R.drawable.communitytwo,
            R.drawable.communitythree,
            R.drawable.communityfour,
            R.drawable.communityfive,
            R.drawable.communitysix
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        searchEditText = view.findViewById(R.id.edit_search);
        komunitasCards = new ArrayList<>();

        ImageView iconHistory = view.findViewById(R.id.icon_history3);
        iconHistory.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), RiwayatDonasiActivity.class);
            startActivity(intent);
        });


        // Ambil data dari strings.xml
        String[] namaArray = getResources().getStringArray(R.array.donation_targets);
        String[] alamatArray = getResources().getStringArray(R.array.alamat_komunitas);
        String[] kontakArray = getResources().getStringArray(R.array.kontak_komunitas);
        String[] jamArray = getResources().getStringArray(R.array.jam_komunitas);
        String[] jenisArray = getResources().getStringArray(R.array.jenis_pakaian_komunitas);

        // Loop data komunitas
        for (int i = 0; i < cardIds.length; i++) {
            View cardView = view.findViewById(cardIds[i]);
            komunitasCards.add(new Pair<>(namaArray[i], cardView));

            setupJoin(view, buttonIds[i], namaArray[i], alamatArray[i], kontakArray[i], jamArray[i], jenisArray[i], imageResIds[i]);
        }

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
            intent.putExtra("imageResId", imageResId);
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
