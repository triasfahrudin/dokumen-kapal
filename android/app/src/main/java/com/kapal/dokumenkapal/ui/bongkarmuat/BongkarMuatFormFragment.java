package com.kapal.dokumenkapal.ui.bongkarmuat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kapal.dokumenkapal.MainActivity;
import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;

public class BongkarMuatFormFragment extends Fragment {
    ProgressDialog loading;
    @BindView(R.id.bongkarmuat_btnKirim)
    Button btnKirim;
    @BindView(R.id.bongkarmuat_spinner)
    Spinner spinnerPilihJenisMuatan;
    @BindView(R.id.bongkarmuat_bobot)
    EditText etBobot;
    @BindView(R.id.bongkarmuat_jenis_muatan)
    EditText etJenisMuatan;
    @BindView(R.id.bongkarmuat_nama_kapal)
    EditText etNamaKapal;
    @BindView(R.id.bongkarmuat_angkutan_nopol)
    EditText etAngkutanNopol;
    @BindView(R.id.bongkarmuat_angkutan_supir)
    EditText etAngkutanSupir;

    private Context mContext;
    private BaseApiService mBaseApiService;
    private SharedPrefManager sharedPrefManager;

    private ArrayList<SpinPilihJenisMuatanModel> pilihJenisMuatanModelArrayList = new ArrayList<>();
    private SpinPilihJenisMuatanAdapter spinnerArrayAdapter;

    private String selectedJenisMuatanKode;

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
        View root = inflater.inflate(R.layout.fragment_form_bongkarmuat, container, false);
        ButterKnife.bind(this, root);

        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();
        }

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        loading = ProgressDialog.show(mContext, null, "Mengambil data ...", true, false);

        mBaseApiService.getJenisMuatan()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                String jsonResponse;
                                try {
                                    jsonResponse = response.body().string();
                                    fillSpinnerPilihJenisMuatan(jsonResponse, root);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            loading.dismiss();

                        } else {
                            loading.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Toasty.error(mContext, String.format("Ada kesalahan! %s", t.getMessage()), Toast.LENGTH_LONG, true).show();
                        loading.dismiss();
                    }
                });


        spinnerPilihJenisMuatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinPilihJenisMuatanModel spinnerModel = spinnerArrayAdapter.getItem(position);
                selectedJenisMuatanKode = Objects.requireNonNull(spinnerModel).getKode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }

    private void fillSpinnerPilihJenisMuatan(String response, View root) {
        try {

            JSONObject obj = new JSONObject(response);
            if (obj.optString("error").equals("false")) {

                JSONArray dataArray = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    SpinPilihJenisMuatanModel spinnerModel = new SpinPilihJenisMuatanModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    spinnerModel.setKode(dataobj.getString("kode"));
                    spinnerModel.setAlias(dataobj.getString("alias"));

                    pilihJenisMuatanModelArrayList.add(spinnerModel);
                }

                spinnerArrayAdapter = new SpinPilihJenisMuatanAdapter(mContext, simple_spinner_item, pilihJenisMuatanModelArrayList);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinnerPilihJenisMuatan.setAdapter(spinnerArrayAdapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    //Pressed return button - returns to the results menu
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                BongkarMuatFragment mf = new BongkarMuatFragment();
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, BongkarMuatFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }

    @OnClick(R.id.bongkarmuat_btnKirim)
    public void onViewClicked() {

        if (pilihJenisMuatanModelArrayList.isEmpty()) {
            Toasty.error(mContext, "Tidak ada data jenis muatan!", Toast.LENGTH_SHORT).show();
        } else {

            new AlertDialog.Builder(mContext)
                    .setTitle("Permohonan Pembuatan Surat Bongkar Muat")
                    .setMessage("Apakah anda yakin semua persyaratan dokumen sudah anda isi ?")
                    .setPositiveButton("YA", (dialog, which) -> {
                        loading = ProgressDialog.show(mContext, null, "Membuat permohonan baru, Mohon tunggu...", true, false);
                        mBaseApiService.bongkarMuatBuatBaruRequest(
                                sharedPrefManager.getSPID(),
                                selectedJenisMuatanKode,
                                etJenisMuatan.getText().toString(),
                                Integer.parseInt(etBobot.getText().toString()),
                                etNamaKapal.getText().toString(),
                                etAngkutanNopol.getText().toString(),
                                etAngkutanSupir.getText().toString())
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            loading.dismiss();
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.body().string());
                                                if (jsonObject.getString("error").equals("false")) {

                                                    Toast.makeText(mContext, "Permohonan baru berhasil dibuat", Toast.LENGTH_SHORT).show();

                                                    BongkarMuatFragment mf = new BongkarMuatFragment();
                                                    FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                                            .beginTransaction()
                                                            .add(R.id.nav_host_fragment, mf, BongkarMuatFragment.class.getSimpleName())
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
                                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                                        Toasty.error(mContext, t.toString(), Toast.LENGTH_LONG).show();
                                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                                        loading.dismiss();
                                    }
                                });
                    }).setNegativeButton("Batal", null).show();
        }


    }

}
