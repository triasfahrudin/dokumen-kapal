package com.kapal.dokumenkapal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
public class SplashActivity extends AppCompatActivity {


    SharedPrefManager sharedPrefManager;
    BaseApiService mBaseApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);

        int SPLASH_TIME_OUT = 150;
        new Handler().postDelayed(() -> {


            mBaseApiService.getSettings()
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    if (jsonObject.getString("error").equals("false")) {

                                        String noRek = jsonObject.getJSONObject("setting").getString("nomor_rekening_bank");
                                        sharedPrefManager.saveSPString(SharedPrefManager.SP_SETTING_NO_REKENING, noRek);

                                        String namaRek = jsonObject.getJSONObject("setting").getString("nama_rekening_bank");
                                        sharedPrefManager.saveSPString(SharedPrefManager.SP_SETTING_NAMA_REKENING, namaRek);

                                    } else {
                                        String error_message = jsonObject.getString("error_msg");
                                        Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                            } else {

                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {


                        }
                    });


            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIME_OUT);
    }
}
