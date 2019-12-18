package com.kapal.dokumenkapal.ui.kapal;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KapalModelList {

    @SerializedName("kapalList")
    private ArrayList<KapalModelRecycler> kapalList;

    public ArrayList<KapalModelRecycler> getKapalArrayList(){
        return kapalList;
    }

    public void setKapaArraylList(ArrayList<KapalModelRecycler> kapaArraylList){
        this.kapalList = kapaArraylList;
    }
}
