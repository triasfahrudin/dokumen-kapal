package com.kapal.dokumenkapal.util.api;

import com.kapal.dokumenkapal.ui.kapal.KapalModelList;
import com.kapal.dokumenkapal.ui.riwayatpelayaran.RiwayatPelayaranModelList;

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

    @GET("get_bukupelaut/{pemohon_id}")
    Call<ResponseBody> getBukuPelautRequest(@Path("pemohon_id") int pemohon_id);


    @GET("get_kapal")
    Call<KapalModelList> getKapal(@Query("pemohon_id") int pemohon_id);

    @GET("get_riwayatpelayaran")
    Call<RiwayatPelayaranModelList> getRiwayatPelayaran(@Query("pemohon_id") int pemohon_id);

    @GET("get_profile/{id}")
    Call<ResponseBody> getProfileRequest(@Path("id") int id);


    @POST("update_profile/{id}")
    Call<ResponseBody> updateProfileRequest(
            @Path("id") int id,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat
    );


    @Multipart
    @POST("uploadfile")
    Call<ResponseBody> uploadFile(
            @Part("jenis") String jenis,
            @Part("id") int id,
            @Part MultipartBody.Part file,
            @Part("filename") RequestBody name
    );


}