package com.example.reweave;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Model.Donasi;
import com.example.reweave.Model.Point;
import com.google.android.material.textfield.TextInputEditText;

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
            dragDropArea.setText("File dipilih: " + selectedImageUri.getLastPathSegment());
            imagePreview.setImageURI(selectedImageUri);
        }
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(view -> {
            realm.executeTransaction(r -> {
                Donasi donasi = r.createObject(Donasi.class, java.util.UUID.randomUUID().toString());
                donasi.setFirstName(inputFirst.getText().toString());
                donasi.setLastName(inputLast.getText().toString());
                donasi.setEmail(inputEmail.getText().toString());
                donasi.setPhone(inputPhone.getText().toString());
                donasi.setBrand(inputType.getText().toString());
                donasi.setColor(inputColor.getText().toString());
                donasi.setSize(inputSize.getText().toString());
                donasi.setCallTime(inputCall.getText().toString());
                donasi.setInfo(inputInfo.getText().toString());
                donasi.setTarget(donationTargetSpinner.getSelectedItem().toString());
                donasi.setPermission(permissionContact.isChecked());
                donasi.setPhotoUri(selectedImageUri != null ? selectedImageUri.toString() : "");
            });

            tambahPoinDonasi(100);
            Toast.makeText(this, "Donasi berhasil dikirim dan +100 poin ditambahkan!", Toast.LENGTH_SHORT).show();
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
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
//
//public class DonationActivity extends AppCompatActivity {
//
//    private static final int PICK_IMAGE_REQUEST = 101;
//    private Uri selectedImageUri;
//
//    private TextInputEditText inputFirst, inputLast, inputEmail, inputPhone, inputType, inputColor, inputSize, inputCall, inputInfo;
//    private Spinner donationTargetSpinner;
//    private CheckBox permissionContact;
//    private Button uploadButton, submitButton;
//    private TextView dragDropArea;
//    private ImageView imagePreview;
//
//    private Realm realm;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_donation);
//
//        // Inisialisasi Realm
//        Realm.init(this);
//        realm = Realm.getDefaultInstance();
//
//        // Inisialisasi View
//        editNama = findViewById(R.id.editNama);
//        editAlamat = findViewById(R.id.editAlamat);
//        editJumlah = findViewById(R.id.editJumlah);
//        btnSubmitDonation = findViewById(R.id.btnSubmitDonation);
//
//        // Ketika user klik tombol submit donasi
//        btnSubmitDonation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (validateInput()) {
//                    tambahPoinDonasi(100);
//                } else {
//                    Toast.makeText(DonationActivity.this, "Mohon lengkapi data donasi.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private boolean validateInput() {
//        return !editNama.getText().toString().isEmpty()
//                && !editAlamat.getText().toString().isEmpty()
//                && !editJumlah.getText().toString().isEmpty();
//    }
//
//    private void tambahPoinDonasi(int poin) {
//        SharedPreferences preferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
//        String email = preferences.getString("user_email", null);
//
//        if (email != null) {
//            realm.executeTransaction(transactionRealm -> {
//                Point userPoint = transactionRealm.where(Point.class).equalTo("email", email).findFirst();
//                if (userPoint != null) {
//                    int currentPoin = userPoint.getPoints();
//                    userPoint.setPoints(currentPoin + poin);
//                } else {
//                    Point newPoint = transactionRealm.createObject(Point.class);
//                    newPoint.setEmail(email);
//                    newPoint.setPoints(poin);
//                }
//            });
//            Toast.makeText(this, "+100 Poin berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
//            finish(); // kembali ke Home
//        } else {
//            Toast.makeText(this, "User tidak ditemukan!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (realm != null) realm.close();
//    }
//}
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_donation);
//
//        Realm.init(this);
//        realm = Realm.getDefaultInstance();
//
//        initViews();
//        setupSpinner();
//        setupUploadButton();
//        setupSubmitButton();
//    }
//
//    private void initViews() {
//        inputFirst = findViewById(R.id.inputEditFirst);
//        inputLast = findViewById(R.id.inputEditLast);
//        inputEmail = findViewById(R.id.inputEditEmail);
//        inputPhone = findViewById(R.id.inputEditPhone);
//        inputType = findViewById(R.id.inputType);
//        inputColor = findViewById(R.id.inputSize);
//        inputSize = findViewById(R.id.inputSizw);
//        inputCall = findViewById(R.id.inputCall);
//        inputInfo = findViewById(R.id.inputInfo);
//
//        donationTargetSpinner = findViewById(R.id.donation_targets);
//        permissionContact = findViewById(R.id.permissioncontact);
//        uploadButton = findViewById(R.id.upload_button);
//        submitButton = findViewById(R.id.submit_button);
//        dragDropArea = findViewById(R.id.drag_drop_area);
//        imagePreview = new ImageView(this);
//    }
//
//    private void setupSpinner() {
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this,
//                R.array.donation_targets,
//                android.R.layout.simple_spinner_item
//        );
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        donationTargetSpinner.setAdapter(adapter);
//    }
//
//    private void setupUploadButton() {
//        uploadButton.setOnClickListener(view -> {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
//            selectedImageUri = data.getData();
//            dragDropArea.setText("File dipilih: " + selectedImageUri.getLastPathSegment());
//            imagePreview.setImageURI(selectedImageUri);
//        }
//    }
//
//    private void setupSubmitButton() {
//        submitButton.setOnClickListener(view -> {
//            realm.executeTransaction(r -> {
//                Donasi donasi = r.createObject(Donasi.class, java.util.UUID.randomUUID().toString());
//                donasi.setFirstName(inputFirst.getText().toString());
//                donasi.setLastName(inputLast.getText().toString());
//                donasi.setEmail(inputEmail.getText().toString());
//                donasi.setPhone(inputPhone.getText().toString());
//                donasi.setBrand(inputType.getText().toString());
//                donasi.setColor(inputColor.getText().toString());
//                donasi.setSize(inputSize.getText().toString());
//                donasi.setCallTime(inputCall.getText().toString());
//                donasi.setInfo(inputInfo.getText().toString());
//                donasi.setTarget(donationTargetSpinner.getSelectedItem().toString());
//                donasi.setPermission(permissionContact.isChecked());
//                donasi.setPhotoUri(selectedImageUri != null ? selectedImageUri.toString() : "");
//            });
//            finish();
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (realm != null) realm.close();
//    }
//}
