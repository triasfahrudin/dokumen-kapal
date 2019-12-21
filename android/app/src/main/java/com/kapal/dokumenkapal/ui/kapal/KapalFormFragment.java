package com.kapal.dokumenkapal.ui.kapal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kapal.dokumenkapal.MainActivity;
import com.kapal.dokumenkapal.R;
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

public class KapalFormFragment extends Fragment {

    ProgressDialog loading;
    @BindView(R.id.kapal_etNamaKapal)
    EditText kapalEtNamaKapal;
    @BindView(R.id.kapal_etJenisKapal)
    EditText kapalEtJenisKapal;
    @BindView(R.id.kapal_etIMONumber)
    EditText kapalEtIMONumber;
    @BindView(R.id.kapal_etGRT)
    EditText kapalEtGRT;
    @BindView(R.id.kapal_etKapasitasPenumpang)
    EditText kapalEtKapasitasPenumpang;
    @BindView(R.id.kapal_etKapasitasRodaDua)
    EditText kapalEtKapasitasRodaDua;
    @BindView(R.id.kapal_etKapasitasRodaEmpat)
    EditText kapalEtKapasitasRodaEmpat;
    @BindView(R.id.kapal_btnUploadSuratUkur)
    Button kapalBtnUploadSuratUkur;
    @BindView(R.id.kapal_btnUploadSuratLaut)
    Button kapalBtnUploadSuratLaut;
    @BindView(R.id.kapal_btnUploadSertifikatKeselamatan)
    Button kapalBtnUploadSertifikatKeselamatan;
    @BindView(R.id.kapal_btnUploadSertifikatKlasifikasi)
    Button kapalBtnUploadSertifikatKlasifikasi;
    @BindView(R.id.kapal_btnUploadSertifikatPmk)
    Button kapalBtnUploadSertifikatPmk;
    @BindView(R.id.kapal_btnUploadSertifikatLiferaft)
    Button kapalBtnUploadSertifikatLiferaft;
    @BindView(R.id.kapal_btnUpdate)
    Button kapalBtnUpdate;
    @BindView(R.id.kapal_etUploadSuratUkur)
    EditText kapalEtUploadSuratUkur;
    @BindView(R.id.kapal_etUploadSuratLaut)
    EditText kapalEtUploadSuratLaut;
    @BindView(R.id.kapal_etUploadSertifikatKeselamatan)
    EditText kapalEtUploadSertifikatKeselamatan;
    @BindView(R.id.kapal_etUploadSertifikatKlasifikasi)
    EditText kapalEtUploadSertifikatKlasifikasi;
    @BindView(R.id.kapal_etUploadSertifikatPmk)
    EditText kapalEtUploadSertifikatPmk;
    @BindView(R.id.kapal_etUploadSertifikatLiferaft)
    EditText kapalEtUploadSertifikatLiferaft;

    private Context mContext;
    private BaseApiService mBaseApiService;
    private SharedPrefManager sharedPrefManager;

    private int recyclerKapalID;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_form_kapal, container, false);
        ButterKnife.bind(this, root);

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        recyclerKapalID = getArguments().getInt("id");
        kapalEtNamaKapal.setText(getArguments().getString("nama_kapal"));
        kapalEtJenisKapal.setText(getArguments().getString("jenis_kapal"));
        kapalEtIMONumber.setText(getArguments().getString("imo_number"));
        kapalEtGRT.setText(String.valueOf(getArguments().getInt("grt")));
        kapalEtKapasitasPenumpang.setText(String.valueOf(getArguments().getInt("kapasitas_penumpang")));
        kapalEtKapasitasRodaDua.setText(String.valueOf(getArguments().getInt("kapasitas_roda_dua")));
        kapalEtKapasitasRodaEmpat.setText(String.valueOf(getArguments().getInt("kapasitas_roda_empat")));


        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Form Data Kapal");
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

        return root;
    }

    private static final int PILIH_SURAT_UKUR = 1;
    private static final int PILIH_SURAT_LAUT = 2;
    private static final int PILIH_SERTIFIKAT_KESELAMATAN = 3;
    private static final int PILIH_SERTIFIKAT_KLASIFIKASI = 4;
    private static final int PILIH_SERTIFIKAT_PMK = 5;
    private static final int PILIH_SERTIFIKAT_LIFERAFT = 6;

    @OnClick(R.id.kapal_btnUploadSuratUkur)
    public void onKapalBtnUploadSuratUkurClicked() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih file PDF"), PILIH_SURAT_UKUR);

    }

    @OnClick(R.id.kapal_btnUploadSuratLaut)
    public void onKapalBtnUploadSuratLautClicked() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih file PDF"), PILIH_SURAT_LAUT);
    }

    @OnClick(R.id.kapal_btnUploadSertifikatKeselamatan)
    public void onKapalBtnUploadSertifikatKeselamatanClicked() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih file PDF"), PILIH_SERTIFIKAT_KESELAMATAN);

    }

    @OnClick(R.id.kapal_btnUploadSertifikatKlasifikasi)
    public void onKapalBtnUploadSertifikatKlasifikasiClicked() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih file PDF"), PILIH_SERTIFIKAT_KLASIFIKASI);

    }

    @OnClick(R.id.kapal_btnUploadSertifikatPmk)
    public void onKapalBtnUploadSertifikatPmkClicked() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih file PDF"), PILIH_SERTIFIKAT_PMK);

    }

    @OnClick(R.id.kapal_btnUploadSertifikatLiferaft)
    public void onKapalBtnUploadSertifikatLiferaftClicked() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih file PDF"), PILIH_SERTIFIKAT_LIFERAFT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String filepath = data.getData().getPath();

            switch (requestCode) {

                case PILIH_SURAT_UKUR:
                    kapalEtUploadSuratUkur.setText(filepath);
                    uploadFile("kapal_surat_ukur", filepath);
                    break;

                case PILIH_SURAT_LAUT:
                    kapalEtUploadSuratLaut.setText(filepath);
                    uploadFile("kapal_surat_laut", filepath);
                    break;

                case PILIH_SERTIFIKAT_KESELAMATAN:
                    kapalEtUploadSertifikatKeselamatan.setText(filepath);
                    uploadFile("kapal_sertifikat_keselamatan", filepath);
                    break;

                case PILIH_SERTIFIKAT_KLASIFIKASI:
                    kapalEtUploadSertifikatKlasifikasi.setText(filepath);
                    uploadFile("kapal_sertifikat_klasifikasi", filepath);
                    break;
                case PILIH_SERTIFIKAT_PMK:
                    kapalEtUploadSertifikatPmk.setText(filepath);
                    uploadFile("kapal_sertifikat_pmk", filepath);
                    break;

                case PILIH_SERTIFIKAT_LIFERAFT:
                    kapalEtUploadSertifikatLiferaft.setText(filepath);
                    uploadFile("kapal_sertifikat_liferaft", filepath);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + data);
            }

//            etUpload.setText(filepath);

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadFile(String jenis, String path) {
        String pdfname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("filename", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), pdfname);

        loading = ProgressDialog.show(mContext, null, "Proses upload file, Mohon tunggu ...", true, false);

        mBaseApiService.uploadFile(jenis, sharedPrefManager.getSPID(), this.recyclerKapalID, fileToUpload, filename)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                      Log.e("", "Response returned by website is : " + response.code());
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.getString("error").equals("false")) {
                                    Toast.makeText(mContext, "Upload file berhasil", Toast.LENGTH_SHORT).show();
                                    KapalFormFragment.this.recyclerKapalID = jsonObject.getInt("last_id");
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

    @OnClick(R.id.kapal_btnUpdate)
    public void onKapalBtnUpdateClicked() {
    }
}
