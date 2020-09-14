package com.rsip.mobile.View;

import android.net.Uri;

import java.util.ArrayList;

public class SemuaDokterModel {

    private String namaDokter;//alias namadokter

    private String spesialistik;//alias spesialistik

    private String idDokter,hariPraktik,jamPraktik,status,keterangan;
    String urlPhoto;
    private String jamPraktikKedua,hariPraktikKedua;
    private Uri uri;
    private String key;


    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public SemuaDokterModel() {

    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getSpesialistik() {
        return spesialistik;
    }

    public void setSpesialistik(String spesialistik) {
        this.spesialistik = spesialistik;
    }

    public String getIdDokter() {
        return idDokter;
    }

    public void setIdDokter(String idDokter) {
        this.idDokter = idDokter;
    }

    public String getHariPraktik() {
        return hariPraktik;
    }

    public void setHariPraktik(String hariPraktik) {
        this.hariPraktik = hariPraktik;
    }

    public String getJamPraktik() {
        return jamPraktik;
    }

    public void setJamPraktik(String jamPraktik) {
        this.jamPraktik = jamPraktik;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getJamPraktikKedua() {
        return jamPraktikKedua;
    }

    public void setJamPraktikKedua(String jamPraktikKedua) {
        this.jamPraktikKedua = jamPraktikKedua;
    }

    public String getHariPraktikKedua() {
        return hariPraktikKedua;
    }

    public void setHariPraktikKedua(String hariPraktikKedua) {
        this.hariPraktikKedua = hariPraktikKedua;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
