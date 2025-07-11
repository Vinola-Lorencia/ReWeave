package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;
import com.example.reweave.Model.User;

public class RegisterActivity extends AppCompatActivity {
    EditText name, email, phone, password;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.edtnama);
        email = findViewById(R.id.edtemail);
        phone = findViewById(R.id.edtnmber);
        password = findViewById(R.id.edtpassword);
        btnRegister = findViewById(R.id.button2);

        btnRegister.setOnClickListener(v -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                User user = r.createObject(User.class);
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPhone(phone.getText().toString());
                user.setPassword(password.getText().toString());
                Toast.makeText(this, "Register sukses", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            });
        });
    }
}
