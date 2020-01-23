package com.kapal.dokumenkapal.ui.kapal;

import com.google.gson.annotations.SerializedName;

public class KapalModelRecycler {

    @SerializedName("id")
    private int id;
    @SerializedName("nama_kapal")
    private String nama_kapal;
    @SerializedName("jenis_kapal")
    private String jenis_kapal;
    @SerializedName("kode_pengenal")
    private String kode_pengenal;
    @SerializedName("pelabuhan_daftar")
    private String pelabuhan_daftar;
    @SerializedName("imo_number")
    private String imo_number;
    @SerializedName("lambung_timbul")
    private int lambung_timbul;
    @SerializedName("grt")
    private int grt;
    @SerializedName("tgl_kontrak")
    private String tgl_kontrak;
    @SerializedName("tgl_peletakan_lunas")
    private String tgl_peletakan_lunas;
    @SerializedName("tgl_serah_terima")
    private String tgl_serah_terima;
    @SerializedName("tgl_perubahan")
    private String tgl_perubahan;
    @SerializedName("kapasitas_penumpang")
    private int kapasitas_penumpang;
    @SerializedName("kapasitas_roda_dua")
    private int kapasitas_roda_dua;
    @SerializedName("kapasitas_roda_empat")
    private int kapasitas_roda_empat;

    public int getLambung_timbul() {
        return lambung_timbul;
    }

    public void setLambung_timbul(int lambung_timbul) {
        this.lambung_timbul = lambung_timbul;
    }

    public String getTgl_kontrak() {
        return tgl_kontrak;
    }

    public void setTgl_kontrak(String tgl_kontrak) {
        this.tgl_kontrak = tgl_kontrak;
    }

    public String getTgl_peletakan_lunas() {
        return tgl_peletakan_lunas;
    }

    public void setTgl_peletakan_lunas(String tgl_peletakan_lunas) {
        this.tgl_peletakan_lunas = tgl_peletakan_lunas;
    }

    public String getTgl_serah_terima() {
        return tgl_serah_terima;
    }

    public void setTgl_serah_terima(String tgl_serah_terima) {
        this.tgl_serah_terima = tgl_serah_terima;
    }

    public String getTgl_perubahan() {
        return tgl_perubahan;
    }

    public void setTgl_perubahan(String tgl_perubahan) {
        this.tgl_perubahan = tgl_perubahan;
    }

    public String getPelabuhan_daftar() {
        return pelabuhan_daftar;
    }

    public void setPelabuhan_daftar(String pelabuhan_daftar) {
        this.pelabuhan_daftar = pelabuhan_daftar;
    }

    public String getKode_pengenal() {
        return kode_pengenal;
    }

    public void setKode_pengenal(String kode_pengenal) {
        this.kode_pengenal = kode_pengenal;
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
