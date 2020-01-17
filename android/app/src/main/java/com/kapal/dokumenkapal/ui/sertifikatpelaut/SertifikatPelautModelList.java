package com.kapal.dokumenkapal.ui.sertifikatpelaut;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SertifikatPelautModelList {
    @SerializedName("sertifikatPelautList")
    private ArrayList<SertifikatPelautModelRecycler> sertifikatPelautList;

    public ArrayList<SertifikatPelautModelRecycler> getSertifikatPelautArrayList() {
        return sertifikatPelautList;
    }

    public void setSertifikatPelautArraylList(ArrayList<SertifikatPelautModelRecycler> sertifikatPelautArrayList) {
        this.sertifikatPelautList = sertifikatPelautArrayList;
    }
}

