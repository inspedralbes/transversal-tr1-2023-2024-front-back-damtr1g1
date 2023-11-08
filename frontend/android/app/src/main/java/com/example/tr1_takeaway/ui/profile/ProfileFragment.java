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

        Button editarProfile = root.findViewById(R.id.EditarProfile);
        Button guardarNewProfile = root.findViewById(R.id.GuardarProfile);

        EditText EditTextexpirydateText = root.findViewById(R.id.EditTextexpirydateText);
        EditText EditTextname = root.findViewById(R.id.EditTextname);
        EditText EditTextcognoms = root.findViewById(R.id.EditTextcognoms);
        EditText EditTextcreditcard = root.findViewById(R.id.EditTextcreditcard);
        EditText EditTextcreditCardNumber = root.findViewById(R.id.EditTextcreditCardNumber);
        EditText EditTextemailText = root.findViewById(R.id.EditTextemailText);
        EditText EditTextccvText = root.findViewById(R.id.EditTextccvText);
        EditText EditTextemail = root.findViewById(R.id.EditTextemail);
        EditText EditTextusername = root.findViewById(R.id.EditTextusername);


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
                .baseUrl("http://192.168.205.249:3001")
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

        editarProfile.setOnClickListener(v->{
            EditTextexpirydateText.setVisibility(View.VISIBLE);
            EditTextname.setVisibility(View.VISIBLE);
            EditTextcognoms.setVisibility(View.VISIBLE);
            EditTextcreditcard.setVisibility(View.VISIBLE);
            EditTextcreditCardNumber.setVisibility(View.VISIBLE);
            EditTextemailText.setVisibility(View.VISIBLE);
            EditTextccvText.setVisibility(View.VISIBLE);
            EditTextemail.setVisibility(View.VISIBLE);
            EditTextusername.setVisibility(View.VISIBLE);
            guardarNewProfile.setVisibility(View.VISIBLE);

            this.username.setVisibility(View.INVISIBLE);
            this.name.setVisibility(View.INVISIBLE);
            this.cognoms.setVisibility(View.INVISIBLE);
            this.correu_electronic.setVisibility(View.INVISIBLE);
            this.creditCardNumber.setVisibility(View.INVISIBLE);
            this.creditCardExpirationDate.setVisibility(View.INVISIBLE);
            this.creditCardCCV.setVisibility(View.INVISIBLE);
            editarProfile.setVisibility(View.INVISIBLE);
        });

        /*guardarNewProfile.setOnClickListener(v -> {
            // Recopilar los datos de los EditTexts
            String newName = EditTextname.getText().toString();
            String newCognoms = EditTextcognoms.getText().toString();
            String newCorreuElectronic = EditTextemailText.getText().toString();
            String newCreditCardNumber = EditTextcreditCardNumber.getText().toString();
            String newCreditCardExpirationDate = EditTextexpirydateText.getText().toString();
            String newCreditCardCCV = EditTextccvText.getText().toString();

            // Crear un objeto con los datos modificados
            ProfileDataParse updatedData = new EditProfileDataParse(newName, newCognoms, newCorreuElectronic, newCreditCardNumber, newCreditCardExpirationDate, newCreditCardCCV);

            // Enviar los datos modificados al servidor para actualizar la información en la base de datos
            Call<ProfileDataParse> updateCall = apiService.updateUserData(userId, updatedData);
            updateCall.enqueue(new Callback<ProfileDataParse>() {
                @Override
                public void onResponse(@NonNull Call<ProfileDataParse> call, @NonNull Response<ProfileDataParse> response) {
                    if (response.isSuccessful()) {
                        // Actualizar los datos en la vista con los nuevos valores
                        username.setText(updatedData.getNom());
                        name.setText(updatedData.getNom_real());
                        correu_electronic.setText(updatedData.getCorreu_electronic());
                        creditCardNumber.setText(updatedData.getNumero_targeta());
                        creditCardExpirationDate.setText(updatedData.getData_caducitat_targeta());
                        creditCardCCV.setText(updatedData.getCvv_targeta());
                    } else {
                        Log.e("TAG", "Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProfileDataParse> call, @NonNull Throwable t) {
                    Log.e("TAG", "Error: " + t.getMessage());
                }
            });
        });*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}