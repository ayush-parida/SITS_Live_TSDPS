package com.sitslive.sitsliveschool.ui.fee;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FeeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FeeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is attendance fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}