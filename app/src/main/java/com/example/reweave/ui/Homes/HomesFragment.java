package com.example.reweave.ui.Homes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.reweave.R;

public class HomesFragment extends Fragment {
    TextView edtNama;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        edtNama = root.findViewById(R.id.edtNama);

        // ambil data dari activity
        String namaUser = getActivity().getIntent().getStringExtra("nama_user");
        edtNama.setText(namaUser);

        return root;
    }
}
