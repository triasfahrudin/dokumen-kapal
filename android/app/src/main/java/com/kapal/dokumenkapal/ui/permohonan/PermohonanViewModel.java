package com.kapal.dokumenkapal.ui.permohonan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PermohonanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PermohonanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is permohonan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}