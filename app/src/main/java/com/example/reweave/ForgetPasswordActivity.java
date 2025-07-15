package com.example.reweave;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Model.User;

import io.realm.Realm;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText edtEmailForgot;
    Button btnSearchPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        edtEmailForgot = findViewById(R.id.edtEmailForgot);
        btnSearchPassword = findViewById(R.id.btnSearchPassword);

        btnSearchPassword.setOnClickListener(v -> {
            String email = edtEmailForgot.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            Realm realm = Realm.getDefaultInstance();
            User user = realm.where(User.class).equalTo("email", email).findFirst();

            if (user != null) {
                showPasswordDialog(user.getPassword());
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPasswordDialog(String password) {
        new AlertDialog.Builder(this)
                .setTitle("Your Password")
                .setMessage("Password: " + password)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Pindah ke LoginActivity setelah klik OK
                    Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Tutup ForgetPasswordActivity
                })
                .show();
    }
}
