package com.rsip.mobile.Model;

public class AyatModel {
    String namaSurah,namaTranslation,typeSurah,numberAyat,textArab,idTranslation,rectiation,nameRectitation,jumlahAyat,idTranslationAyat;


    public AyatModel(String namaSurah, String namaTranslation, String typeSurah, String numberAyat, String textArab, String idTranslation, String rectiation, String nameRectitation, String jumlahAyat, String idTranslationAyat) {
        this.namaSurah = namaSurah;
        this.namaTranslation = namaTranslation;
        this.typeSurah = typeSurah;
        this.numberAyat = numberAyat;
        this.textArab = textArab;
        this.idTranslation = idTranslation;
        this.rectiation = rectiation;
        this.nameRectitation = nameRectitation;
        this.jumlahAyat = jumlahAyat;
        this.idTranslationAyat = idTranslationAyat;
    }

    public AyatModel() {
    }

    public String getNamaSurah() {
        return namaSurah;
    }

    public void setNamaSurah(String namaSurah) {
        this.namaSurah = namaSurah;
    }

    public String getNamaTranslation() {
        return namaTranslation;
    }

    public void setNamaTranslation(String namaTranslation) {
        this.namaTranslation = namaTranslation;
    }

    public String getTypeSurah() {
        return typeSurah;
    }

    public void setTypeSurah(String typeSurah) {
        this.typeSurah = typeSurah;
    }

    public String getNumberAyat() {
        return numberAyat;
    }

    public void setNumberAyat(String numberAyat) {
        this.numberAyat = numberAyat;
    }

    public String getTextArab() {
        return textArab;
    }

    public void setTextArab(String textArab) {
        this.textArab = textArab;
    }

    public String getIdTranslation() {
        return idTranslation;
    }

    public void setIdTranslation(String idTranslation) {
        this.idTranslation = idTranslation;
    }

    public String getRectiation() {
        return rectiation;
    }

    public void setRectiation(String rectiation) {
        this.rectiation = rectiation;
    }
    public String getJumlahAyat() {
        return jumlahAyat;
    }

    public void setJumlahAyat(String jumlahAyat) {
        this.jumlahAyat = jumlahAyat;
    }

    public String getIdTranslationAyat() {
        return idTranslationAyat;
    }

    public void setIdTranslationAyat(String idTranslationAyat) {
        this.idTranslationAyat = idTranslationAyat;
    }

    public String getNameRectitation() {
        return nameRectitation;
    }

    public void setNameRectitation(String nameRectitation) {
        this.nameRectitation = nameRectitation;
    }
}
