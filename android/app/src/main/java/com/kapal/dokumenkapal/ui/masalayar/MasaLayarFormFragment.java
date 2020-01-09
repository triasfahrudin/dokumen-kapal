package com.kapal.dokumenkapal.ui.masalayar;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasaLayarFormFragment extends Fragment {
    ProgressDialog loading;
    @BindView(R.id.masalayar_btnKirim)
    Button masalayarBtnKirim;

    private Context mContext;
    private BaseApiService mBaseApiService;
    private SharedPrefManager sharedPrefManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /*
         * nama, tgl_mohon,nomor_buku,kode_pelaut
         * ==> nama_sertifikat,nomor,penerbit
         *
         * */
        View root = inflater.inflate(R.layout.fragment_form_masalayar, container, false);
        ButterKnife.bind(this, root);

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

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

                MasaLayarFragment mf = new MasaLayarFragment();
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, MasaLayarFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }

    @OnClick(R.id.masalayar_btnKirim)
    public void onViewClicked() {

        new AlertDialog.Builder(mContext)
                .setTitle("Permohonan Pembuatan Masa Layar")
                .setMessage("Apakah anda yakin semua persyaratan dokumen sudah anda isi ?")
                .setPositiveButton("YA", (dialog, which) -> {
                    loading = ProgressDialog.show(mContext, null, "Membuat permohonan baru, Mohon tunggu...", true, false);
                    mBaseApiService.masaLayarBuatBaruRequest(sharedPrefManager.getSPID())
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        loading.dismiss();
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.body().string());
                                            if (jsonObject.getString("error").equals("false")) {

                                                Toast.makeText(mContext, "Permohonan baru berhasil dibuat", Toast.LENGTH_SHORT).show();

                                                MasaLayarFragment mf = new MasaLayarFragment();
                                                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .add(R.id.nav_host_fragment, mf, MasaLayarFragment.class.getSimpleName())
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
                                    Toasty.error(mContext, t.toString(), Toast.LENGTH_LONG).show();
                                    Log.e("debug", "onFailure: ERROR > " + t.toString());
                                    loading.dismiss();
                                }
                            });
                }).setNegativeButton("Batal", null).show();


    }
}
