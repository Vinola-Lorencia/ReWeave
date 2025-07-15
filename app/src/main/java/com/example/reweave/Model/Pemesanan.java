package com.example.reweave.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Pemesanan extends RealmObject {

    @PrimaryKey
    private String id;
    private String namaProduk;
    private int harga;
    private int jumlah;
    private int total;
    private String alamat;
    private boolean isGift;
    private int gambar;

    public Pemesanan() {
    }

    public Pemesanan(String id, String namaProduk, int harga, int jumlah, int total, String alamat, boolean isGift, int gambar) {
        this.id = id;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.jumlah = jumlah;
        this.total = total;
        this.alamat = alamat;
        this.isGift = isGift;
        this.gambar = gambar;
        ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public boolean isGift() {
        return isGift;
    }

    public void setGift(boolean gift) {
        isGift = gift;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }
}
