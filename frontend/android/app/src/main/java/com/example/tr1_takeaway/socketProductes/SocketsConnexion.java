package com.example.tr1_takeaway.socketProductes;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tr1_takeaway.R;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketsConnexion extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shopcart);

        /*
        private String imgURL;
        private String nom;
        private double preu;
        private int quantitat;
        */

        //buyShopcartButton.setOnClickListener(v->{
            // Al enviar la comanda inicializa el socket
            try {
                Socket socket = IO.socket("http://192.168.205.249:3001"); // Reemplaza con la URL de tu servidor
                socket.connect();

                socket.emit();


            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

    }

}
