package com.example.tr1_takeaway.ui.shop;

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
import com.example.tr1_takeaway.databinding.FragmentShopBinding;
import com.example.tr1_takeaway.shopService.ShopApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopFragment extends Fragment {

    private FragmentShopBinding binding;

        private RecyclerView productDisplay;
    private Adapter adapter;
    private Button addToCart;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_shop, container, false);
            View productview = inflater.inflate(R.layout.grid_item, container, false);

            Log.d("TAG", "what the fuck is a kilometer");

            ShopViewModel shopViewModel =
                    new ViewModelProvider(this).get(ShopViewModel.class);

            productDisplay = view.findViewById(R.id.productDisplay);
            productDisplay.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns grid
            addToCart = productview.findViewById(R.id.addProductToCart);

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
                    .baseUrl("http://192.168.205.99:3001")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ShopApiService apiService = retrofit.create(ShopApiService.class);
            // Call the method to fetch data from Retrofit
            fetchDataFromApi(apiService, productDisplay);

            return view;
        }

        private void fetchDataFromApi(ShopApiService ShopApiService, RecyclerView recyclerView) {

            Call<List<ProductDataModel>> call = ShopApiService.getProducts();

            call.enqueue(new Callback<List<ProductDataModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<ProductDataModel>> call, @NonNull Response<List<ProductDataModel>> response) {
                    if (response.isSuccessful()) {
                        List<ProductDataModel> data = response.body();
                        assert data != null;
                        Log.d("DATA", data.toString());
                        adapter = new Adapter(data);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.e("TAG", "what the fuck is a kilometer");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ProductDataModel>> call, @NonNull Throwable t) {
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