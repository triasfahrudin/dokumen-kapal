package com.kapal.dokumenkapal.ui.masalayar;

import com.google.gson.annotations.SerializedName;

public class MasaLayarModelRecycler {
    @SerializedName("id")
    private int id;
    @SerializedName("kode")
    private String kode;
    @SerializedName("tgl_mohon")
    private String tgl_mohon;
    @SerializedName("tgl_update")
    private String tgl_update;
    @SerializedName("biaya")
    private Double biaya;
    @SerializedName("status")
    private String status;
    @SerializedName("alasan_status")
    private String alasan_status;
    @SerializedName("arti_status")
    private String arti_status;
    @SerializedName("rating_kepuasan")
    private int rating_kepuasan;
    @SerializedName("komentar")
    private String komentar;

    public Double getBiaya() {
        return biaya;
    }

    public void setBiaya(Double biaya) {
        this.biaya = biaya;
    }

    public String getAlasan_status() {
        return alasan_status;
    }

    public void setAlasan_status(String alasan_status) {
        this.alasan_status = alasan_status;
    }

    public String getArti_status() {
        return arti_status;
    }

    public void setArti_status(String arti_status) {
        this.arti_status = arti_status;
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
