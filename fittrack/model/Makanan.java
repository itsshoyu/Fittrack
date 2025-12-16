package com.fittrack.model;

public class Makanan {
    private int logId;
    private String nama;
    private int kalori;
    
    public Makanan(int logId, String nama, int kalori) {
        this.logId = logId;
        this.nama = nama;
        this.kalori = kalori;
    }

    public Makanan(String nama, int kalori) {
        this.nama = nama;
        this.kalori = kalori;
    }

    public int getLogId() {
        return logId;
    }
    
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getKalori() {
        return kalori;
    }

    public void setKalori(int kalori) {
        this.kalori = kalori;
    }
}