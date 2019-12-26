package com.kapal.dokumenkapal.ui.sertifikatkeselamatan;

import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.SerializedName;
import com.kapal.dokumenkapal.ui.bongkarmuat.BongkarMuatModelRecycler;

import java.util.ArrayList;

public class SertifikatKeselamatanModelList extends ViewModel {

    @SerializedName("sertifikatKeselamatanList")
    private ArrayList<SertifikatKeselamatanModelRecycler> sertifikatKeselamatanList;

    public ArrayList<SertifikatKeselamatanModelRecycler> getSertifikatKeselamatanArrayList(){
        return sertifikatKeselamatanList;
    }

    public void setSertifikatKeselamatanArraylList(ArrayList<SertifikatKeselamatanModelRecycler> sertifikatKeselamatanArraylList){
        this.sertifikatKeselamatanList = sertifikatKeselamatanArraylList;
    }

}

