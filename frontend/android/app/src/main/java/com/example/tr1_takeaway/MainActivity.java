package com.example.tr1_takeaway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tr1_takeaway.loginService.LoginApiService;
import com.example.tr1_takeaway.loginService.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    EditText usuario;
    EditText contrasenya;

    public final static String EXTRA_USERNAME_TEXT = "USERNAME: ";

    public final static String EXTRA_PASSWORD_TEXT = "PASSWORD: ";
    Intent secondScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        usuario = findViewById(R.id.usernameText);
        contrasenya = findViewById(R.id.passwordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.56.1:3001")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                LoginApiService service = retrofit.create(LoginApiService.class);
                Call<LoginResponse> call = service.validarLogin(usuario.getText().toString(), contrasenya.getText().toString());
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                        if (response.isSuccessful()) {
                            LoginResponse booleanResponse = response.body();
                            if (booleanResponse != null) {
                                boolean resultado = LoginResponse.isLoginBool();
                                Log.d("TAG", "El resultado de la validación de inicio de sesión es: " + resultado);
                                Bundle extras = new Bundle();
                                secondScreen = new Intent(MainActivity.this, ShopActivity.class);
                                extras.putString(EXTRA_USERNAME_TEXT, usuario.getText().toString());
                                extras.putString(EXTRA_PASSWORD_TEXT, contrasenya.getText().toString());
                                secondScreen.putExtras(extras);
                                startActivity(secondScreen);

                            }
                        } else {
                            Log.e("TAG", "Error en la solicitud");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                        Log.e("TAG", "Error en la solicitud: " + t.getMessage());
                    }
                });

            }
        });


    }
}