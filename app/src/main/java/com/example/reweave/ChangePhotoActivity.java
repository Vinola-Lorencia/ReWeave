package com.example.reweave;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Model.User;

import io.realm.Realm;

public class ChangePhotoActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPhone;
    private Button btnSave;
    private Realm realm;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_photo);

        edtName = findViewById(R.id.edtChName);
        edtEmail = findViewById(R.id.edtChEmail);
        edtPhone = findViewById(R.id.edtChPhone);
        btnSave = findViewById(R.id.btnSave);

        realm = Realm.getDefaultInstance();

        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String email = preferences.getString("user_email", null);

        if (email != null) {
            currentUser = realm.where(User.class).equalTo("email", email).findFirst();
            if (currentUser != null) {
                edtName.setText(currentUser.getName());
                edtEmail.setText(currentUser.getEmail());
                edtPhone.setText(currentUser.getPhone());
            }
        }

        btnSave.setOnClickListener(v -> {
            if (currentUser != null) {
                realm.executeTransaction(r -> {
                    currentUser.setName(edtName.getText().toString());
                    currentUser.setEmail(edtEmail.getText().toString());
                    currentUser.setPhone(edtPhone.getText().toString());
                });

                Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
                finish(); // balik ke ProfileFragment
            }
        });
    }
}
