package com.kapal.dokumenkapal.util.api;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.kapal.dokumenkapal.ui.bongkarmuat.BongkarMuatModelList;
import com.kapal.dokumenkapal.ui.kapal.KapalModelList;
import com.kapal.dokumenkapal.ui.masalayar.MasaLayarModelList;
import com.kapal.dokumenkapal.ui.riwayatpelayaran.RiwayatPelayaranModelList;
import com.kapal.dokumenkapal.ui.sertifikatkeselamatan.SertifikatKeselamatanModelList;
import com.kapal.dokumenkapal.ui.sertifikatpelaut.SertifikatPelautModelList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {

    // Fungsi ini untuk memanggil API http://{PATH}/restapi/login
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);


    @FormUrlEncoded
    @POST("send_tokenid")
    Call<ResponseBody> sendRegistrationToServer(@Field("pemohon_id") int pemohon_id,
                                                @Field("token_id") String token_id);

    // Fungsi ini untuk memanggil API http://{PATH}/restapi/register
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(
            @Field("jenis") String jenis,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("update_bukupelaut")
    Call<ResponseBody> updateBukuPelautRequest(
            @Field("pemohon_id") int pemohon_id,
            @Field("nomor_buku") String nomor_buku,
            @Field("kode_pelaut") String kode_pelaut,
            @Field("nomor_daftar") String nomor_daftar
    );

    @FormUrlEncoded
    @POST("update_profile")
    Call<ResponseBody> updateProfileRequest(
            @Field("id") int pemohon_id,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat
    );

    @FormUrlEncoded
    @POST("insertupdate_kapal")
    Call<ResponseBody> insertUpdateKapalRequest(
            @Field("id") int id,
            @Field("pemohon_id") int pemohon_id,
            @Field("nama_kapal") String namaKapal,
            @Field("jenis_kapal") String jenisKapal,
            @Field("imo_number") String imoNumber,
            @Field("grt") int grt,
            @Field("kapasitas_penumpang") int kapasitasPenumpang,
            @Field("kapasitas_roda_dua") int kapasitasRodaDua,
            @Field("kapasitas_roda_empat") int kapasitasRodaEmpat
    );

    @FormUrlEncoded
    @POST("insertupdate_riwayatpelayaran")
    Call<ResponseBody> insertUpdateRiwayatPelayaranRequest(
            @Field("id") int id,
            @Field("pemohon_id") int pemohon_id,
            @Field("nama_kapal") String namaKapal,
            @Field("tenaga_mesin") String tenagaMesin,
            @Field("jabatan") String jabatan,
            @Field("tgl_naik") String tgl_naik,
            @Field("tgl_turun") String tgl_turun
    );

    @GET("get_bukupelaut/{pemohon_id}")
    Call<ResponseBody> getBukuPelautRequest(@Path("pemohon_id") int pemohon_id);


    @GET("get_kapal")
    Call<KapalModelList> getKapal(@Query("pemohon_id") int pemohon_id);

    @GET("get_kapal_for_spinner")
    Call<ResponseBody> getKapalForSpinner(@Query("pemohon_id") int pemohon_id);

    @GET("get_riwayatpelayaran")
    Call<RiwayatPelayaranModelList> getRiwayatPelayaran(@Query("pemohon_id") int pemohon_id);

    @GET("get_profile/{id}")
    Call<ResponseBody> getProfileRequest(@Path("id") int id);

    @GET("delete_kapal/{id}")
    Call<ResponseBody> delKapalRequest(@Path("id") int id);

    @GET("delete_riwayatpelayaran/{id}")
    Call<ResponseBody> delRiwayatPelayaranRequest(@Path("id") int id);


    @Multipart
    @POST("uploadfile")
    Call<ResponseBody> uploadFile(
            @Part("jenis") String jenis,
            @Part("id") int id,
            @Part MultipartBody.Part file,
            @Part("filename") RequestBody name
    );

    @Multipart
    @POST("uploadfile")
    Call<ResponseBody> uploadFile(
            @Part("jenis") String jenis,
            @Part("id") int id,
            @Part("pemohon_id") int pemohon_id,
            @Part MultipartBody.Part file,
            @Part("filename") RequestBody name
    );

    @GET("get_sertifikatpelaut")
    Call<SertifikatPelautModelList> getSertifikatPelaut(@Query("pemohon_id") int pemohon_id);

    @FormUrlEncoded
    @POST("insertupdate_sertifikatpelaut")
    Call<ResponseBody> insertUpdateSertifikatPelautRequest(
            @Field("id") int id,
            @Field("pemohon_id") int pemohon_id,
            @Field("nama_sertifikat") String nama_sertifikat,
            @Field("nomor") String nomor,
            @Field("penerbit") String penerbit,
            @Field("tgl_terbit") String tgl_terbit
    );

    @GET("delete_sertifikatpelaut/{id}")
    Call<ResponseBody> delSertifikatPelautRequest(@Path("id") int id);


    @GET("get_masalayar")
    Call<MasaLayarModelList> getMasaLayar(@Query("pemohon_id") int pemohon_id );

    @GET("get_bongkarmuat")
    Call<BongkarMuatModelList> getBongkarMuat(@Query("pemohon_id") int pemohon_id );

    @GET("get_sertifikatkeselamatan")
    Call<SertifikatKeselamatanModelList> getSertifikatKeselamatan(@Query("pemohon_id") int pemohon_id );

    @FormUrlEncoded
    @POST("get_rating")
    Call<ResponseBody> getRating(
            @Field("jenis") String jenis,
            @Field("int") int id
    );

    @FormUrlEncoded
    @POST("update_rating")
    Call<ResponseBody> updateRating(
            @Field("jenis") String jenis,
            @Field("id") int id,
            @Field("rating_kepuasan") float rating_kepuasan,
            @Field("komentar") String komentar
    );

    @GET("get_masalayar_active_req")
    Call<ResponseBody> getMasaLayarActiveCountRequest(@Query("pemohon_id") int pemohon_id );

    @GET("get_settings")
    Call<ResponseBody> getSettings();

    @FormUrlEncoded
    @POST("insert_masalayar")
    Call<ResponseBody> masaLayarBuatBaruRequest (@Field("pemohon_id") int pemohon_id);

    @FormUrlEncoded
    @POST("updatestatus_masalayar")
    Call<ResponseBody> masaLayarUbahStatusRequest(
            @Field("masalayar_id") int masalayar_id,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("insert_sertifikat_keselamatan")
    Call<ResponseBody> sertifikatKeselamatanBuatBaruRequest(@Field("kapal_id") int kapal_id);

    @GET("get_jenis_muatan")
    Call<ResponseBody> getJenisMuatan();

    @FormUrlEncoded
    @POST("insert_bongkarmuat")
    Call<ResponseBody> bongkarMuatBuatBaruRequest(
            @Field("pemohon_id") int pemohon_id,
            @Field("kode_biaya") String kode_biaya,
            @Field("jenis_muatan") String jenis_muatan,
            @Field("bobot") int bobot,
            @Field("nama_kapal") String nama_kapal,
            @Field("angkutan_nopol") String angkutan_nopol,
            @Field("angkutan_supir") String angkutan_supir
    );
}
