package com.kapal.dokumenkapal.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String SP_DOKUMEN_APP = "spDokumenApp";

    public static final String SP_JENIS = "spJenis";
    public static final String SP_ID = "spID";
    public static final String SP_NAMA = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_FOTO = "spFoto";
    public static final String SP_NPWP = "spNPWP";
    public static final String SP_ALAMAT = "spAlamat";
    public static final String SP_TELP = "spTelp";


    public static final String SP_SETTING_NO_REKENING = "spSettingNoRek";
    public static final String SP_SETTING_NAMA_REKENING = "spSettingNamaRek";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(SP_DOKUMEN_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPJenis() {
        return sp.getString(SP_JENIS, "");
    }

    public Integer getSPID() {
        return sp.getInt(SP_ID, 0);
    }

    public String getSPNPWP() {
        return sp.getString(SP_NPWP, "");
    }

    public String getSPFoto() {
        return sp.getString(SP_FOTO, "");
    }

    public String getSPAlamat() {
        return sp.getString(SP_ALAMAT, "");
    }

    public String getSPTelp() {
        return sp.getString(SP_TELP, "");
    }

    public String getSPNama() {
        return sp.getString(SP_NAMA, "");
    }

    public String getSPEmail() {
        return sp.getString(SP_EMAIL, "");
    }

    public Boolean getSPSudahLogin() {
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public String getSpSettingNoRekening() {
        return sp.getString(SP_SETTING_NO_REKENING, "");
    }

    public String getSpSettingNamaRekening() {
        return sp.getString(SP_SETTING_NAMA_REKENING, "");
    }

}
