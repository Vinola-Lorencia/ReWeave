package com.example.reweave.Model;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PoinNotification extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String email;
    private String promoTitle;
    private int pointsRedeemed;
    private Date date;
    private String alamat;



    // Constructor kosong (wajib untuk Realm)
    public PoinNotification() {}

    // Constructor dengan parameter
    public PoinNotification(String email, String promoTitle, int pointsRedeemed, Date date, String alamat) {
        this.email = email;
        this.promoTitle = promoTitle;
        this.pointsRedeemed = pointsRedeemed;
        this.date = date;
        this.alamat=alamat;

    }

    // Getter & Setter
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPromoTitle() {
        return promoTitle;
    }

    public int getPointsRedeemed() {
        return pointsRedeemed;
    }

    public Date getDate() {
        return date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
    }

    public void setPointsRedeemed(int pointsRedeemed) {
        this.pointsRedeemed = pointsRedeemed;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
