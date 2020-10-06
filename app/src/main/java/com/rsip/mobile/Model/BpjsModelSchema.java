package com.rsip.mobile.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BpjsModelSchema {

    @SerializedName("nomorkartu")
    @Expose
    private String nomorkartu;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("notelp")
    @Expose
    private String notelp;
    @SerializedName("tanggalperiksa")
    @Expose
    private String tanggalperiksa;
    @SerializedName("kodepoli")
    @Expose
    private String kodepoli;
    @SerializedName("nomorreferensi")
    @Expose
    private String nomorreferensi;
    @SerializedName("jenisreferensi")
    @Expose
    private Integer jenisreferensi;
    @SerializedName("jenisrequest")
    @Expose
    private Integer jenisrequest;
    @SerializedName("polieksekutif")
    @Expose
    private Integer polieksekutif;

    public String getNomorkartu() {
        return nomorkartu;
    }

    public void setNomorkartu(String nomorkartu) {
        this.nomorkartu = nomorkartu;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getTanggalperiksa() {
        return tanggalperiksa;
    }

    public void setTanggalperiksa(String tanggalperiksa) {
        this.tanggalperiksa = tanggalperiksa;
    }

    public String getKodepoli() {
        return kodepoli;
    }

    public void setKodepoli(String kodepoli) {
        this.kodepoli = kodepoli;
    }

    public String getNomorreferensi() {
        return nomorreferensi;
    }

    public void setNomorreferensi(String nomorreferensi) {
        this.nomorreferensi = nomorreferensi;
    }

    public Integer getJenisreferensi() {
        return jenisreferensi;
    }

    public void setJenisreferensi(Integer jenisreferensi) {
        this.jenisreferensi = jenisreferensi;
    }

    public Integer getJenisrequest() {
        return jenisrequest;
    }

    public void setJenisrequest(Integer jenisrequest) {
        this.jenisrequest = jenisrequest;
    }

    public Integer getPolieksekutif() {
        return polieksekutif;
    }

    public void setPolieksekutif(Integer polieksekutif) {
        this.polieksekutif = polieksekutif;
    }

}