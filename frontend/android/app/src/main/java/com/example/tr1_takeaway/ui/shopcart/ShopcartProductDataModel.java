package com.example.tr1_takeaway.ui.shopcart;

public class ShopcartProductDataModel {

    private String imgURL;
    private String nom;
    private double preu;
    private int quantitat;

    public ShopcartProductDataModel(String imgURL, String nom, double preu, int quantitat) {
        this.imgURL = imgURL;
        this.nom = nom;
        this.preu = preu;
        this.quantitat = quantitat;
    }


    public String getImageUrl() {
        return imgURL;
    }
    public void setImageUrl(String url_img) {
        this.imgURL = url_img;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

}