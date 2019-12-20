package com.kapal.dokumenkapal.ui.sertifikatpelaut;

import com.google.gson.annotations.SerializedName;

public class SertifikatPelautModelRecycler {

    @SerializedName("namaSertifikat")
    private String namaSertifikat;
    @SerializedName("penerbit")
    private String penerbit;
    @SerializedName("tanggal")
    private String tanggal;

    public SertifikatPelautModelRecycler(String namaSertifikat, String penerbit, String tanggal) {
        this.namaSertifikat = namaSertifikat;
        this.penerbit = penerbit;
        this.tanggal = tanggal;
    }

    public String getNamaSertifikat() {
        return namaSertifikat;
    }

    public void setNamaSertifikat(String namaSertifikat) {
        this.namaSertifikat = namaSertifikat;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


}
