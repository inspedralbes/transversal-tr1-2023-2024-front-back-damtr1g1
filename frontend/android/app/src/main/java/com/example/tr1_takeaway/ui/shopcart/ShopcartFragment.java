package com.example.tr1_takeaway.ui.shopcart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.databinding.FragmentShopcartBinding;
import com.example.tr1_takeaway.ui.shop.Adapter;
import com.example.tr1_takeaway.ui.shop.ShopViewModel;


public class ShopcartFragment extends Fragment {

    private FragmentShopcartBinding binding;
    private RecyclerView productDisplay;
    private Adapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopcart, container, false);
        Log.d("TAG", "what the fuck is a kilometer");

        binding = FragmentShopcartBinding.inflate(inflater, container, false);
        ShopcartViewModel shopcartViewModel = new ViewModelProvider(this).get(ShopcartViewModel.class);

        productDisplay = view.findViewById(R.id.ShopCartDisplay);
        productDisplay.setLayoutManager(new LinearLayoutManager(requireContext())); // 2 columns grid


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}