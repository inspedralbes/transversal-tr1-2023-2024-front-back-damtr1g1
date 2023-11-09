package com.example.tr1_takeaway.ui.shopcart;

import com.example.tr1_takeaway.MainActivity;

public class ShopcartProductDataModel {

    private String imatgeNom;
    private String nom;

    private int id;
    private double preu;
    private int quantitat;


    public ShopcartProductDataModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        MainActivity MA = new MainActivity();
        return MA.URL+"/api/getImage/" +imatgeNom;
    }

    public void setImageUrl(String url_img) {
        this.imatgeNom = url_img;
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