package com.kapal.dokumenkapal.ui.sertifikatkeselamatan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.util.FileUtils;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
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

public class SertifikatKeselamatanFormBayarFragment extends Fragment {
    private static final int RESULT_OK = -1;

    @BindView(R.id.sertifikatkeselamatan_tvFormBayarMsg)
    TextView textViewMsg;
    @BindView(R.id.sertifikatkeselamatan_btnUploadBuktiBayar)
    Button btnUploadBuktiBayar;

    private Context mContext;
    private BaseApiService mBaseApiService;
    private SharedPrefManager sharedPrefManager;

    private ProgressDialog loading;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*
         * nama, tgl_mohon,nomor_buku,kode_pelaut
         * ==> nama_sertifikat,nomor,penerbit
         * */
        View root = inflater.inflate(R.layout.fragment_form_bayar_sertifikat_keselamatan, container, false);
        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);
        ButterKnife.bind(this, root);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        double biaya = Objects.requireNonNull(getArguments()).getDouble("biaya");

        textViewMsg.setText(
                String.format(Locale.US, "Segera lakukan pembayaran ke rekening %s A.n %s sebesar %s", sharedPrefManager.getSpSettingNoRekening(),sharedPrefManager.getSpSettingNamaRekening(), formatRupiah.format((double)biaya) ));
        return root;
    }

    @Override
    //Pressed return button - returns to the results menu
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                SertifikatKeselamatanFragment mf = new SertifikatKeselamatanFragment();
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, SertifikatKeselamatanFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            uploadFile(requestCode, FileUtils.getPath(mContext, uri));
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadFile(int recyclerID, String path) {
        String fileName = String.valueOf(Calendar.getInstance().getTimeInMillis());

        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("filename", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), fileName);

        loading = ProgressDialog.show(mContext, null, "Proses upload file, Mohon tunggu ...", true, false);

        mBaseApiService.uploadFile("sertifikat_keselamatan", recyclerID, sharedPrefManager.getSPID(), fileToUpload, filename)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.getString("error").equals("false")) {
                                    Toast.makeText(mContext, "Upload file berhasil", Toast.LENGTH_SHORT).show();

                                    SertifikatKeselamatanFragment mf = new SertifikatKeselamatanFragment();
                                    FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                            .beginTransaction()
                                            .add(R.id.nav_host_fragment, mf, SertifikatKeselamatanFragment.class.getSimpleName())
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
                        loading.dismiss();
                        Log.e("", "Response returned by website is : " + t.getMessage());
                    }
                });
    }

    @OnClick(R.id.sertifikatkeselamatan_btnUploadBuktiBayar)
    public void onViewClicked() {
        int recyclerId = Objects.requireNonNull(getArguments()).getInt("recyclerId");

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Pilih Image"), recyclerId);
    }
}
