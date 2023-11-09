package com.example.tr1_takeaway.ui.shop;

public class ProductDataModel {

    private int id;
    private String imatgeNom;
    private String nom;
    private double preu;
    private int categoria_id;
    private int quantitat;

    public ProductDataModel(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return "http://192.168.1.195:3001/api/getImage/"+imatgeNom;
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

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }
}