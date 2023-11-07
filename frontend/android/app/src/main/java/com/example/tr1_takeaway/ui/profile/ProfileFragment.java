package com.example.tr1_takeaway.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tr1_takeaway.databinding.FragmentProfileBinding;
import com.example.tr1_takeaway.loginService.LoginApiService;
import com.example.tr1_takeaway.loginService.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private TextView username, name, email, creditCardNumber, creditCardExpirationDate, creditCardCCV;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.username = binding.username;
        this.name = binding.nameText;
        this.email = binding.emailText;
        this.creditCardNumber = binding.ccnumberText;
        this.creditCardExpirationDate = binding.expirydateText;
        this.creditCardCCV = binding.ccvText;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.205.249:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginApiService apiService = retrofit.create(LoginApiService.class);

        Call<LoginResponse> call = apiService.getUserDataByName("ELADMIN");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Aquí puedes manejar la respuesta
                    // por ejemplo, actualizando las vistas con los datos recibidos
                } else {
                    Log.e("TAG", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
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