package com.example.tugas_akhir_pam;

import android.net.Uri;

import java.io.Serializable;

public class location implements Serializable {
    private String nama;
    private String description;
    private String harga;
//    private Uri image;

//    public Uri getImage() {
//        return image;
//    }
//
//    public void setImage(Uri image) {
//        this.image = image;
//    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public location(String nama, String description, String harga) {
        this.nama = nama;
        this.description = description;
        this.harga = harga;
//        this.image = image;
    }

    public location(){}

}
