package com.example.reweave.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Keranjang extends RealmObject {

    @PrimaryKey
    private String id;

    private int produkID;
    private String namaProduk;
    private int harga;
    private int gambar;
    private int kuantitas;

    public Keranjang() {}

    public Keranjang(String id, int produkID, String namaProduk, int harga, int gambar, int kuantitas) {
        this.id = id;
        this.produkID = produkID;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.gambar = gambar;
        this.kuantitas = kuantitas;
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProdukID() {
        return produkID;
    }

    public void setProdukID(int produkID) {
        this.produkID = produkID;
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

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }
}
