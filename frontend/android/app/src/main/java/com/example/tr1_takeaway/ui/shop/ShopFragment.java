package com.example.tr1_takeaway.ui.shop;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.databinding.FragmentShopBinding;
import com.example.tr1_takeaway.shopService.ShopApiService;
import com.example.tr1_takeaway.shopService.ShopResponse;
import com.example.tr1_takeaway.ui.shopcart.ShopcartAdapter;
import com.example.tr1_takeaway.ui.shopcart.ShopcartFragment;
import com.example.tr1_takeaway.ui.shopcart.ShopcartProductDataModel;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

                    productDisplay.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.205.99:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShopApiService apiService = retrofit.create(ShopApiService.class);

        fetchDataFromApi(apiService, productDisplay);


        addToCart.setOnClickListener(v -> {
            Log.d("TAG", "S'ha clicat");
            Call<List<ShopcartProductDataModel>> call = apiService.addShoppingCartProduct(1, 1, 2);
            call.enqueue(new Callback<List<ShopcartProductDataModel>>() {
                @Override
                public void onResponse(Call<List<ShopcartProductDataModel>> call, Response<List<ShopcartProductDataModel>> response) {
                    if (response.isSuccessful()) {
                        List<ShopcartProductDataModel> data = response.body();
                        assert data != null;
                        Log.d("DATA", data.toString());
                        ShopcartAdapter adapter = new ShopcartAdapter(data);
                    } else {
                        Log.e("TAG", "what the fuck is a kilometer");
                    }
                }

                @Override
                public void onFailure(Call<List<ShopcartProductDataModel>> call, Throwable t) {
                    Log.e("TAG", "what the fuck is a kilometer");
                }
            });
        });


        return view;
    }

        private void fetchDataFromApi(ShopApiService ShopApiService, RecyclerView recyclerView) {

            Call<List<ProductDataModel>> call = ShopApiService.getProducts();

            call.enqueue(new Callback<List<ProductDataModel>>() {
                @Override
                public void onResponse(Call<List<ProductDataModel>> call, Response<List<ProductDataModel>> response) {
                    if (response.isSuccessful()) {
                        List<ProductDataModel> data = response.body();
                        assert data != null;
                        Log.d("DATA", data.toString());
                        adapter = new Adapter(data);
                        recyclerView.setAdapter(adapter);
                        ShopcartFragment fragment = new ShopcartFragment();
                        fragment.fetchDataFromApi(ShopApiService);
                    } else {
                        Log.e("TAG", "what the fuck is a kilometer");
                    }
                }

                @Override
                public void onFailure(Call<List<ProductDataModel>> call, Throwable t) {
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