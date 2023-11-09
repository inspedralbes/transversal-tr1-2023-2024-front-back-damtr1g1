package com.example.tr1_takeaway.ui.shopcart;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.example.tr1_takeaway.MainActivity;
import com.example.tr1_takeaway.api.shopService.ShopApiService;
import com.example.tr1_takeaway.api.shopService.ShopResponse;
import com.example.tr1_takeaway.ui.shop.ShopFragment;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopcartDialog extends DialogFragment {
    public class CustomAlertDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Vols completar aquesta comanda?")
                    .setPositiveButton("Completar Comanda", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            initiateRetrofitCall();
                        }
                    })
                    .setNegativeButton("Cancel·lar Comanda", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            redirectToMainActivity();
                        }
                    });

            return builder.create();
        }

        private void initiateRetrofitCall() {
            MainActivity MA = new MainActivity();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MA.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ShopApiService retrofitService = retrofit.create(ShopApiService.class);
            Call<ShopResponse> call = retrofitService.addComanda(1, "admin");
            // Execute the call
            call.enqueue(new Callback<ShopResponse>() {
                @Override
                public void onResponse(Call<ShopResponse> call, Response<ShopResponse> response) {
                    // Handle the response
                }

                @Override
                public void onFailure(Call<ShopResponse> call, Throwable t) {
                    // Handle the error
                }
            });
        }

        private void redirectToMainActivity() {
            Intent intent = new Intent(getActivity(), ShopcartFragment.class);
            startActivity(intent);
        }
    }

}