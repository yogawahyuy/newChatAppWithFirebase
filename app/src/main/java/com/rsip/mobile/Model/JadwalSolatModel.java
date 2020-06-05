package com.rsip.mobile.Model;

public class JadwalSolatModel {
    String lokasi,tanggal,subuh,imsak,duha,duhur,azhar,magrib,isya;

    public JadwalSolatModel(String lokasi, String tanggal, String subuh, String imsak, String duha, String duhur, String azhar, String magrib, String isya) {
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.subuh = subuh;
        this.imsak = imsak;
        this.duha = duha;
        this.duhur = duhur;
        this.azhar = azhar;
        this.magrib = magrib;
        this.isya = isya;
    }

    public JadwalSolatModel() {
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getSubuh() {
        return subuh;
    }

    public void setSubuh(String subuh) {
        this.subuh = subuh;
    }

    public String getImsak() {
        return imsak;
    }

    public void setImsak(String imsak) {
        this.imsak = imsak;
    }

    public String getDuha() {
        return duha;
    }

    public void setDuha(String duha) {
        this.duha = duha;
    }

    public String getDuhur() {
        return duhur;
    }

    public void setDuhur(String duhur) {
        this.duhur = duhur;
    }

    public String getAzhar() {
        return azhar;
    }

    public void setAzhar(String azhar) {
        this.azhar = azhar;
    }

    public String getMagrib() {
        return magrib;
    }

    public void setMagrib(String magrib) {
        this.magrib = magrib;
    }

    public String getIsya() {
        return isya;
    }

    public void setIsya(String isya) {
        this.isya = isya;
    }
}
