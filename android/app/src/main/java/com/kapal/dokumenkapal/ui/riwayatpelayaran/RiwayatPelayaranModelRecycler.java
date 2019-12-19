package com.kapal.dokumenkapal.ui.riwayatpelayaran;

import com.google.gson.annotations.SerializedName;

public class RiwayatPelayaranModelRecycler {

    @SerializedName("namaKapal")
    private String namaKapal;

    @SerializedName("jabatan")
    private String jabatan;

    @SerializedName("tanggal")
    private String tanggal;

    public RiwayatPelayaranModelRecycler(String namaKapal, String jabatan, String tanggal) {
        this.namaKapal = namaKapal;
        this.jabatan = jabatan;
        this.tanggal = tanggal;
    }

    public String getNamaKapal() {
        return namaKapal;
    }

    public void setNamaKapal(String namaKapal) {
        this.namaKapal = namaKapal;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


}
