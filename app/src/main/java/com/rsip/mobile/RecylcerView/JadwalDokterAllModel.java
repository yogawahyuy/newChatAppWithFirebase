package com.rsip.mobile.RecylcerView;

import java.util.ArrayList;

public class JadwalDokterAllModel {

    String kd_poliklinikx, nm_poliklinikx,nip_dokterx,nm_dokterx,harix,tglx,jam_mulaix,jam_selesaix;

    private ArrayList<JadwalDokterAllModel> singleItemModelArrayList;

    public JadwalDokterAllModel(String kd_poliklinikx, String nm_poliklinikx, String nip_dokterx, String nm_dokterx, String harix, String tglx, String jam_mulaix, String jam_selesaix) {
        this.kd_poliklinikx = kd_poliklinikx;
        this.nm_poliklinikx = nm_poliklinikx;
        this.nip_dokterx = nip_dokterx;
        this.nm_dokterx = nm_dokterx;
        this.harix = harix;
        this.tglx = tglx;
        this.jam_mulaix = jam_mulaix;
        this.jam_selesaix = jam_selesaix;
    }

    public JadwalDokterAllModel(String harix,ArrayList<JadwalDokterAllModel> singleItemModelArrayList) {
        this.harix = harix;
        this.singleItemModelArrayList = singleItemModelArrayList;
    }

    public JadwalDokterAllModel() {

    }

    public String getKd_poliklinikx() {
        return kd_poliklinikx;
    }

    public void setKd_poliklinikx(String kd_poliklinikx) {
        this.kd_poliklinikx = kd_poliklinikx;
    }

    public String getNm_poliklinikx() {
        return nm_poliklinikx;
    }

    public void setNm_poliklinikx(String nm_poliklinikx) {
        this.nm_poliklinikx = nm_poliklinikx;
    }

    public String getNip_dokterx() {
        return nip_dokterx;
    }

    public void setNip_dokterx(String nip_dokterx) {
        this.nip_dokterx = nip_dokterx;
    }

    public String getNm_dokterx() {
        return nm_dokterx;
    }

    public void setNm_dokterx(String nm_dokterx) {
        this.nm_dokterx = nm_dokterx;
    }

    public String getHarix() {
        return harix;
    }

    public void setHarix(String harix) {
        this.harix = harix;
    }

    public String getTglx() {
        return tglx;
    }

    public void setTglx(String tglx) {
        this.tglx = tglx;
    }

    public String getJam_mulaix() {
        return jam_mulaix;
    }

    public void setJam_mulaix(String jam_mulaix) {
        this.jam_mulaix = jam_mulaix;
    }

    public String getJam_selesaix() {
        return jam_selesaix;
    }

    public void setJam_selesaix(String jam_selesaix) {
        this.jam_selesaix = jam_selesaix;
    }

    public ArrayList<JadwalDokterAllModel> getSingleItemArrayList() {
        return singleItemModelArrayList;
    }

    public void setSingleItemArrayList(ArrayList<JadwalDokterAllModel> singleItemModelArrayList) {
        this.singleItemModelArrayList = singleItemModelArrayList;
    }
}
