package com.kapal.dokumenkapal.ui.sertifikatkeselamatan;

import com.google.gson.annotations.SerializedName;

public class SpinPilihKapalModel {

    @SerializedName("id")
    private int id;
    @SerializedName("nama_kapal")
    private String nama_kapal;
    @SerializedName("jenis_kapal")
    private String jenis_kapal;
    @SerializedName("imo_number")
    private String imo_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_kapal() {
        return nama_kapal;
    }

    public void setNama_kapal(String nama_kapal) {
        this.nama_kapal = nama_kapal;
    }

    public String getJenis_kapal() {
        return jenis_kapal;
    }

    public void setJenis_kapal(String jenis_kapal) {
        this.jenis_kapal = jenis_kapal;
    }

    public String getImo_number() {
        return imo_number;
    }

    public void setImo_number(String imo_number) {
        this.imo_number = imo_number;
    }


}
