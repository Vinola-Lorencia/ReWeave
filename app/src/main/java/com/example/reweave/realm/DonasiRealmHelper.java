package com.example.reweave.realm;

import com.example.reweave.Model.Donasi;

import io.realm.Realm;
import io.realm.RealmResults;

public class DonasiRealmHelper {

    private Realm realm;

    public DonasiRealmHelper() {
        realm = Realm.getDefaultInstance();
    }

    public RealmResults<Donasi> getAllDonasi() {
        return realm.where(Donasi.class).findAll();
    }

    public void deleteAllDonasi() {
        realm.executeTransaction(r -> {
            RealmResults<Donasi> donasiList = r.where(Donasi.class).findAll();
            donasiList.deleteAllFromRealm();
        });
    }

    public void deleteDonasiById(String id) {
        realm.executeTransaction(r -> {
            Donasi donasi = r.where(Donasi.class).equalTo("id", id).findFirst();
            if (donasi != null) donasi.deleteFromRealm();
        });
    }

    public void insertDonasi(Donasi donasi) {
        realm.executeTransaction(r -> r.insert(donasi));
    }

    public void close() {
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}
