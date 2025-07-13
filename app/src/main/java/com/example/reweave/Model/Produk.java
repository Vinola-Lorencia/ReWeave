package com.example.reweave.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Produk extends RealmObject {
    @PrimaryKey
    private int ProdukID;
    private int Gambar, Harga;
    private String NamaProduk, Detail;
    private boolean onSale;
    private String kategori;
    private double rating, like;
    public Produk(){}




    public Produk(int produkID, String namaProduk, int harga, int gambar, boolean onsale, String kategori, double rating, double like, String detail) {
        this.ProdukID = produkID;
        NamaProduk = namaProduk;
        Harga = harga;
        Gambar = gambar;
        onSale = onsale;
        this.kategori = kategori;
        this.like = like;
        this.rating = rating;
        Detail = detail;


    }

    public int getProdukID() {
        return ProdukID;
    }

    public void setProdukID(int produkID) {
        ProdukID = produkID;
    }

    public String getNamaProduk() {
        return NamaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        NamaProduk = namaProduk;
    }

    public int getHarga() {
        return Harga;
    }

    public void setHarga(int harga) {
        Harga = harga;
    }

    public int getGambar() {
        return Gambar;
    }

    public void setGambar(int gambar) {
        Gambar = gambar;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getLike() {
        return like;
    }

    public void setLike(double like) {
        this.like = like;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }
}
