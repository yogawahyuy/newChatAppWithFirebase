package com.rsip.mobile.RecylcerView;

import java.util.ArrayList;

public class JadwalOperasiModel {
    private String noRegis,kamar,tanggalOperasi,jamOperasi,icd10,icd9,operator,spesialistik,lastupdate;

    public JadwalOperasiModel(String noRegis, String kamar, String tanggalOperasi, String jamOperasi, String icd10, String icd9, String operator, String spesialistik, String lastupdate) {

        this.noRegis = noRegis;
        this.kamar = kamar;
        this.tanggalOperasi = tanggalOperasi;
        this.jamOperasi = jamOperasi;
        this.icd10 = icd10;
        this.icd9 = icd9;
        this.operator = operator;
        this.spesialistik = spesialistik;
        this.lastupdate = lastupdate;
    }

    public JadwalOperasiModel() {
    }



    public String getNoRegis() {
        return noRegis;
    }

    public void setNoRegis(String noRegis) {
        this.noRegis = noRegis;
    }

    public String getKamar() {
        return kamar;
    }

    public void setKamar(String kamar) {
        this.kamar = kamar;
    }

    public String getTanggalOperasi() {
        return tanggalOperasi;
    }

    public void setTanggalOperasi(String tanggalOperasi) {
        this.tanggalOperasi = tanggalOperasi;
    }

    public String getJamOperasi() {
        return jamOperasi;
    }

    public void setJamOperasi(String jamOperasi) {
        this.jamOperasi = jamOperasi;
    }

    public String getIcd10() {
        return icd10;
    }

    public void setIcd10(String icd10) {
        this.icd10 = icd10;
    }

    public String getIcd9() {
        return icd9;
    }

    public void setIcd9(String icd9) {
        this.icd9 = icd9;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSpesialistik() {
        return spesialistik;
    }

    public void setSpesialistik(String spesialistik) {
        this.spesialistik = spesialistik;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }
}
