package com.example.tr1_takeaway.ui.shopcart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopcartViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ShopcartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is shopcart fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}