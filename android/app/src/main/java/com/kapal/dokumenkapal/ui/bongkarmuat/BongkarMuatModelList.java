package com.kapal.dokumenkapal.ui.bongkarmuat;

import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.SerializedName;
import com.kapal.dokumenkapal.ui.masalayar.MasaLayarModelRecycler;

import java.util.ArrayList;

public class BongkarMuatModelList extends ViewModel {

    @SerializedName("bongkarMuatList")
    private ArrayList<BongkarMuatModelRecycler> bongkarMuatList;

    public ArrayList<BongkarMuatModelRecycler> getBongkarMuatArrayList(){
        return bongkarMuatList;
    }

    public void setBongkarMuatArraylList(ArrayList<BongkarMuatModelRecycler> bongkarMuatArraylList){
        this.bongkarMuatList = bongkarMuatArraylList;
    }

}
