package com.kapal.dokumenkapal.ui.kapal;

import com.google.gson.annotations.SerializedName;

public class KapalModelRecycler {
    @SerializedName("nama")
    private String nama;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("imo_number")
    private String imo_number;

    public KapalModelRecycler(String nama,String jenis,String imo_number){
        this.nama = nama;
        this.jenis = jenis;
        this.imo_number = imo_number;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getImo_number() {
        return imo_number;
    }

    public void setImo_number(String imo_number) {
        this.imo_number = imo_number;
    }



}
