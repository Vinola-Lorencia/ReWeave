package com.example.reweave.ui.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.reweave.Model.User;
import com.example.reweave.R;
import io.realm.Realm;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail, tvPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvName = view.findViewById(R.id.textView43);
        tvEmail = view.findViewById(R.id.textView46);
        tvPhone = view.findViewById(R.id.textView48);

        SharedPreferences preferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE);
        String email = preferences.getString("user_email", null);

        if (email != null) {
            Realm realm = Realm.getDefaultInstance();
            User user = realm.where(User.class).equalTo("email", email).findFirst();

            if (user != null) {
                tvName.setText(user.getName());
                tvEmail.setText(user.getEmail());
                tvPhone.setText(user.getPhone());
            }
        }

        return view;
    }
}
