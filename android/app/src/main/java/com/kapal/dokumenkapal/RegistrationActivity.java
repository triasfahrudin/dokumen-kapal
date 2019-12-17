package com.kapal.dokumenkapal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.cbPerusahaan)
    CheckBox cbPerusahaan;

    @BindView(R.id.reg_etName)
    EditText etName;

    @BindView(R.id.reg_etEmail)
    EditText etEmail;

    @BindView(R.id.reg_etPassword)
    EditText etPassword;

    ProgressDialog loading;

    Context mContext;
    BaseApiService mBaseApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);

        mContext = this;
        mBaseApiService = UtilsApi.getAPIService();

        TextView signIn_text = findViewById(R.id.signIn_text);
        signIn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });
    }


    @OnClick(R.id.btnSignUp)
    public void requestSignup(){
        /*
        *
        * @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("jenis") String jenis,
                                       @Field("nama") String nama,
                                       @Field("email") String email,
                                       @Field("password") String password
        * */
        String jenisAkun;
        if(cbPerusahaan.isChecked()){
            jenisAkun = "perusahaan";
        }else{
            jenisAkun = "perorangan";
        }

        loading = ProgressDialog.show(mContext,null,"Mohon tunggu...",true,false);
        mBaseApiService.registerRequest(jenisAkun,etName.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if(jsonObject.getString("error").equals("false")){


                                    Toast.makeText(mContext, "Pendaftaran Berhasil, Silahkan Masuk dengan akun anda", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(mContext, LoginActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();

                                }else {
                                    String error_message = jsonObject.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else{
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }
}
