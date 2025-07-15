package com.example.reweave;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reweave.Model.Donasi;
import com.example.reweave.R;
import com.google.android.material.textfield.TextInputEditText;

import io.realm.Realm;

public class DonationActivity extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 101;
    private Uri selectedImageUri;

    private TextInputEditText inputFirst, inputLast, inputEmail, inputPhone, inputType, inputColor, inputSize, inputCall, inputInfo;
    private AutoCompleteTextView dropdownOption1, dropdownOption2, dropdownOption3, dropdownOption4, dropdownOption5;
    private CheckBox permissionContact;
    private Button uploadButton, submitButton;
    private TextView dragDropArea;
    private ImageView imagePreview;

    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        Realm.init(requireContext());
        realm = Realm.getDefaultInstance();

        initViews(view);
        setupDropdowns();
        setupUploadButton();
        setupSubmitButton();

        return view;
    }

    private void initViews(View view) {
        inputFirst = view.findViewById(R.id.inputEditFirst);
        inputLast = view.findViewById(R.id.inputEditLast);
        inputEmail = view.findViewById(R.id.inputEditEmail);
        inputPhone = view.findViewById(R.id.inputEditPhone);
        inputType = view.findViewById(R.id.inputType);
        inputColor = view.findViewById(R.id.inputSize);
        inputSize = view.findViewById(R.id.inputSizw);
        inputCall = view.findViewById(R.id.inputCall);
        inputInfo = view.findViewById(R.id.inputInfo);

        dropdownOption1 = view.findViewById(R.id.dropdownOption1);
        dropdownOption2 = view.findViewById(R.id.dropdownOption2);
        dropdownOption3 = view.findViewById(R.id.dropdownOption3);
        dropdownOption4 = view.findViewById(R.id.dropdownOption4);
        dropdownOption5 = view.findViewById(R.id.dropdownOption5);

        permissionContact = view.findViewById(R.id.permissioncontact);
        uploadButton = view.findViewById(R.id.upload_button);
        submitButton = view.findViewById(R.id.submit_button);
        dragDropArea = view.findViewById(R.id.drag_drop_area);

        imagePreview = new ImageView(requireContext());
        ((ViewGroup) view).addView(imagePreview);
    }

    private void setupDropdowns() {
        setDropdownAdapter(dropdownOption1, R.array.clothes_condition);
        setDropdownAdapter(dropdownOption2, R.array.clothes_type);
        setDropdownAdapter(dropdownOption3, R.array.worn_duration);
        setDropdownAdapter(dropdownOption4, R.array.extra_options);
        setDropdownAdapter(dropdownOption5, R.array.donation_target);
    }

    private void setDropdownAdapter(AutoCompleteTextView dropdown, int arrayRes) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), arrayRes, android.R.layout.simple_dropdown_item_1line);
        dropdown.setAdapter(adapter);
    }

    private void setupUploadButton() {
        uploadButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                donasi.setCondition(dropdownOption1.getText().toString());
                donasi.setType(dropdownOption2.getText().toString());
                donasi.setWornYears(dropdownOption3.getText().toString());
                donasi.setExtraInfo(dropdownOption4.getText().toString());
                donasi.setTarget(dropdownOption5.getText().toString());
                donasi.setPermission(permissionContact.isChecked());
                donasi.setPhotoUri(selectedImageUri != null ? selectedImageUri.toString() : "");
            });
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
