package com.example.tr1_takeaway.ui.shopcart;

public class ShoppingCartProduct {
    public ShoppingCartProduct(int quantitat, int id_carret, int id_producte) {
        this.quantitat = quantitat;
        this.id_carret = id_carret;
        this.id_producte = id_producte;
    }

    private int quantitat;
    private int id_carret;
    private int id_producte;

    public int getQuantitat() {
        return quantitat;
    }
    public int getId_carret() {
        return id_carret;
    }

    public int getId_producte() {
        return id_producte;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public void setId_carret(int id_carret) {
        this.id_carret = id_carret;
    }

    public void setId_producte(int id_producte) {
        this.id_producte = id_producte;
    }
}

