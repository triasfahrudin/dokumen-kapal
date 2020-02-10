package com.kapal.dokumenkapal.ui.sertifikatpelaut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kapal.dokumenkapal.MainActivity;
import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.util.SetDate;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class SertifikatPelautFormFragment extends Fragment {
    ProgressDialog loading;
    @BindView(R.id.sp_etNamaSertifikat)
    EditText spEtNamaSertifikat;
    @BindView(R.id.sp_etNomor)
    EditText spEtNomor;
    @BindView(R.id.sp_etPenerbit)
    EditText spEtPenerbit;
    @BindView(R.id.sp_etTglTerbit)
    EditText spEtTglTerbit;
    @BindView(R.id.sp_btnUpload)
    Button spBtnUpload;
    @BindView(R.id.sp_btnUpdate)
    Button spBtnUpdate;
    @BindView(R.id.sp_btnDelete)
    Button spBtnDelete;
    @BindView(R.id.sp_etUpload)
    EditText spEtUpload;
    @BindView(R.id.formsertifikat_pelaut_error_msg)
    TextView formsertifikatPelautErrorMsg;
    @BindView(R.id.scroolview_sertifikat_pelaut)
    ScrollView scroolviewSertifikatPelaut;

    private Context mContext;
    private BaseApiService mBaseApiService;
    private SharedPrefManager sharedPrefManager;

    private int recyclerID;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_form_sertifikat_pelaut, container, false);
        ButterKnife.bind(this, root);

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        recyclerID = getArguments().getInt("id");
        spEtNamaSertifikat.setText(getArguments().getString("nama_sertifikat"));
        spEtNomor.setText(getArguments().getString("nomor"));
        spEtPenerbit.setText(getArguments().getString("penerbit"));
        spEtTglTerbit.setText(getArguments().getString("tgl_terbit"));

        SetDate tglTerbit = new SetDate(spEtTglTerbit, mContext);

        if (recyclerID == 0) {
            spBtnDelete.setVisibility(View.INVISIBLE);
        }

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Form Data Kapal");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();
        }

        return root;
    }


    @OnClick(R.id.sp_btnUpload)
    public void onSpBtnUploadClicked() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih file PDF"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri filepath = data.getData();

            spEtUpload.setText(filepath.getPath());
            uploadFile("sertifikat_pelaut", filepath);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void uploadFile(String jenis, Uri path) {
        String pdfname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        File file = new File(getRealPathFromURI(mContext,path));
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("filename", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), pdfname);

        loading = ProgressDialog.show(mContext, null, "Proses upload file, Mohon tunggu ...", true, false);

        mBaseApiService.uploadFile(jenis, this.recyclerID, sharedPrefManager.getSPID(), fileToUpload, filename)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.getString("error").equals("false")) {
                                    Toast.makeText(mContext, "Upload file berhasil", Toast.LENGTH_SHORT).show();
                                    SertifikatPelautFormFragment.this.recyclerID = jsonObject.getInt("last_id");
                                } else {
                                    String error_message = jsonObject.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Log.e("", "Response returned by website is : " + t.getMessage());
                    }
                });
    }

    @OnClick(R.id.sp_btnUpdate)
    public void onSpBtnUpdateClicked() {

        formsertifikatPelautErrorMsg.setVisibility(View.GONE);

        loading = ProgressDialog.show(mContext, null, "Menyimpan data, Mohon tunggu...", true, false);
        mBaseApiService.insertUpdateSertifikatPelautRequest(
                this.recyclerID,
                sharedPrefManager.getSPID(),
                spEtNamaSertifikat.getText().toString(),
                spEtNomor.getText().toString(),
                spEtPenerbit.getText().toString(),
                spEtTglTerbit.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("error").equals("false")) {

                            //SertifikatPelautFormFragment.this.recyclerID = jsonObject.getInt("last_id");
                            SertifikatPelautFragment mf = new SertifikatPelautFragment();
                            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.nav_host_fragment, mf, SertifikatPelautFragment.class.getSimpleName())
                                    .addToBackStack(null);
                            ft.commit();

                            Toast.makeText(mContext, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();

                            //spBtnDelete.setVisibility(View.VISIBLE);

                        } else {
                            String error_message = jsonObject.getString("error_msg");
                            Toast.makeText(mContext, "Error!", Toast.LENGTH_SHORT).show();
                            formsertifikatPelautErrorMsg.setVisibility(View.VISIBLE);
                            formsertifikatPelautErrorMsg.setText(error_message);

                            scroolviewSertifikatPelaut.fullScroll(ScrollView.FOCUS_UP);
                        }


                    } catch (JSONException | IOException e) {
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

    @Override
    //Pressed return button
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                SertifikatPelautFragment mf = new SertifikatPelautFragment();
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, SertifikatPelautFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }

    @OnClick(R.id.sp_btnDelete)
    void onSpBtnDeleteClicked() {

        new AlertDialog.Builder(mContext)
                .setTitle("Menghapus data")
                .setMessage("Apakah anda yakin ingin menghapus data ini ?")
                .setPositiveButton("HAPUS !", (dialog, which) -> {
                    loading = ProgressDialog.show(mContext, null, "Menghapus data, Mohon tunggu...", true, false);
                    mBaseApiService.delSertifikatPelautRequest(
                            SertifikatPelautFormFragment.this.recyclerID
                    ).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                loading.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    if (jsonObject.getString("error").equals("false")) {

                                        Toast.makeText(mContext, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();

                                        SertifikatPelautFragment mf = new SertifikatPelautFragment();
                                        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                                .beginTransaction()
                                                .add(R.id.nav_host_fragment, mf, SertifikatPelautFragment.class.getSimpleName())
                                                .addToBackStack(null);
                                        ft.commit();

                                    } else {
                                        String error_message = jsonObject.getString("error_msg");
                                        Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException | IOException e) {
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
                }).setNegativeButton("Batal", null).show();


    }
}
