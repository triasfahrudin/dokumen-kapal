package com.kapal.dokumenkapal.ui.kapal;

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

    private static final int PILIH_SURAT_UKUR = 1;
    private static final int PILIH_SURAT_LAUT = 2;
    private static final int PILIH_SERTIFIKAT_KESELAMATAN = 3;
    private static final int PILIH_SERTIFIKAT_KLASIFIKASI = 4;
    private static final int PILIH_SERTIFIKAT_PMK = 5;
    private static final int PILIH_SERTIFIKAT_LIFERAFT = 6;
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
    @BindView(R.id.kapal_btnDelete)
    Button kapalBtnDelete;
    @BindView(R.id.kapal_etKodePengenal)
    EditText kapalEtKodePengenal;
    @BindView(R.id.kapal_etPelabuhanDaftar)
    EditText kapalEtPelabuhanDaftar;
    @BindView(R.id.kapal_etTglKontrak)
    EditText kapalEtTglKontrak;
    @BindView(R.id.kapal_etTglPeletakanLunas)
    EditText kapalEtTglPeletakanLunas;
    @BindView(R.id.kapal_etTglSerahTerima)
    EditText kapalEtTglSerahTerima;
    @BindView(R.id.kapal_etTglPerubahan)
    EditText kapalEtTglPerubahan;
    @BindView(R.id.formkapal_error_msg)
    TextView formkapalErrorMsg;
    @BindView(R.id.formkapal_scroolview)
    ScrollView formkapalScroolview;
    @BindView(R.id.kapal_etLambungTimbul)
    EditText kapalEtLambungTimbul;
    @BindView(R.id.kapal_etLokasiDokTerakhir)
    EditText kapalEtLokasiDokTerakhir;
    @BindView(R.id.kapal_etTglDokTerakhir)
    EditText kapalEtTglDokTerakhir;
    private Context mContext;
    private BaseApiService mBaseApiService;
    private SharedPrefManager sharedPrefManager;
    private int recyclerID;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_form_kapal, container, false);

        ButterKnife.bind(this, root);

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        recyclerID = Objects.requireNonNull(getArguments()).getInt("id");
        kapalEtNamaKapal.setText(getArguments().getString("nama_kapal"));
        kapalEtJenisKapal.setText(getArguments().getString("jenis_kapal"));
        kapalEtKodePengenal.setText(getArguments().getString("kode_pengenal"));
        kapalEtPelabuhanDaftar.setText(getArguments().getString("pelabuhan_daftar"));
        kapalEtIMONumber.setText(getArguments().getString("imo_number"));
        kapalEtGRT.setText(String.valueOf(getArguments().getInt("grt")));

        kapalEtLambungTimbul.setText(String.valueOf(getArguments().getInt("lambung_timbul")));

        kapalEtTglKontrak.setText(getArguments().getString("tgl_kontrak"));
        kapalEtTglPeletakanLunas.setText(getArguments().getString("tgl_peletakan_lunas"));
        kapalEtTglSerahTerima.setText(getArguments().getString("tgl_serah_terima"));
        kapalEtTglPerubahan.setText(getArguments().getString("tgl_perubahan"));

        kapalEtLokasiDokTerakhir.setText(getArguments().getString("lokasi_dok_terakhir"));
        kapalEtTglDokTerakhir.setText(getArguments().getString("tgl_dok_terakhir"));

        SetDate tglKontrak = new SetDate(kapalEtTglKontrak, mContext);
        SetDate tglpeletakanLunas = new SetDate(kapalEtTglPeletakanLunas, mContext);
        SetDate tglSerahTerima = new SetDate(kapalEtTglSerahTerima, mContext);
        SetDate tglPerubahan = new SetDate(kapalEtTglPerubahan, mContext);
        SetDate tglDokterakhir = new SetDate(kapalEtTglDokTerakhir,mContext);

        kapalEtKapasitasPenumpang.setText(String.valueOf(getArguments().getInt("kapasitas_penumpang")));
        kapalEtKapasitasRodaDua.setText(String.valueOf(getArguments().getInt("kapasitas_roda_dua")));
        kapalEtKapasitasRodaEmpat.setText(String.valueOf(getArguments().getInt("kapasitas_roda_empat")));

        if (recyclerID == 0) {
            kapalBtnDelete.setVisibility(View.INVISIBLE);
        }

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Form Data Kapal");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();
        }

        return root;
    }

    private void openDialog(int request_code) {
//        String[] mimeTypes = {"application/pdf"};
//
//        Intent intent;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
//            if (mimeTypes.length > 0) {
//                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//            }
//        } else {
//            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            String mimeTypesStr = "";
//            for (String mimeType : mimeTypes) {
//                mimeTypesStr += mimeType + "|";
//            }
//            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
//        }
//        startActivityForResult(Intent.createChooser(intent, getString(R.string.text_pilih_file_pdf)), request_code);

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih file PDF"), request_code);
    }

    @OnClick(R.id.kapal_btnUploadSuratUkur)
    public void onKapalBtnUploadSuratUkurClicked() {
        openDialog(PILIH_SURAT_UKUR);
    }

    @OnClick(R.id.kapal_btnUploadSuratLaut)
    public void onKapalBtnUploadSuratLautClicked() {

        openDialog(PILIH_SURAT_LAUT);
    }

    @OnClick(R.id.kapal_btnUploadSertifikatKeselamatan)
    public void onKapalBtnUploadSertifikatKeselamatanClicked() {
        openDialog(PILIH_SERTIFIKAT_KESELAMATAN);

    }

    @OnClick(R.id.kapal_btnUploadSertifikatKlasifikasi)
    public void onKapalBtnUploadSertifikatKlasifikasiClicked() {
        openDialog(PILIH_SERTIFIKAT_KLASIFIKASI);
    }

    @OnClick(R.id.kapal_btnUploadSertifikatPmk)
    public void onKapalBtnUploadSertifikatPmkClicked() {
        openDialog(PILIH_SERTIFIKAT_PMK);
    }

    @OnClick(R.id.kapal_btnUploadSertifikatLiferaft)
    public void onKapalBtnUploadSertifikatLiferaftClicked() {
        openDialog(PILIH_SERTIFIKAT_LIFERAFT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri filepath = data.getData();

            switch (requestCode) {

                case PILIH_SURAT_UKUR:
                    kapalEtUploadSuratUkur.setText(filepath.getPath());
                    uploadFile("kapal_surat_ukur", filepath);
                    break;

                case PILIH_SURAT_LAUT:
                    kapalEtUploadSuratLaut.setText(filepath.getPath());
                    uploadFile("kapal_surat_laut", filepath);
                    break;

                case PILIH_SERTIFIKAT_KESELAMATAN:
                    kapalEtUploadSertifikatKeselamatan.setText(filepath.getPath());
                    uploadFile("kapal_sertifikat_keselamatan", filepath);
                    break;

                case PILIH_SERTIFIKAT_KLASIFIKASI:
                    kapalEtUploadSertifikatKlasifikasi.setText(filepath.getPath());
                    uploadFile("kapal_sertifikat_klasifikasi", filepath);
                    break;
                case PILIH_SERTIFIKAT_PMK:
                    kapalEtUploadSertifikatPmk.setText(filepath.getPath());
                    uploadFile("kapal_sertifikat_pmk", filepath);
                    break;

                case PILIH_SERTIFIKAT_LIFERAFT:
                    kapalEtUploadSertifikatLiferaft.setText(filepath.getPath());
                    uploadFile("kapal_sertifikat_liferaft", filepath);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + data);
            }
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
                                    KapalFormFragment.this.recyclerID = jsonObject.getInt("last_id");
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

        formkapalErrorMsg.setVisibility(View.GONE);

        loading = ProgressDialog.show(mContext, null, "Menyimpan data, Mohon tunggu...", true, false);
        mBaseApiService.insertUpdateKapalRequest(
                this.recyclerID,
                sharedPrefManager.getSPID(),
                kapalEtNamaKapal.getText().toString(),
                kapalEtJenisKapal.getText().toString(),
                kapalEtKodePengenal.getText().toString(),
                kapalEtPelabuhanDaftar.getText().toString(),
                kapalEtIMONumber.getText().toString(),
                Integer.parseInt(kapalEtLambungTimbul.getText().toString()),
                Integer.parseInt(kapalEtGRT.getText().toString()),
                kapalEtTglKontrak.getText().toString(),
                kapalEtTglPeletakanLunas.getText().toString(),
                kapalEtTglSerahTerima.getText().toString(),
                kapalEtTglPerubahan.getText().toString(),

                kapalEtLokasiDokTerakhir.getText().toString(),
                kapalEtTglDokTerakhir.getText().toString(),

                Integer.parseInt(kapalEtKapasitasPenumpang.getText().toString()),
                Integer.parseInt(kapalEtKapasitasRodaDua.getText().toString()),
                Integer.parseInt(kapalEtKapasitasRodaEmpat.getText().toString())
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("error").equals("false")) {

                            KapalFormFragment.this.recyclerID = jsonObject.getInt("last_id");
                            Toast.makeText(mContext, "data kapal berhasil disimpan", Toast.LENGTH_SHORT).show();

                            kapalBtnDelete.setVisibility(View.VISIBLE);

                        } else {
                            String error_message = jsonObject.getString("error_msg");
                            Toast.makeText(mContext, "Error!", Toast.LENGTH_SHORT).show();
                            formkapalErrorMsg.setVisibility(View.VISIBLE);
                            formkapalErrorMsg.setText(error_message);

                            formkapalScroolview.fullScroll(ScrollView.FOCUS_UP);
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
                Toasty.error(mContext, "Ada kesalahan!\n" + t.toString(), Toast.LENGTH_LONG, true).show();
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

                KapalFragment mf = new KapalFragment();
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, KapalFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }

    @OnClick(R.id.kapal_btnDelete)
    void onBtnDeleteClicked() {

        new AlertDialog.Builder(mContext)
                .setTitle("Menghapus data")
                .setMessage("Apakah anda yakin ingin menghapus data ini?")
                .setPositiveButton("HAPUS !", (dialog, which) -> {
                    loading = ProgressDialog.show(mContext, null, "Menghapus data, Mohon tunggu...", true, false);
                    mBaseApiService.delKapalRequest(
                            KapalFormFragment.this.recyclerID
                    ).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                loading.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    if (jsonObject.getString("error").equals("false")) {

                                        Toast.makeText(mContext, "data kapal berhasil dihapus", Toast.LENGTH_SHORT).show();

                                        KapalFragment mf = new KapalFragment();
                                        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                                .beginTransaction()
                                                .add(R.id.nav_host_fragment, mf, KapalFragment.class.getSimpleName())
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
                            Toasty.error(mContext, "Ada kesalahan!\n" + t.toString(), Toast.LENGTH_LONG, true).show();
                            loading.dismiss();
                        }
                    });

                }).setNegativeButton("Batal", null).show();


    }
}
