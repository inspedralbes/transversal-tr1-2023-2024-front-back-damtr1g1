package com.example.tr1_takeaway.ui.shop;

public class ProductDataModel {

    private int id;
    private String imgURL;
    private String nom;
    private String definicio;
    private double preu;
    private int categoria_id;
    private int quantitat;

    public ProductDataModel(){

    }
    public ProductDataModel(int id, String imgURL, String nom, String definicio, double preu, int categoria_id, int quantitat) {
        this.id = id;
        this.imgURL = imgURL;
        this.nom = nom;
        this.definicio = definicio;
        this.preu = preu;
        this.categoria_id = categoria_id;
        this.quantitat = quantitat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDefinicio() {
        return definicio;
    }

    public void setDefinicio(String definicio) {
        this.definicio = definicio;
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