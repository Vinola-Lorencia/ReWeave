package com.example.reweave.ui.Homes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.reweave.Model.User;
import com.example.reweave.R;

import io.realm.Realm;

public class HomesFragment extends Fragment {
    TextView edtNama;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        edtNama = root.findViewById(R.id.edtNama);

        // Ambil email dari session
        SharedPreferences preferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE);
        String email = preferences.getString("user_email", null);

        if (email != null) {
            Realm realm = Realm.getDefaultInstance();
            User user = realm.where(User.class).equalTo("email", email).findFirst();
            if (user != null) {
                edtNama.setText(user.getName());
            }
        }

        return root;
    }
}
