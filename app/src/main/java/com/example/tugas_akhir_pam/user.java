package com.example.tugas_akhir_pam;

import java.util.ArrayList;

public class user {
    public String nama;
    public String email;
    public String password;
    public ArrayList<String> judul = new ArrayList<>();

    public user(String nama, String email,String password) {
        this.nama = nama;
        this.email = email;
        this.password = password;
    }

    public user(){}

    public user(String nama, String email,String password, String judul) {
        this.nama = nama;
        this.email = email;
        this.password= password;
        this.judul.add(judul);
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul.add(judul);
    }
}
