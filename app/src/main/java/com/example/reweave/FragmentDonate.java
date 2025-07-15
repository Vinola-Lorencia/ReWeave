package com.example.reweave;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        searchEditText = view.findViewById(R.id.edit_search);

        komunitasCards = new HashMap<>();

//        komunitasCards.put("komunitas a", view.findViewById(R.id.text_komunitas_a).getParent().getParent());
//        komunitasCards.put("komunitas b", view.findViewById(R.id.text_komunitas_b).getParent().getParent());
//        komunitasCards.put("komunitas c", view.findViewById(R.id.text_komunitas_c).getParent().getParent());
//        komunitasCards.put("komunitas d", view.findViewById(R.id.text_komunitas_d).getParent().getParent());
//        komunitasCards.put("komunitas e", view.findViewById(R.id.text_komunitas_e).getParent().getParent());
//        komunitasCards.put("komunitas f", view.findViewById(R.id.text_komunitas_f).getParent().getParent());

        komunitasCards.put("komunitas a", view.findViewById(R.id.card_komunitas_a));
        komunitasCards.put("komunitas b", view.findViewById(R.id.card_komunitas_b));
        komunitasCards.put("komunitas c", view.findViewById(R.id.card_komunitas_c));
        komunitasCards.put("komunitas d", view.findViewById(R.id.card_komunitas_d));
        komunitasCards.put("komunitas e", view.findViewById(R.id.card_komunitas_e));
        komunitasCards.put("komunitas f", view.findViewById(R.id.card_komunitas_f));


        // Setup tombol join
        setupJoinButton(view, R.id.btn_join_komunitas_a, "Komunitas A");
        setupJoinButton(view, R.id.btn_join_komunitas_b, "Komunitas B");
        setupJoinButton(view, R.id.btn_join_komunitas_c, "Komunitas C");
        setupJoinButton(view, R.id.btn_join_komunitas_d, "Komunitas D");
        setupJoinButton(view, R.id.btn_join_komunitas_e, "Komunitas E");
        setupJoinButton(view, R.id.btn_join_komunitas_f, "Komunitas F");

        setupSearchListener();

        return view;
    }

    private void setupJoinButton(View view, int buttonId, String komunitasName) {
        Button joinButton = view.findViewById(buttonId);
        joinButton.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Bergabung dengan " + komunitasName, Toast.LENGTH_SHORT).show()
        );
    }

    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
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
