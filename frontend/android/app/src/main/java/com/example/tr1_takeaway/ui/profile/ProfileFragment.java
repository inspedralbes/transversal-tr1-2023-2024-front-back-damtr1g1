package com.example.tr1_takeaway.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.databinding.FragmentProfileBinding;
import com.example.tr1_takeaway.loginService.LoginApiService;
import com.example.tr1_takeaway.loginService.LoginResponse;
import com.example.tr1_takeaway.shopService.ShopApiService;

import retrofit2.Call;
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.205.249:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginApiService apiService = retrofit.create(LoginApiService.class);

        Call<LoginResponse> call = apiService.getUserDataByName("ELADMIN");


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}