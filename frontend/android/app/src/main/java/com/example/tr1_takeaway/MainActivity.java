package com.example.tr1_takeaway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tr1_takeaway.api.loginService.LoginApiService;
import com.example.tr1_takeaway.api.loginService.LoginResponse;
import com.example.tr1_takeaway.ui.shopcart.AddShoppingCartToNode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public String URL = "http://192.168.205.249:3001"; //"http://192.168.205.249:3001" URL Ramon

    Button loginButton;
    EditText nom, contrasenya;
    String UsernameText, UserPasswordText;
    public String getUsernameText() {
        return UsernameText;
    }
    public final static String EXTRA_USERNAME_TEXT = "USERNAME: ";

    public final static String EXTRA_PASSWORD_TEXT = "PASSWORD: ";
    Intent secondScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        nom = findViewById(R.id.usernameText);
        contrasenya = findViewById(R.id.passwordText);

        AddShoppingCartToNode AddCarrito = new AddShoppingCartToNode();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginApiService service = retrofit.create(LoginApiService.class);

        loginButton.setOnClickListener(view -> {

            UsernameText = nom.getText().toString();
            UserPasswordText = contrasenya.getText().toString();

            Call<LoginResponse> call = service.validateLogin(UsernameText, UserPasswordText);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse != null) {
                            boolean resultado = loginResponse.isLoginBool();
                            Log.d("TAG", "El resultado de la validación de inicio de sesión es: " + resultado);
                            if(resultado) {
                                // Aquí puedes almacenar el ID del usuario en SharedPreferences
                                String userId = nom.getText().toString();
                                SharedPreferences.Editor editor = getSharedPreferences("NombrePreferencias", MODE_PRIVATE).edit();
                                editor.putString("IDUsuario", userId);
                                editor.apply();
                                AddCarrito.CreateShoppingCart(userId);
                                Bundle extras = new Bundle();
                                secondScreen = new Intent(MainActivity.this, ShopActivity.class);
                                extras.putString(EXTRA_USERNAME_TEXT, nom.getText().toString());
                                extras.putString(EXTRA_PASSWORD_TEXT, contrasenya.getText().toString());
                                secondScreen.putExtras(extras);
                                startActivity(secondScreen);
                            } else{
                                Log.d("TAG", String.valueOf(loginResponse.isLoginBool()));
                                Log.e("TAG", "Error al iniciar");
                            }
                        }
                    } else {
                        Log.e("TAG", "Error en la solicitud");
                    }
                }
                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    Log.e("TAG", "Error en la solicitud: " + t.getMessage());
                    t.printStackTrace(); // Imprimir el seguimiento de la pila para obtener más detalles sobre el error
                }
            });

        });
    }

}