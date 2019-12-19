package com.kapal.dokumenkapal.ui.riwayatpelayaran;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RiwayatPelayaranModelList {

    @SerializedName("riwayatPelayaranList")
    private ArrayList<RiwayatPelayaranModelRecycler> riwayatPelayaranList;

    public ArrayList<RiwayatPelayaranModelRecycler> getRiwayatPelayaranArrayList(){
        return riwayatPelayaranList;
    }

    public void setRiwayatPelayaranArraylList(ArrayList<RiwayatPelayaranModelRecycler> riwayatPelayaranArrayList){
        this.riwayatPelayaranList = riwayatPelayaranArrayList;
    }
}
