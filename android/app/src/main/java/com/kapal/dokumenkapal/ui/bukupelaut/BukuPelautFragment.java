package com.kapal.dokumenkapal.ui.bukupelaut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kapal.dokumenkapal.MainActivity;
import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.menuprofiledata.MenuProfileDataFragment;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class BukuPelautFragment extends Fragment {

    private static final int BUFFER_SIZE = 1024 * 2;
    @BindView(R.id.pbp_etNomorBuku)
    EditText etNomorBuku;
    @BindView(R.id.pbp_etKodePelaut)
    EditText etKodePelaut;
    @BindView(R.id.pbp_etNomorDaftar)
    EditText etNomorDaftar;
    @BindView(R.id.pbp_etUpload)
    EditText etUpload;
    @BindView(R.id.pbp_btnUpload)
    Button btnUpload;
    ProgressDialog loading;
    Context mContext;
    BaseApiService mBaseApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_form_bukupelaut, container, false);
        ButterKnife.bind(this, root);

        androidx.appcompat.widget.Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Buku Pelaut");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toasty.error(mContext, "Ada kesalahan!", Toast.LENGTH_LONG, true).show();
                }
            });
        }

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);
        loading = ProgressDialog.show(mContext, null, "Mengambil data ...", true, false);

        mBaseApiService.getBukuPelautRequest(sharedPrefManager.getSPID())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.getString("error").equals("false")) {

                                    etKodePelaut.setText(jsonObject.getJSONObject("buku_pelaut").getString("kode_pelaut"));
                                    etNomorBuku.setText(jsonObject.getJSONObject("buku_pelaut").getString("nomor_buku"));
                                    etNomorDaftar.setText(jsonObject.getJSONObject("buku_pelaut").getString("nomor_daftar"));

                                } else {
                                    String error_message = jsonObject.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

        return root;
    }

    @OnClick(R.id.pbp_btnUpload)
    public void btnLoadFileClicked() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String filepath = data.getData().getPath();
            etUpload.setText(filepath);
            uploadFile(filepath);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @OnClick(R.id.pbp_btnSubmit)
    public void btnSubmitClicked() {

        loading = ProgressDialog.show(mContext, null, "Update Buku Pelaut, Mohon tunggu...", true, false);

        mBaseApiService.updateBukuPelautRequest(
                sharedPrefManager.getSPID(),
                etNomorBuku.getText().toString(),
                etKodePelaut.getText().toString(),
                etNomorDaftar.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("error").equals("false")) {

                            Toast.makeText(mContext, "Buku Pelaut berhasil diupdate", Toast.LENGTH_SHORT).show();

                        } else {
                            String error_message = jsonObject.getString("error_msg");
                            Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
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


    private void uploadFile(String path) {
        String pdfname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("filename", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), pdfname);

        loading = ProgressDialog.show(mContext, null, "Proses upload file, Mohon tunggu ...", true, false);

        mBaseApiService.uploadFile("buku_pelaut", sharedPrefManager.getSPID(), fileToUpload, filename)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.e("", "Response returned by website is : " + response.code());

                        Toast.makeText(mContext, "Upload file berhasil", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Log.e("", "Response returned by website is : " + t.getMessage());
                    }
                });
    }

    @Override
    //Pressed return button
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                MenuProfileDataFragment mf = new MenuProfileDataFragment();
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, MenuProfileDataFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }


}
