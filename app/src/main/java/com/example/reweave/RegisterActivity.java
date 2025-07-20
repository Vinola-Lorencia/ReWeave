package com.example.reweave;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Model.Point;
import com.example.reweave.Model.User;

import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity {

    EditText edtnama, edtemail, edtnmber, edtpassword, edtConfirmPassword;
    TextView checkUppercase, checkNumber, checkLength, checkSpecial, checkMatch;
    Button btnRegister;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_register);

        // Inisialisasi View
        edtnama = findViewById(R.id.edtnama);
        edtemail = findViewById(R.id.edtemail);
        edtnmber = findViewById(R.id.edtnmber);
        edtpassword = findViewById(R.id.edtpassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.button2);
        checkBox = findViewById(R.id.checkBox);

        checkUppercase = findViewById(R.id.check_uppercase);
        checkNumber = findViewById(R.id.check_number);
        checkLength = findViewById(R.id.check_length);
        checkSpecial = findViewById(R.id.check_special);
        checkMatch = findViewById(R.id.check_match);

        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
            }
        };

        edtpassword.addTextChangedListener(watcher);
        edtConfirmPassword.addTextChangedListener(watcher);

        // Saat tombol register diklik
        btnRegister.setOnClickListener(v -> {
            String name = edtnama.getText().toString().trim();
            String email = edtemail.getText().toString().trim();
            String phone = edtnmber.getText().toString().trim();
            String password = edtpassword.getText().toString();

            // Validasi input
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!checkBox.isChecked()) {
                Toast.makeText(this, "Please agree to the Terms & Conditions", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validasi password
            if (isPasswordValid()) {
                // Simpan user ke Realm
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(r -> {
                    User user = r.createObject(User.class);
                    user.setName(name);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setPassword(password);

                    Point point = r.createObject(Point.class, email); // gunakan email sebagai primary key
                    point.setPoints(2000);
                });

                // Simpan email ke SharedPreferences
                SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
                preferences.edit().putString("user_email", email).apply();


                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Please fix the password requirements", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Validasi password saat diketik
    private void validatePassword() {
        String password = edtpassword.getText().toString();
        String confirm = edtConfirmPassword.getText().toString();

        updateCriteria(checkUppercase, password.matches(".*[A-Z].*"));
        updateCriteria(checkNumber, password.matches(".*[0-9].*"));
        updateCriteria(checkLength, password.length() >= 10);
        updateCriteria(checkSpecial, password.matches(".*[!@#$%^&*+=?._-].*"));
        updateCriteria(checkMatch, password.equals(confirm));
    }

    private void updateCriteria(TextView view, boolean valid) {
        if (valid) {
            view.setText("✓ " + view.getText().toString().substring(2));
            view.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            view.setText("✗ " + view.getText().toString().substring(2));
            view.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    // Validasi keseluruhan password
    private boolean isPasswordValid() {
        String password = edtpassword.getText().toString();
        String confirm = edtConfirmPassword.getText().toString();
        return password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.length() >= 10 &&
                password.matches(".*[!@#$%^&*+=?._-].*") &&
                password.equals(confirm);
    }
}
