package com.kapal.dokumenkapal.ui.bongkarmuat;

import com.google.gson.annotations.SerializedName;

public class BongkarMuatModelRecycler {

    @SerializedName("id")
    private int id;
    @SerializedName("kode")
    private String kode;
    @SerializedName("kode_biaya")
    private String kode_biaya;
    @SerializedName("jenis_muatan")
    private String jenis_muatan;
    @SerializedName("bobot")
    private Double bobot;
    @SerializedName("nama_kapal")
    private String nama_kapal;

    @SerializedName("jenis_kapal")
    private String jenis_kapal;

    @SerializedName("gt_kapal")
    private String gt_kapal;

    @SerializedName("agen_kapal")
    private String agen_kapal;

    @SerializedName("angkutan_nopol")
    private String angkutan_nopol;
    @SerializedName("angkutan_supir")
    private String angkutan_supir;
    @SerializedName("tgl_mohon")
    private String tgl_mohon;
    @SerializedName("tgl_pelaksanaan")
    private String tgl_pelaksanaan;
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

    public String getJenis_kapal() {
        return jenis_kapal;
    }

    public void setJenis_kapal(String jenis_kapal) {
        this.jenis_kapal = jenis_kapal;
    }

    public String getGt_kapal() {
        return gt_kapal;
    }

    public void setGt_kapal(String gt_kapal) {
        this.gt_kapal = gt_kapal;
    }

    public String getAgen_kapal() {
        return agen_kapal;
    }

    public void setAgen_kapal(String agen_kapal) {
        this.agen_kapal = agen_kapal;
    }

    public String getTgl_pelaksanaan() {
        return tgl_pelaksanaan;
    }

    public void setTgl_pelaksanaan(String tgl_pelaksanaan) {
        this.tgl_pelaksanaan = tgl_pelaksanaan;
    }

    public String getKode_biaya() {
        return kode_biaya;
    }

    public void setKode_biaya(String kode_biaya) {
        this.kode_biaya = kode_biaya;
    }

    public String getJenis_muatan() {
        return jenis_muatan;
    }

    public void setJenis_muatan(String jenis_muatan) {
        this.jenis_muatan = jenis_muatan;
    }

    public Double getBobot() {
        return bobot;
    }

    public void setBobot(Double bobot) {
        this.bobot = bobot;
    }

    public String getNama_kapal() {
        return nama_kapal;
    }

    public void setNama_kapal(String nama_kapal) {
        this.nama_kapal = nama_kapal;
    }

    public String getAngkutan_nopol() {
        return angkutan_nopol;
    }

    public void setAngkutan_nopol(String angkutan_nopol) {
        this.angkutan_nopol = angkutan_nopol;
    }

    public String getAngkutan_supir() {
        return angkutan_supir;
    }

    public void setAngkutan_supir(String angkutan_supir) {
        this.angkutan_supir = angkutan_supir;
    }

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

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public int getRating_kepuasan() {
        return rating_kepuasan;
    }

    public void setRating_kepuasan(int rating_kepuasan) {
        this.rating_kepuasan = rating_kepuasan;
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
