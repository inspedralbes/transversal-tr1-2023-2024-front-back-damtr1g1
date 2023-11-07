package com.example.tr1_takeaway.ui.profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tr1_takeaway.MainActivity;
import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.databinding.FragmentProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private TextView username, name, cognoms, correu_electronic, creditCardNumber, creditCardExpirationDate, creditCardCCV;
    private FragmentProfileBinding binding;
    private EditText EditTextexpirydateText, EditTextname, EditTextcognoms, EditTextcreditcard, EditTextcreditCardNumber, EditTextemailText, EditTextccvText, EditTextemail, EditTextusername;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Context context = getActivity();

        // Obtén el ID del usuario de SharedPreferences en otra clase
        assert context != null;
        SharedPreferences prefs = context.getSharedPreferences("NombrePreferencias", MODE_PRIVATE);
        String userId = prefs.getString("IDUsuario", "ValorPredeterminadoSiNoSeEncuentra");

        this.username = binding.username;
        this.name = binding.name;
        this.cognoms = binding.cognoms;
        this.correu_electronic = binding.emailText;
        this.creditCardNumber = binding.creditCardNumber;
        this.creditCardExpirationDate = binding.expirydateText;
        this.creditCardCCV = binding.ccvText;

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.168.205.249:3001")
                .baseUrl("http://10.2.2.83:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProfileApiService apiService = retrofit.create(ProfileApiService.class);


        Call<ProfileDataParse> call = apiService.getUserDataByName(userId);
        call.enqueue(new Callback<ProfileDataParse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileDataParse> call, @NonNull Response<ProfileDataParse> response) {
                if (response.isSuccessful()) {
                    Log.d("Profile", "response true");
                    ProfileDataParse profileDataParse = response.body();
                    if (profileDataParse != null) {
                        Log.d("Porfile", "Profile is not null");
                        username.setText(profileDataParse.getNom());
                        name.setText(profileDataParse.getNom_real());
                        correu_electronic.setText(profileDataParse.getCorreu_electronic());
                        creditCardNumber.setText(profileDataParse.getNumero_targeta());
                        creditCardExpirationDate.setText(profileDataParse.getData_caducitat_targeta());
                        String ccvTexto = String.valueOf(profileDataParse.getCvv_targeta()); // Pasem el int a String
                        creditCardCCV.setText(ccvTexto); // L'impimim per pantalla
                    }
                } else {
                    Log.e("TAG", "Error: " + response.message());
                }

            }
            @Override
            public void onFailure(@NonNull Call<ProfileDataParse> call, @NonNull Throwable t) {
                Log.e("TAG", "Error: " + t.getMessage());
            }
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}