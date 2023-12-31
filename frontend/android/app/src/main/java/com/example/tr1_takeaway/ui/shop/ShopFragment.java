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

import com.example.tr1_takeaway.MainActivity;
import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.databinding.FragmentShopBinding;
import com.example.tr1_takeaway.api.shopService.ShopApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopFragment extends Fragment {

    private FragmentShopBinding binding;

        private RecyclerView productDisplay;
        Button addtocart;
        private Adapter adapter;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_shop, container, false);

            //addtocart.findViewById(R.id.addProductToCart);

            ShopViewModel shopViewModel =
                    new ViewModelProvider(this).get(ShopViewModel.class);

            productDisplay = view.findViewById(R.id.productDisplay);
            productDisplay.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns grid

            MainActivity MA = new MainActivity();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MA.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ShopApiService apiService = retrofit.create(ShopApiService.class);

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