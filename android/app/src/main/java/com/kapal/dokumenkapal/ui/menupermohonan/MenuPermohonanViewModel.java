package com.kapal.dokumenkapal.ui.menupermohonan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenuPermohonanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MenuPermohonanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is permohonan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}