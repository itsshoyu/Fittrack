package com.fittrack.model;

public class Latihan {
    private String nama;
    private String deskripsi;
    private String imagePath;


    public Latihan(String nama, String deskripsi, String imagePath) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.imagePath = imagePath;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getImagePath(){
        return  imagePath;
    }
}