package com.example.tr1_takeaway.ui.shopcart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.MainActivity;
import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.databinding.FragmentShopcartBinding;
import com.example.tr1_takeaway.api.shopService.ShopApiService;
import com.example.tr1_takeaway.api.shopService.ShopResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ShopcartFragment extends Fragment implements DatePickerFragment.IDateSetListener, TimePickerFragment.ITimeSetListener{

    MainActivity main = new MainActivity();
    private FragmentShopcartBinding binding;
    public RecyclerView shopcartDisplay;
    private ShopcartAdapter adapter;
    ShopcartProductDataModel product;
    Button buyCart, pickDeliveryTime, pickDeliveryDate;
    ImageButton removeFromCart;
    ShopcartDialog confirmPurchase;

    int deliveryDateYear, deliveryDateMonth, deliveryDateDay;
    int deliveryTimeHour, deliveryTimeMinute;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopcart, container, false);
        View productview = inflater.inflate(R.layout.shopcart_grid_item, container, false);
        Log.d("TAG", "what the fuck is a kilometer");
        DatePickerFragment pickDate = new DatePickerFragment();
        TimePickerFragment pickTime = new TimePickerFragment();
        ((DatePickerFragment)pickDate).setIDateSetListener(this);
        ((TimePickerFragment)pickTime).setITimeSetListener(this);

        binding = FragmentShopcartBinding.inflate(inflater, container, false);
        ShopcartViewModel shopcartViewModel = new ViewModelProvider(this).get(ShopcartViewModel.class);

        shopcartDisplay = view.findViewById(R.id.ShopCartDisplay);
        shopcartDisplay.setLayoutManager(new LinearLayoutManager(requireContext()));
        buyCart = view.findViewById(R.id.buyShopcartButton);
        pickDeliveryTime = view.findViewById(R.id.timePickerButton);
        pickDeliveryDate = view.findViewById(R.id.datePickerButton);
        removeFromCart = productview.findViewById(R.id.deleteShopcartProduct);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(main.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShopApiService service = retrofit.create(ShopApiService.class);

        removeFromCart.setOnClickListener(v -> {
            Call<List<ShopcartProductDataModel>> call = service.deleteShoppingCartProduct(product.getId());
            call.enqueue(new Callback<List<ShopcartProductDataModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<ShopcartProductDataModel>> call, @NonNull Response<List<ShopcartProductDataModel>> response) {
                    if (response.isSuccessful()) {
                        List<ShopcartProductDataModel> data = response.body();
                        assert data != null;
                        Log.d("DATA", data.toString());
                        adapter = new ShopcartAdapter(data);
                        shopcartDisplay.setAdapter(adapter);
                    } else {
                        Log.e("TAG", "what the fuck is a kilometer");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ShopcartProductDataModel>> call, @NonNull Throwable t) {
                    Log.e("TAG", "Error en la solicitud: " + t.getMessage());
                    t.printStackTrace();
                }
            });
        });

        pickDeliveryDate.setOnClickListener(v -> {
            Log.d("TAG", "pick delivery date");
            pickDate.show(getActivity().getSupportFragmentManager(), "datePicker");
        });

        pickDeliveryTime.setOnClickListener(v -> {
            Log.d("TAG", "pick delivery time");
            pickTime.show(getActivity().getSupportFragmentManager(), "timePicker");
        });

        if(pickDeliveryDate.isPressed() && pickDeliveryTime.isPressed()){
            Log.e("TAG", "the order can be processed");
            buyCart.setOnClickListener(v -> {
                Log.e("TAG", "Processing order");

                Call<ShopResponse> call = service.addComanda(1, "admin",  processDatePickerResult(deliveryDateYear, deliveryDateMonth, deliveryDateDay),processTimePickerResult(deliveryTimeHour, deliveryTimeMinute));
                call.enqueue(new Callback<ShopResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ShopResponse> call, @NonNull Response<ShopResponse> response) {
                        if (response.isSuccessful()) {
                            confirmPurchase = new ShopcartDialog();
                            confirmPurchase.show(getActivity().getSupportFragmentManager(), "Completar comanda");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ShopResponse> call, @NonNull Throwable t) {
                        Log.e("TAG", "Error en la solicitud: " + t.getMessage());
                        t.printStackTrace(); // Imprimir el seguimiento de la pila para obtener más detalles sobre el error
                    }
                });
            });
        }

        fetchDataFromApi(service);

        return view;
    }

    public void fetchDataFromApi(ShopApiService service) {
        Call<List<ShopcartProductDataModel>> call = service.getCartProducts();

        call.enqueue(new Callback<List<ShopcartProductDataModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<ShopcartProductDataModel>> call, @NonNull Response<List<ShopcartProductDataModel>> response) {
                if (response.isSuccessful()) {
                    List<ShopcartProductDataModel> data = response.body();
                    assert data != null;
                    Log.d("DATA", data.toString());
                    ShopcartAdapter adapter = new ShopcartAdapter(data);
                    shopcartDisplay.setAdapter(adapter);
                } else {
                    Log.e("TAG", "what the fuck is a kilometer");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ShopcartProductDataModel>> call, @NonNull Throwable t) {
                Log.e("TAG", "what the fuck is a kilometer");
            }


        });
    }

    public String processDatePickerResult(int year, int month, int day) {

        Log.d("TAG", "processDatePickerResult()");

        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String deliveryDate = (month_string + "-" + day_string + "-" + year_string);
        return deliveryDate;
    }
    public String processTimePickerResult( int hour, int minute) {

        Log.d("TAG", "processTimePickerResult()");

        String hour_string = Integer.toString(hour);
        String minute_string = Integer.toString(minute);
        String deliveryTime = (hour_string + ":" + minute_string);
        return deliveryTime;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}