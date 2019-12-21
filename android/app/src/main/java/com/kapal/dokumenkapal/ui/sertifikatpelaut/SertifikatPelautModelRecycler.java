package com.kapal.dokumenkapal.ui.sertifikatpelaut;

import com.google.gson.annotations.SerializedName;

public class SertifikatPelautModelRecycler {

    @SerializedName("id")
    private int id;
    @SerializedName("nama_sertifikat")
    private String namaSertifikat;
    @SerializedName("nomor")
    private String nomor;
    @SerializedName("penerbit")
    private String penerbit;
    @SerializedName("tgl_terbit")
    private String tglTerbit;
    @SerializedName("tgl_berakhir")
    private String tglBerakhir;

    public SertifikatPelautModelRecycler(int id, String namaSertifikat, String nomor,
                                         String penerbit, String tglTerbit, String tglBerakhir) {
        this.id = id;
        this.namaSertifikat = namaSertifikat;
        this.nomor = nomor;
        this.penerbit = penerbit;
        this.tglTerbit = tglTerbit;
        this.tglBerakhir = tglBerakhir;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getTglBerakhir() {
        return tglBerakhir;
    }

    public void setTglBerakhir(String tglBerakhir) {
        this.tglBerakhir = tglBerakhir;
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

    public String getTglTerbit() {
        return tglTerbit;
    }

    public void setTglTerbit(String tglTerbit) {
        this.tglTerbit = tglTerbit;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
