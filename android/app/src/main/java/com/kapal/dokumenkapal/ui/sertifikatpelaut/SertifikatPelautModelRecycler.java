package com.kapal.dokumenkapal.ui.sertifikatpelaut;

import com.google.gson.annotations.SerializedName;

public class SertifikatPelautModelRecycler {

    @SerializedName("id")
    private int id;
    @SerializedName("nama_sertifikat")
    private String nama_sertifikat;
    @SerializedName("nomor")
    private String nomor;
    @SerializedName("penerbit")
    private String penerbit;
    @SerializedName("tgl_terbit")
    private String tgl_terbit;

    public SertifikatPelautModelRecycler(int id, String nama_sertifikat, String nomor,
                                         String penerbit, String tgl_terbit) {
        this.id = id;
        this.nama_sertifikat = nama_sertifikat;
        this.nomor = nomor;
        this.penerbit = penerbit;
        this.tgl_terbit = tgl_terbit;

    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getNama_sertifikat() {
        return nama_sertifikat;
    }

    public void setNama_sertifikat(String nama_sertifikat) {
        this.nama_sertifikat = nama_sertifikat;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getTgl_terbit() {
        return tgl_terbit;
    }

    public void setTgl_terbit(String tgl_terbit) {
        this.tgl_terbit = tgl_terbit;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
