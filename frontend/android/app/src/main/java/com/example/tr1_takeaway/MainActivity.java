package com.example.tr1_takeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    EditText username;
    EditText passw;

    public final static String EXTRA_USERNAME_TEXT = "USERNAME: ";

    public final static String EXTRA_PASSWORD_TEXT = "PASSWORD: ";
    Intent secondScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        username = findViewById(R.id.usernameText);
        passw = findViewById(R.id.passwordText);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                secondScreen = new Intent(MainActivity.this, ShopActivity.class);
                extras.putString(EXTRA_USERNAME_TEXT, username.getText().toString());
                extras.putString(EXTRA_PASSWORD_TEXT, passw.getText().toString());
                secondScreen.putExtras(extras);


                startActivity(secondScreen);
            }
        });
    }
}