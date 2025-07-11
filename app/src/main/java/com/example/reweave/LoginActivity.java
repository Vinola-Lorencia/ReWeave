package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmResults;
import com.example.reweave.Model.User;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button btnLogin;
    TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        btnLogin = findViewById(R.id.button2);
        txtRegister = findViewById(R.id.textView5);

        btnLogin.setOnClickListener(v -> {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<User> results = realm.where(User.class)
                    .equalTo("email", email.getText().toString())
                    .equalTo("password", password.getText().toString())
                    .findAll();

            if (results.size() > 0) {
                String name = results.get(0).getName();
                Intent i = new Intent(this, MainUIActivity.class);
                i.putExtra("nama_user", name);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show();
            }
        });

        txtRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
