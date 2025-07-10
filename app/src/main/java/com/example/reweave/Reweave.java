package com.example.reweave;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Reweave extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Inisialisasi Realm sekali saja untuk seluruh aplikasi
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("default.realm")           // Nama file database
                .schemaVersion(1)                // Versi schema database
                .allowWritesOnUiThread(true)     // Sementara aktifkan untuk demo
                .deleteRealmIfMigrationNeeded()  // Hapus database jika ada perubahan model
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
