package com.sitslive.sitsliveschool.ui.schoolDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SchoolDetailsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SchoolDetailsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}