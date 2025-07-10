package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FormulirDonasi extends Fragment {

    private EditText firstName, lastName, email, phoneNumber, clothesBrand, clothesColor, clothesAge, preferredCallTime, extraInfo;
    private Spinner clothesCondition, clothesType, clothesFabric, clothesSize;
    private CheckBox permissionContact;
    private Button uploadButton, submitButton;
    private TextView dragDropArea, uploadPhotosLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_formulir_donasi); // Use the correct layout for the activity

        // Initialize form fields
        clothesBrand = clothesBrand.findViewById(R.id.clothes_brand);
        clothesColor = findViewById(R.id.clothes_color);
        clothesAge = findViewById(R.id.clothes_age);
        preferredCallTime = findViewById(R.id.preferred_call_time);
        extraInfo = findViewById(R.id.extra_info);

        clothesCondition = findViewById(R.id.clothes_condition);
        clothesType = findViewById(R.id.clothes_type);
        clothesFabric = findViewById(R.id.clothes_fabric);
        clothesSize = findViewById(R.id.clothes_size);

        permissionContact = findViewById(R.id.permission_contact);
        uploadButton = findViewById(R.id.upload_button);
        submitButton = findViewById(R.id.submit_button);

        dragDropArea = findViewById(R.id.drag_drop_area);
        uploadPhotosLabel = findViewById(R.id.upload_photos_label);

        // Setup Spinners
        setupSpinners();

        // Set click listeners
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle file upload (This is just a placeholder for now)
                Toast.makeText(FormulirDonasi.this, "File upload clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to setup the Spinners
    private void setupSpinners() {
        // Clothes Condition Spinner
        ArrayAdapter<CharSequence> conditionAdapter = ArrayAdapter.createFromResource(this,
                R.array.clothes_condition, android.R.layout.simple_spinner_item);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clothesCondition.setAdapter(conditionAdapter);

        // Clothes Type Spinner
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.clothes_type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clothesType.setAdapter(typeAdapter);

        // Clothes Fabric Spinner
        ArrayAdapter<CharSequence> fabricAdapter = ArrayAdapter.createFromResource(this,
                R.array.clothes_fabric, android.R.layout.simple_spinner_item);
        fabricAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clothesFabric.setAdapter(fabricAdapter);

        // Clothes Size Spinner
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.clothes_size, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clothesSize.setAdapter(sizeAdapter);
    }
    private void submitForm() {
        // Ambil data dari form
        String firstNameText = firstName.getText().toString().trim();
        String lastNameText = lastName.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String phoneNumberText = phoneNumber.getText().toString().trim();
        String clothesBrandText = clothesBrand.getText().toString().trim();
        String clothesColorText = clothesColor.getText().toString().trim();
        String clothesAgeText = clothesAge.getText().toString().trim();
        String preferredCallTimeText = preferredCallTime.getText().toString().trim();
        String extraInfoText = extraInfo.getText().toString().trim();
        firstName = findViewById(R.id.inputEditFirst);
        lastName = findViewById(R.id.inputEditLast);
        email = findViewById(R.id.inputEditEmail);
        phoneNumber = findViewById(R.id.inputEditPhone);


        // Validasi
        if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() || phoneNumberText.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tampilkan pesan berhasil
        Toast.makeText(this, "Donation form submitted successfully!", Toast.LENGTH_SHORT).show();

        // Optional: Bersihkan form
        clearForm();

        // Pindah ke halaman DaftarKomunitas
        Intent intent = new Intent(FormulirDonasi.this, DaftarKomunitas.class);
        startActivity(intent);
    }


    private void clearForm() {
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        phoneNumber.setText("");
        clothesBrand.setText("");
        clothesColor.setText("");
        clothesAge.setText("");
        preferredCallTime.setText("");
        extraInfo.setText("");

        clothesCondition.setSelection(0);  // Reset spinner to first item
        clothesType.setSelection(0);
        clothesFabric.setSelection(0);
        clothesSize.setSelection(0);

        permissionContact.setChecked(false);
    }
}
