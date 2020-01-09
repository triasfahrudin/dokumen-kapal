package com.kapal.dokumenkapal.ui.bongkarmuat;

import com.google.gson.annotations.SerializedName;

class SpinPilihJenisMuatanModel {
    @SerializedName("kode")
    private String kode;
    @SerializedName("alias")
    private String alias;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
