package com.example.tr1_takeaway.ui.shopcart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.databinding.FragmentShopcartBinding;
import com.example.tr1_takeaway.loginService.LoginResponse;
import com.example.tr1_takeaway.shopService.ShopApiService;
import com.example.tr1_takeaway.shopService.ShopResponse;
import com.example.tr1_takeaway.ui.shop.Adapter;
import com.example.tr1_takeaway.ui.shop.ProductDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ShopcartFragment extends Fragment {

    private FragmentShopcartBinding binding;
    private RecyclerView shopcartDisplay;
    private Adapter adapter;
    Button buyCart;
    ImageButton removeFromCart;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopcart, container, false);
        View productview = inflater.inflate(R.layout.shopcart_grid_item, container, false);
        Log.d("TAG", "what the fuck is a kilometer");

        binding = FragmentShopcartBinding.inflate(inflater, container, false);
        ShopcartViewModel shopcartViewModel = new ViewModelProvider(this).get(ShopcartViewModel.class);

        shopcartDisplay = view.findViewById(R.id.ShopCartDisplay);
        shopcartDisplay.setLayoutManager(new LinearLayoutManager(requireContext()));
        buyCart = view.findViewById(R.id.buyShopcartButton);
        removeFromCart = productview.findViewById(R.id.deleteShopcartProduct);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.205.99:3001") // URL Wilson
                //.baseUrl("http://192.168.205.249:3001") // URL Ramon
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShopApiService service = retrofit.create(ShopApiService.class);

        removeFromCart.setOnClickListener(v -> {
            Call<ShopResponse> call = service.deleteShoppingCartProduct(1);
            call.enqueue(new Callback<ShopResponse>() {
                @Override
                public void onResponse(Call<ShopResponse> call, Response<ShopResponse> response) {
                    if (response.isSuccessful()) {
                        ShopResponse shopResponse = response.body();
                        if (shopResponse != null) {
                            Log.e("TAG", "Wahoo");
                        }
                    } else {
                        Log.e("TAG", "what the fuck is a kilometer");
                    }
                }

                @Override
                public void onFailure(Call<ShopResponse> call, Throwable t) {
                    Log.e("TAG", "Error en la solicitud: " + t.getMessage());
                    t.printStackTrace(); // Imprimir el seguimiento de la pila para obtener más detalles sobre el error
                }
            });
        });

        buyCart.setOnClickListener(v -> {
            Call<ShopResponse> call = service.addComanda(1, "admin");
            call.enqueue(new Callback<ShopResponse>() {
                @Override
                public void onResponse(Call<ShopResponse> call, Response<ShopResponse> response) {
                    if (response.isSuccessful()) {
                        ;
                    }
                }

                @Override
                public void onFailure(Call<ShopResponse> call, Throwable t) {
                    Log.e("TAG", "Error en la solicitud: " + t.getMessage());
                    t.printStackTrace(); // Imprimir el seguimiento de la pila para obtener más detalles sobre el error
                }
            });
        });

        fetchDataFromApi(service);

        return view;
    }

    private void fetchDataFromApi(ShopApiService service) {
        Call<List<ShopcartProductDataModel>> call = service.getCartProducts();

        call.enqueue(new Callback<List<ShopcartProductDataModel>>() {
            @Override
            public void onResponse(Call<List<ShopcartProductDataModel>> call, Response<List<ShopcartProductDataModel>> response) {
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
            public void onFailure(Call<List<ShopcartProductDataModel>> call, Throwable t) {
                Log.e("TAG", "what the fuck is a kilometer");
            }


        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}