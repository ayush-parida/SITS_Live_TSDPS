package com.sitslive.sitsliveschool.ui.academics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AcademicsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AcademicsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is homework fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}