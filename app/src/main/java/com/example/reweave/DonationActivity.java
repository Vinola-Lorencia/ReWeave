package com.example.reweave;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Model.Donasi;
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
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
