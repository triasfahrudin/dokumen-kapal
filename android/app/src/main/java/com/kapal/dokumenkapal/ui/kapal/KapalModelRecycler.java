package com.kapal.dokumenkapal.ui.kapal;

import com.google.gson.annotations.SerializedName;

public class KapalModelRecycler {

    @SerializedName("id")
    private int id;
    @SerializedName("nama_kapal")
    private String nama_kapal;
    @SerializedName("jenis_kapal")
    private String jenis_kapal;
    @SerializedName("imo_number")
    private String imo_number;
    @SerializedName("grt")
    private int grt;
    @SerializedName("kapasitas_penumpang")
    private int kapasitas_penumpang;
    @SerializedName("kapasitas_roda_dua")
    private int kapasitas_roda_dua;
    @SerializedName("kapasitas_roda_empat")
    private int kapasitas_roda_empat;

    public KapalModelRecycler(int id, String nama_kapal,
                              String jenis_kapal, String imo_number,
                              int grt, int kapasitas_penumpang, int kapasitas_roda_dua,
                              int kapasitas_roda_empat) {
        this.id = id;
        this.nama_kapal = nama_kapal;
        this.jenis_kapal = jenis_kapal;
        this.imo_number = imo_number;
        this.grt = grt;
        this.kapasitas_penumpang = kapasitas_penumpang;
        this.kapasitas_roda_dua = kapasitas_roda_dua;
        this.kapasitas_roda_empat = kapasitas_roda_empat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrt() {
        return grt;
    }

    public void setGrt(int grt) {
        this.grt = grt;
    }

    public int getKapasitas_penumpang() {
        return kapasitas_penumpang;
    }

    public void setKapasitas_penumpang(int kapasitas_penumpang) {
        this.kapasitas_penumpang = kapasitas_penumpang;
    }

    public int getKapasitas_roda_dua() {
        return kapasitas_roda_dua;
    }

    public void setKapasitas_roda_dua(int kapasitas_roda_dua) {
        this.kapasitas_roda_dua = kapasitas_roda_dua;
    }

    public int getKapasitas_roda_empat() {
        return kapasitas_roda_empat;
    }

    public void setKapasitas_roda_empat(int kapasitas_roda_empat) {
        this.kapasitas_roda_empat = kapasitas_roda_empat;
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
