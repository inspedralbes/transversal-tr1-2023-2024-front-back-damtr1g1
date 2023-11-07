package com.example.tr1_takeaway.socketProductes;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.ui.shop.ProductDataModel;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketsConnexion extends AppCompatActivity {
    ProductDataModel PD = new ProductDataModel();
    int idProducte = PD.getId();
    String nomProducte = PD.getNom();
    Double preuProducte = PD.getPreu();
    int quantitatProducte = 1;
    String imatgeURL = PD.getImageUrl();

    public void GuardaProdCarrito(){
        try {
            Socket socket = IO.socket("http://192.168.205.99:3001"); // Reemplaza con la URL de tu servidor
            socket.connect();

            socket.emit("CrearCarrito", idProducte, nomProducte, preuProducte, quantitatProducte, imatgeURL);


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shopcart);
    }
    ;

}

