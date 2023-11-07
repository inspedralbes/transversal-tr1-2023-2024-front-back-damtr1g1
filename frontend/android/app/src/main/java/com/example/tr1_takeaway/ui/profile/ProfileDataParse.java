package com.example.tr1_takeaway.ui.profile;

import java.util.Date;

public class ProfileDataParse {
    public String nom, contrasenya, nom_real, cognoms, correu_electronic, numero_targeta, data_caducitat_targeta;
    public int cvv_targeta;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getNom_real() {
        return nom_real;
    }

    public void setNom_real(String nom_real) {
        this.nom_real = nom_real;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getCorreu_electronic() {
        return correu_electronic;
    }

    public void setCorreu_electronic(String correu_electronic) {
        this.correu_electronic = correu_electronic;
    }

    public String getNumero_targeta() {
        return numero_targeta;
    }

    public void setNumero_targeta(String numero_targeta) {
        this.numero_targeta = numero_targeta;
    }

    public int getCvv_targeta() {
        return cvv_targeta;
    }

    public void setCvv_targeta(int cvv_targeta) {
        this.cvv_targeta = cvv_targeta;
    }

    public String getData_caducitat_targeta() {
        return data_caducitat_targeta;
    }

    public void setData_caducitat_targeta(String data_caducitat_targeta) {
        this.data_caducitat_targeta = data_caducitat_targeta;
    }
}
