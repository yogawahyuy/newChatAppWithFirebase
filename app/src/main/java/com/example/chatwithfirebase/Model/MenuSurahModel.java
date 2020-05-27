package com.example.chatwithfirebase.Model;

public class MenuSurahModel {
    String nameSurah,idTranslation,enTranslation,arTranslation,numberAyah,numberSurah,placeSurah,rectiation,typeSurah;

    public MenuSurahModel(String nameSurah, String idTranslation, String enTranslation, String arTranslation, String numberAyah, String numberSurah, String placeSurah, String rectiation, String typeSurah) {
        this.nameSurah = nameSurah;
        this.idTranslation = idTranslation;
        this.enTranslation = enTranslation;
        this.arTranslation = arTranslation;
        this.numberAyah = numberAyah;
        this.numberSurah = numberSurah;
        this.placeSurah = placeSurah;
        this.rectiation = rectiation;
        this.typeSurah = typeSurah;
    }

    public MenuSurahModel() {
    }

    public String getNameSurah() {
        return nameSurah;
    }

    public void setNameSurah(String nameSurah) {
        this.nameSurah = nameSurah;
    }

    public String getIdTranslation() {
        return idTranslation;
    }

    public void setIdTranslation(String idTranslation) {
        this.idTranslation = idTranslation;
    }

    public String getEnTranslation() {
        return enTranslation;
    }

    public void setEnTranslation(String enTranslation) {
        this.enTranslation = enTranslation;
    }

    public String getArTranslation() {
        return arTranslation;
    }

    public void setArTranslation(String arTranslation) {
        this.arTranslation = arTranslation;
    }

    public String getNumberAyah() {
        return numberAyah;
    }

    public void setNumberAyah(String numberAyah) {
        this.numberAyah = numberAyah;
    }

    public String getNumberSurah() {
        return numberSurah;
    }

    public void setNumberSurah(String numberSurah) {
        this.numberSurah = numberSurah;
    }

    public String getPlaceSurah() {
        return placeSurah;
    }

    public void setPlaceSurah(String placeSurah) {
        this.placeSurah = placeSurah;
    }

    public String getRectiation() {
        return rectiation;
    }

    public void setRectiation(String rectiation) {
        this.rectiation = rectiation;
    }

    public String getTypeSurah() {
        return typeSurah;
    }

    public void setTypeSurah(String typeSurah) {
        this.typeSurah = typeSurah;
    }
}
