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

    public SertifikatKeselamatanModelRecycler(int id, String kode, String nama_kapal, String tgl_mohon, String tgl_update, String status) {
        this.id = id;
        this.kode = kode;
        this.nama_kapal = nama_kapal;
        this.tgl_mohon = tgl_mohon;
        this.tgl_update = tgl_update;
        this.status = status;
    }

    public String getNama_kapal() {
        return nama_kapal;
    }

    public void setNama_kapal(String nama_kapal) {
        this.nama_kapal = nama_kapal;
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
}
