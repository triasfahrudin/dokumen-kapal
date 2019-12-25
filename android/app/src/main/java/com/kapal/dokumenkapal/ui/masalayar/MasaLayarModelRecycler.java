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
    @SerializedName("status")
    private String status;

    public MasaLayarModelRecycler(int id, String kode, String tgl_mohon, String tgl_update, String status) {
        this.id = id;
        this.kode = kode;
        this.tgl_mohon = tgl_mohon;
        this.tgl_update = tgl_update;
        this.status = status;
    }

    public String getTgl_update() {
        return tgl_update;
    }

    public void setTgl_update(String tgl_update) {
        this.tgl_update = tgl_update;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
