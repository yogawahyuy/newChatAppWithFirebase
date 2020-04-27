package com.example.chatwithfirebase.Model;

public class KeluhanModel {
    String nama,tanggal,kategori,unit,keluhan,pesanBalasan,statusBalas,idPembalas,sender;

    public KeluhanModel(String nama, String tanggal, String kategori, String unit, String keluhan, String pesanBalasan, String statusBalas, String idPembalas, String sender) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.kategori = kategori;
        this.unit = unit;
        this.keluhan = keluhan;
        this.pesanBalasan = pesanBalasan;
        this.statusBalas = statusBalas;
        this.idPembalas = idPembalas;
        this.sender = sender;
    }

    public KeluhanModel(String nama, String tanggal, String kategori, String unit, String keluhan, String pesanBalasan, String sender) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.kategori = kategori;
        this.unit = unit;
        this.keluhan = keluhan;
        this.pesanBalasan = pesanBalasan;
        this.sender = sender;
    }

    public KeluhanModel() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getPesanBalasan() {
        return pesanBalasan;
    }

    public void setPesanBalasan(String pesanBalasan) {
        this.pesanBalasan = pesanBalasan;
    }

    public String getStatusBalas() {
        return statusBalas;
    }

    public void setStatusBalas(String statusBalas) {
        this.statusBalas = statusBalas;
    }

    public String getIdPembalas() {
        return idPembalas;
    }

    public void setIdPembalas(String idPembalas) {
        this.idPembalas = idPembalas;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
