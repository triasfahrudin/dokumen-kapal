package com.kapal.dokumenkapal.ui.riwayatpelayaran;

import com.google.gson.annotations.SerializedName;

public class RiwayatPelayaranModelRecycler {

    @SerializedName("id")
    private int id;
    @SerializedName("nama_kapal")
    private String nama_kapal;
    @SerializedName("tenaga_mesin")
    private String tenaga_mesin;
    @SerializedName("jabatan")
    private String jabatan;
    @SerializedName("tgl_naik")
    private String tgl_naik;
    @SerializedName("tgl_turun")
    private String tgl_turun;

    public RiwayatPelayaranModelRecycler(int id, String nama_kapal, String tenaga_mesin, String jabatan, String tgl_naik, String tgl_turun) {
        this.id = id;
        this.nama_kapal = nama_kapal;
        this.tenaga_mesin = tenaga_mesin;
        this.jabatan = jabatan;
        this.tgl_naik = tgl_naik;
        this.tgl_turun = tgl_turun;
    }

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

    public String getTenaga_mesin() {
        return tenaga_mesin;
    }

    public void setTenaga_mesin(String tenaga_mesin) {
        this.tenaga_mesin = tenaga_mesin;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getTgl_naik() {
        return tgl_naik;
    }

    public void setTgl_naik(String tgl_naik) {
        this.tgl_naik = tgl_naik;
    }

    public String getTgl_turun() {
        return tgl_turun;
    }

    public void setTgl_turun(String tgl_turun) {
        this.tgl_turun = tgl_turun;
    }


}
