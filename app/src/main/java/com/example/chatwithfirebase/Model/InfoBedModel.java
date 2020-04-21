package com.example.chatwithfirebase.Model;

public class InfoBedModel {

    String namaRuang,kodeRuang,kodeKelas,kapasitas,tersedia;

    public InfoBedModel(String namaRuang, String kodeRuang, String kodeKelas, String kapasitas, String tersedia) {
        this.namaRuang = namaRuang;
        this.kodeRuang = kodeRuang;
        this.kodeKelas = kodeKelas;
        this.kapasitas = kapasitas;
        this.tersedia = tersedia;
    }

    public InfoBedModel() {
    }

    public String getNamaRuang() {
        return namaRuang;
    }

    public void setNamaRuang(String namaRuang) {
        this.namaRuang = namaRuang;
    }

    public String getKodeRuang() {
        return kodeRuang;
    }

    public void setKodeRuang(String kodeRuang) {
        this.kodeRuang = kodeRuang;
    }

    public String getKodeKelas() {
        return kodeKelas;
    }

    public void setKodeKelas(String kodeKelas) {
        this.kodeKelas = kodeKelas;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getTersedia() {
        return tersedia;
    }

    public void setTersedia(String tersedia) {
        this.tersedia = tersedia;
    }
}
