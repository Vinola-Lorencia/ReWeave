package com.example.reweave;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reweave.Adapter.PoinNotificationAdapter;
import com.example.reweave.Model.PoinNotification;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class NotificationActivity extends AppCompatActivity {

    private ListView listView;
    private PoinNotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Realm.init(this);
        listView = findViewById(R.id.listviewnotif);

        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String userEmail = prefs.getString("user_email", "");

        Realm realm = Realm.getDefaultInstance();
        RealmResults<PoinNotification> data = realm.where(PoinNotification.class)
                .equalTo("email", userEmail)
                .sort("date", Sort.DESCENDING)
                .findAll();

        adapter = new PoinNotificationAdapter(this, data);
        listView.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.imageButton2);
        backButton.setOnClickListener(v -> finish());
    }
}
