package com.example.tr1_takeaway.ui.shop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.api.shopService.ShopApiService;
import com.example.tr1_takeaway.databinding.FragmentShopBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopFragment extends Fragment {

    private RecyclerView productDisplay;
    private Adapter adapter;

    ShopViewModel ShopViewModel = new ShopViewModel();

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        //Button addtocart = view.findViewById(R.id.addProductToCart); // Asigna la vista al botón aquí

        //ShopViewModel shopViewModel =
        //        new ViewModelProvider(this).get(ShopViewModel.class);

        productDisplay = view.findViewById(R.id.productDisplay);
        productDisplay.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns grid

        ViewTreeObserver vto = productDisplay.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // The view is now ready; you can initialize your RecyclerView here.
                // Remove the listener if it's no longer needed.
                productDisplay.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.168.205.249:3001")
                .baseUrl("http://10.2.2.83:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShopApiService apiService = retrofit.create(ShopApiService.class);
        //AddtoShoppingCartApiService service = retrofit.create(AddtoShoppingCartApiService.class);
        // Call the method to fetch data from Retrofit
        fetchDataFromApi(apiService, productDisplay);
        ShopViewModel.addProductToCart(view);

        return view;
    }

    private void fetchDataFromApi(ShopApiService shopApiService, RecyclerView recyclerView) {
        Call<List<ProductDataModel>> call = shopApiService.getProducts();

        call.enqueue(new Callback<List<ProductDataModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductDataModel>> call, @NonNull Response<List<ProductDataModel>> response) {
                if (response.isSuccessful()) {
                    List<ProductDataModel> data = response.body();
                    assert data != null;
                    Log.d("DATA", data.toString());
                    adapter = new Adapter(data);
                    if (recyclerView != null) {
                        recyclerView.setAdapter(adapter);

                    }
                } else {
                    Log.e("TAG", "Error al obtener los datos del servidor");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductDataModel>> call, @NonNull Throwable t) {
                Log.e("TAG", "Error en la solicitud: " + t.getMessage());
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentShopBinding binding = null;
    }
}