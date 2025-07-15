package com.example.reweave.ui.Homes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.reweave.ChangePoinActivity;
import com.example.reweave.FormulirDonasi;
import com.example.reweave.MainUIActivity;
import com.example.reweave.Model.User;
import com.example.reweave.R;
import com.example.reweave.RedeemPoinActivity;
import com.example.reweave.RiwayatPoinActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class HomesFragment extends Fragment {

    private TextView edtNama, txtLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private ImageButton btnCollect,btnChange;
    private Button button, button1, button2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        edtNama = root.findViewById(R.id.edtNama);
        txtLocation = root.findViewById(R.id.location);// Pastikan TextView ini ada di layout kamu
        btnCollect = root.findViewById(R.id.btnCollect);
        btnChange=root.findViewById(R.id.btnChange);
        button = root.findViewById(R.id.button);
        button1 = root.findViewById(R.id.button1);
        button2 = root.findViewById(R.id.button2);


        // Ambil dan tampilkan nama user dari Realm
        SharedPreferences preferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE);
        String email = preferences.getString("user_email", null);

        if (email != null) {
            Realm realm = Realm.getDefaultInstance();
            User user = realm.where(User.class).equalTo("email", email).findFirst();
            if (user != null) {
                edtNama.setText(user.getName());
            }
        }

        btnChange.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ChangePoinActivity.class);
            startActivity(intent);
        });

        btnCollect.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MainUIActivity.class);
            startActivity(intent);
        });

        button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), FormulirDonasi.class);
            startActivity(intent);
        });


        button1.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), FormulirDonasi.class);
            startActivity(intent);
        });

        button2.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), FormulirDonasi.class);
            startActivity(intent);
        });



        // Inisialisasi client GPS
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        // Cek dan minta izin lokasi
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation(); // Kalau sudah diizinkan, langsung ambil lokasi
        }

        return root;
    }

    // Ambil lokasi pengguna jika permission sudah diberikan
    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            txtLocation.setText("Permission not granted");
            return;
        }

        try {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(2000);

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    fusedLocationProviderClient.removeLocationUpdates(this);
                    if (locationResult != null && !locationResult.getLocations().isEmpty()) {
                        Location location = locationResult.getLastLocation();
                        getCityName(location.getLatitude(), location.getLongitude());
                    }
                }
            }, Looper.getMainLooper());

        } catch (SecurityException e) {
            txtLocation.setText("SecurityException: No Location Permission");
        }
    }

    // Convert koordinat jadi nama kota
    private void getCityName(double lat, double lon) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            if (addresses != null && !addresses.isEmpty()) {
                String city = addresses.get(0).getLocality();
                txtLocation.setText(city != null ? city : "Unknown City");
            }
        } catch (IOException e) {
            txtLocation.setText("Location error");
        }
    }
}
