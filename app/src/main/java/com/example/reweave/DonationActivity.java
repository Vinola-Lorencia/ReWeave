package com.example.reweave;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Model.Donasi;
import com.example.reweave.Model.PoinNotification;
import com.example.reweave.Model.Point;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

import io.realm.Realm;

public class DonationActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 101;
    private Uri selectedImageUri;

    private TextInputEditText inputFirst, inputLast, inputEmail, inputPhone, inputType, inputColor, inputSize, inputCall, inputInfo;
    private Spinner donationTargetSpinner;
    private CheckBox permissionContact;
    private Button uploadButton, submitButton;
    private TextView dragDropArea;
    private ImageView imagePreview;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        initViews();
        setupSpinner();
        setupUploadButton();
        setupSubmitButton();
    }

    private void initViews() {
        inputFirst = findViewById(R.id.inputEditFirst);
        inputLast = findViewById(R.id.inputEditLast);
        inputEmail = findViewById(R.id.inputEditEmail);
        inputPhone = findViewById(R.id.inputEditPhone);
        inputType = findViewById(R.id.inputType);
        inputColor = findViewById(R.id.inputSize);
        inputSize = findViewById(R.id.inputSizw);
        inputCall = findViewById(R.id.inputCall);
        inputInfo = findViewById(R.id.inputInfo);

        donationTargetSpinner = findViewById(R.id.donation_targets);
        permissionContact = findViewById(R.id.permissioncontact);
        uploadButton = findViewById(R.id.upload_button);
        submitButton = findViewById(R.id.submit_button);
        dragDropArea = findViewById(R.id.drag_drop_area);
        imagePreview = new ImageView(this);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.donation_targets,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationTargetSpinner.setAdapter(adapter);
    }

    private void setupUploadButton() {
        uploadButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            dragDropArea.setText("Selected: " + selectedImageUri.getLastPathSegment());
            imagePreview.setImageURI(selectedImageUri);
        }
    }

    private boolean isEmpty(TextInputEditText input) {
        return input.getText() == null || input.getText().toString().trim().isEmpty();
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(view -> {
            if (isEmpty(inputFirst) || isEmpty(inputLast) || isEmpty(inputEmail) || isEmpty(inputPhone) ||
                    isEmpty(inputType) || isEmpty(inputColor) || isEmpty(inputSize) || isEmpty(inputCall) ||
                    donationTargetSpinner.getSelectedItem() == null) {
                Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            String email = inputEmail.getText().toString().trim();
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                return;
            }

            String phone = inputPhone.getText().toString().trim();
            if (phone.length() < 10) {
                Toast.makeText(this, "Phone number too short!", Toast.LENGTH_SHORT).show();
                return;
            }

            String komunitasTerpilih = donationTargetSpinner.getSelectedItem().toString();

            realm.executeTransaction(r -> {
                Donasi donasi = r.createObject(Donasi.class, UUID.randomUUID().toString());
                donasi.setFirstName(inputFirst.getText().toString());
                donasi.setLastName(inputLast.getText().toString());
                donasi.setEmail(email);
                donasi.setPhone(phone);
                donasi.setBrand(inputType.getText().toString());
                donasi.setColor(inputColor.getText().toString());
                donasi.setSize(inputSize.getText().toString());
                donasi.setCallTime(inputCall.getText().toString());
                donasi.setInfo(inputInfo.getText().toString());
                donasi.setTarget(komunitasTerpilih); // optional
                donasi.setPermission(permissionContact.isChecked());
                donasi.setKomunitas(komunitasTerpilih); // wajib untuk tampil
                donasi.setPoint(100); // tambahkan 100 poin

                if (selectedImageUri != null) {
                    donasi.setPhotoUri(selectedImageUri.toString());
                } else {
                    donasi.setPhotoUri("");
                }
            });

            tambahPoinDonasi(100);

            Toast.makeText(this, "Donation submitted! +100 points added", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void tambahPoinDonasi(int poin) {
        SharedPreferences preferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        String email = preferences.getString("user_email", null);

        if (email != null) {
            realm.executeTransaction(r -> {
                Point point = r.where(Point.class).equalTo("email", email).findFirst();
                if (point != null) {
                    point.setPoints(point.getPoints() + poin);
                } else {
                    Point newPoint = r.createObject(Point.class);
                    newPoint.setEmail(email);
                    newPoint.setPoints(poin);
                }

                // Tambah notifikasi
                PoinNotification notif = r.createObject(PoinNotification.class, java.util.UUID.randomUUID().toString());
                notif.setEmail(email);
                notif.setPromoTitle("Donation");
                notif.setPointsRedeemed(poin);
                notif.setDate(new java.util.Date());
                notif.setAlamat("Thank you for donating!");
                notif.setAddition(true); // Ini karena nambah poin
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
