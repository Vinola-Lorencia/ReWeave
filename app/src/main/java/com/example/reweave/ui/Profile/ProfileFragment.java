package com.example.reweave.ui.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reweave.ChangePhotoActivity;
import com.example.reweave.LoginActivity;
import com.example.reweave.Model.User;
import com.example.reweave.R;
import io.realm.Realm;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail, tvPhone, tvPass;
    private TextView txtlogout;
    private TextView txtChange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inisialisasi TextView
        tvName = view.findViewById(R.id.textView43);
        tvEmail = view.findViewById(R.id.textView46);
        tvPhone = view.findViewById(R.id.textView48);
        tvPass = view.findViewById(R.id.pass);
        txtlogout = view.findViewById(R.id.txtlogout);
        txtChange = view.findViewById(R.id.textView41);

        // Ambil email dari SharedPreferences
        SharedPreferences preferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE);
        String email = preferences.getString("user_email", null);

        if (email != null) {
            Realm realm = Realm.getDefaultInstance();
            User user = realm.where(User.class).equalTo("email", email).findFirst();

            if (user != null) {
                tvName.setText(user.getName());
                tvEmail.setText(user.getEmail());
                tvPhone.setText(user.getPhone());
                tvPass.setText(user.getPassword());
            }
        }

        // Logout
        txtlogout.setOnClickListener(v -> {
            // Hapus sesi login
            SharedPreferences.Editor editor = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            // Pindah ke halaman login
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish(); // Tutup MainUIActivity agar tidak bisa kembali pakai tombol back
        });

        // Pindah ke halaman ChangePhotoActivity saat tombol txtChange diklik
        txtChange.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), ChangePhotoActivity.class));
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshProfileData();
    }
    private void refreshProfileData() {
        SharedPreferences preferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE);
        String email = preferences.getString("user_email", null);

        if (email != null) {
            Realm realm = Realm.getDefaultInstance();
            User user = realm.where(User.class).equalTo("email", email).findFirst();

            if (user != null) {
                tvName.setText(user.getName());
                tvEmail.setText(user.getEmail());
                tvPhone.setText(user.getPhone());
                tvPass.setText(user.getPassword());
            }
        }
    }


}
