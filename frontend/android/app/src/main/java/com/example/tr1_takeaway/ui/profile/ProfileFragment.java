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

    private TextView username, name, cognoms, email, creditCardNumber, creditCardExpirationDate, creditCardCCV;
    private FragmentProfileBinding binding;
    private boolean isEditing = false;
    private Button EditarProfile;
    private EditText editName, editCognoms, editUsername, editEmail, editCreditCardNumber, editCardED, editCVV;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditarProfile = root.findViewById(R.id.EditarProfile);

        Context context = getActivity();

        // Obtén el ID del usuario de SharedPreferences en otra clase
        assert context != null;
        SharedPreferences prefs = context.getSharedPreferences("NombrePreferencias", MODE_PRIVATE);
        String userId = prefs.getString("IDUsuario", "ValorPredeterminadoSiNoSeEncuentra");

        this.username = binding.username;
        this.name = binding.name;
        this.cognoms = binding.cognoms;
        this.email = binding.emailText;
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
                        email.setText(profileDataParse.getCorreu_electronic());
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


        EditarProfile.setOnClickListener(v -> {
            if (isEditing) {
                // Convertir los TextViews a EditTexts
                name.setVisibility(View.GONE);
                this.editName = new EditText(requireContext());
                this.editName.setLayoutParams(name.getLayoutParams());
                this.editName.setText(name.getText());
                ((ViewGroup) name.getParent()).addView(editName);

                cognoms.setVisibility(View.GONE);
                editCognoms = new EditText(requireContext());
                editCognoms.setLayoutParams(cognoms.getLayoutParams());
                editCognoms.setText(cognoms.getText());
                ((ViewGroup) cognoms.getParent()).addView(editCognoms);

                username.setVisibility(View.GONE);
                editUsername= new EditText(requireContext());
                editUsername.setLayoutParams(username.getLayoutParams());
                editUsername.setText(username.getText());
                ((ViewGroup) username.getParent()).addView(editUsername);

                email.setVisibility(View.GONE);
                editEmail = new EditText(requireContext());
                editEmail.setLayoutParams(email.getLayoutParams());
                editEmail.setText(email.getText());
                ((ViewGroup) email.getParent()).addView(editEmail);

                creditCardNumber.setVisibility(View.GONE);
                editCreditCardNumber = new EditText(requireContext());
                editCreditCardNumber.setLayoutParams(creditCardNumber.getLayoutParams());
                editCreditCardNumber.setText(creditCardNumber.getText());
                ((ViewGroup) creditCardNumber.getParent()).addView(editCreditCardNumber);

                creditCardExpirationDate.setVisibility(View.GONE);
                editCardED = new EditText(requireContext());
                editCardED.setLayoutParams(creditCardExpirationDate.getLayoutParams());
                editCardED.setText(creditCardExpirationDate.getText());
                ((ViewGroup) creditCardExpirationDate.getParent()).addView(editCardED);

                creditCardCCV.setVisibility(View.GONE);
                editCVV = new EditText(requireContext());
                editCVV.setLayoutParams(creditCardCCV.getLayoutParams());
                editCVV.setText(creditCardCCV.getText());
                ((ViewGroup) creditCardCCV.getParent()).addView(editCVV);


                EditarProfile.setText("Guardar");
                isEditing = false;
            } else {
                // Guardar los cambios y convertir los EditTexts de vuelta a TextViews
                String newName = editName.getText().toString();
                name.setText(newName);
                ((ViewGroup) editName.getParent()).removeView(editName);
                name.setVisibility(View.VISIBLE);

                String newCognoms = editCognoms.getText().toString();
                cognoms.setText(newCognoms);
                ((ViewGroup) editCognoms.getParent()).removeView(editCognoms);
                cognoms.setVisibility(View.VISIBLE);

                // Continuar con el resto de los TextViews y EditTexts según sea necesario

                EditarProfile.setText("Editar");
                isEditing = true;
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