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
import com.example.reweave.Model.Point;
import com.example.reweave.Model.User;
import com.example.reweave.NotificationActivity;
import com.example.reweave.R;

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

    private TextView edtNama, txtLocation, tvPointHome;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private ImageButton btnCollect, btnChange, btnot;
    private Button button, button1, button2;
    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        edtNama = root.findViewById(R.id.edtNama);
        txtLocation = root.findViewById(R.id.location);
        tvPointHome = root.findViewById(R.id.tv_point_home);
        btnCollect = root.findViewById(R.id.btnCollect);
        btnChange = root.findViewById(R.id.btnChange);
        btnot = root.findViewById(R.id.imagenot);
        button = root.findViewById(R.id.button);
        button1 = root.findViewById(R.id.button1);
        button2 = root.findViewById(R.id.button2);

        Realm.init(requireContext());
        realm = Realm.getDefaultInstance();

        SharedPreferences preferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE);
        String email = preferences.getString("user_email", null);

        if (email != null) {
            // Ambil nama user
            User user = realm.where(User.class).equalTo("email", email).findFirst();
            if (user != null) {
                edtNama.setText(user.getName());
            }

            // Ambil dan tampilkan poin user
            Point point = realm.where(Point.class).equalTo("email", email).findFirst();
            if (point != null) {
                tvPointHome.setText(point.getPoints() + " Points");
            } else {
                tvPointHome.setText("0 Points");
            }
        }

        // Navigasi tombol
        btnChange.setOnClickListener(v -> startActivity(new Intent(requireContext(), ChangePoinActivity.class)));
        btnCollect.setOnClickListener(v -> startActivity(new Intent(requireContext(), MainUIActivity.class)));
        button.setOnClickListener(v -> startActivity(new Intent(requireContext(), FormulirDonasi.class)));
        button1.setOnClickListener(v -> startActivity(new Intent(requireContext(), FormulirDonasi.class)));
        button2.setOnClickListener(v -> startActivity(new Intent(requireContext(), FormulirDonasi.class)));
        btnot.setOnClickListener(v -> startActivity(new Intent(requireContext(), NotificationActivity.class)));

        // Inisialisasi GPS
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }

        return root;
    }

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

    @Override
    public void onResume() {
        super.onResume();
        // Update poin setiap kali kembali ke HomeFragment
        updatePointDisplay();
    }

    private void updatePointDisplay() {
        SharedPreferences preferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE);
        String email = preferences.getString("user_email", null);

        if (email != null && realm != null && !realm.isClosed()) {
            Point point = realm.where(Point.class).equalTo("email", email).findFirst();
            if (point != null) {
                tvPointHome.setText(point.getPoints() + " Points");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
