package com.example.reweave.Model;



import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FlashSale extends RealmObject {
    @PrimaryKey
    private int produkFlashSaleID;
    private String NamaBarangFlashSale, Detail, HargaAwal, HargaDiskon;;

    private int GambarProduk;


    public FlashSale(){}

    public FlashSale(int produkFlashSaleID, String hargaAwal, String hargaDiskon, String detail, String namaBarangFlashSale, int gambarProduk) {
        this.produkFlashSaleID = produkFlashSaleID;
        HargaAwal = hargaAwal;
        HargaDiskon = hargaDiskon;
        Detail = detail;
        NamaBarangFlashSale = namaBarangFlashSale;
        GambarProduk = gambarProduk;
    }

    public int getProdukFlashSaleID() {
        return produkFlashSaleID;
    }

    public void setProdukFlashSaleID(int produkFlashSaleID) {
        this.produkFlashSaleID = produkFlashSaleID;
    }

    public String getHargaDiskon() {
        return HargaDiskon;
    }

    public void setHargaDiskon(String hargaDiskon) {
        HargaDiskon = hargaDiskon;
    }

    public String getHargaAwal() {
        return HargaAwal;
    }

    public void setHargaAwal(String hargaAwal) {
        HargaAwal = hargaAwal;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getNamaBarangFlashSale() {
        return NamaBarangFlashSale;
    }

    public void setNamaBarangFlashSale(String namaBarangFlashSale) {
        NamaBarangFlashSale = namaBarangFlashSale;
    }

    public int getGambarProduk() {
        return GambarProduk;
    }

    public void setGambarProduk(int gambarProduk) {
        GambarProduk = gambarProduk;
    }
}
