package com.kapal.dokumenkapal.ui.sertifikatkeselamatan;

import com.google.gson.annotations.SerializedName;

public class SertifikatKeselamatanModelRecycler {

    @SerializedName("id")
    private int id;
    @SerializedName("kode")
    private String kode;
    @SerializedName("nama_kapal")
    private String nama_kapal;
    @SerializedName("tgl_mohon")
    private String tgl_mohon;
    @SerializedName("tgl_update")
    private String tgl_update;
    @SerializedName("status")
    private String status;
    @SerializedName("rating_kepuasan")
    private int rating_kepuasan;
    @SerializedName("komentar")
    private String komentar;

    public SertifikatKeselamatanModelRecycler(int id, String kode, String nama_kapal, String tgl_mohon, String tgl_update, String status, int rating_kepuasan, String komentar) {
        this.id = id;
        this.kode = kode;
        this.nama_kapal = nama_kapal;
        this.tgl_mohon = tgl_mohon;
        this.tgl_update = tgl_update;
        this.status = status;
        this.rating_kepuasan = rating_kepuasan;
        this.komentar = komentar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama_kapal() {
        return nama_kapal;
    }

    public void setNama_kapal(String nama_kapal) {
        this.nama_kapal = nama_kapal;
    }

    public String getTgl_mohon() {
        return tgl_mohon;
    }

    public void setTgl_mohon(String tgl_mohon) {
        this.tgl_mohon = tgl_mohon;
    }

    public String getTgl_update() {
        return tgl_update;
    }

    public void setTgl_update(String tgl_update) {
        this.tgl_update = tgl_update;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRating_kepuasan() {
        return rating_kepuasan;
    }

    public void setRating_kepuasan(int rating_kepuasan) {
        this.rating_kepuasan = rating_kepuasan;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

}
