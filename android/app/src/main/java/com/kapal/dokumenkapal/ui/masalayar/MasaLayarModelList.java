package com.kapal.dokumenkapal.ui.masalayar;

import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MasaLayarModelList extends ViewModel {

    @SerializedName("masaLayarList")
    private ArrayList<MasaLayarModelRecycler> masaLayarList;

    public ArrayList<MasaLayarModelRecycler> getMasaLayarArrayList() {
        return masaLayarList;
    }

    public void setMasaLayarArraylList(ArrayList<MasaLayarModelRecycler> masaLayarArrayList) {
        this.masaLayarList = masaLayarArrayList;
    }
}
