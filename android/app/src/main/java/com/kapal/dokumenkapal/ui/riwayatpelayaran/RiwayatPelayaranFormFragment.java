package com.kapal.dokumenkapal.ui.riwayatpelayaran;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPelayaranFormFragment extends Fragment {

    ProgressDialog loading;

    @BindView(R.id.rp_etNamaKapal)
    EditText rpEtNamaKapal;
    @BindView(R.id.rp_etTenagaMesin)
    EditText rpEtTenagaMesin;
    @BindView(R.id.rp_etJabatan)
    EditText rpEtJabatan;
    @BindView(R.id.rp_etTglNaik)
    EditText rpEtTglNaik;
    @BindView(R.id.rp_etTglTurun)
    EditText rpEtTglTurun;
    @BindView(R.id.rp_btnUpdate)
    Button rpBtnUpdate;
    @BindView(R.id.rp_btnDelete)
    Button rpBtnDelete;
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

        View root = inflater.inflate(R.layout.fragment_form_riwayat_pelayaran, container, false);
        ButterKnife.bind(this, root);

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        recyclerID = getArguments().getInt("id");
        rpEtNamaKapal.setText(getArguments().getString("nama_kapal"));
        rpEtTenagaMesin.setText(getArguments().getString("tenaga_mesin"));
        rpEtJabatan.setText(getArguments().getString("jabatan"));
        rpEtTglNaik.setText(getArguments().getString("tgl_naik"));
        rpEtTglTurun.setText(getArguments().getString("tgl_turun"));

        SetDate tglNaik = new SetDate(rpEtTglNaik, mContext);
        SetDate tglTurun = new SetDate(rpEtTglTurun, mContext);

        if (recyclerID == 0) {
            rpBtnDelete.setVisibility(View.INVISIBLE);
        }

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Form Data Kapal");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toasty.error(mContext, "Ada kesalahan!", Toast.LENGTH_LONG, true).show();
                }
            });
        }

        return root;


    }

    @OnClick(R.id.rp_etTglNaik)
    public void onRpEtTglNaikClicked() {
    }

    @OnClick(R.id.rp_etTglTurun)
    public void onRpEtTglTurunClicked() {
    }

    @OnClick(R.id.rp_btnUpdate)
    public void onRpBtnUpdateClicked() {

        loading = ProgressDialog.show(mContext, null, "Menyimpan data, Mohon tunggu...", true, false);
        mBaseApiService.insertUpdateRiwayatPelayaranRequest(
                this.recyclerID,
                sharedPrefManager.getSPID(),
                rpEtNamaKapal.getText().toString(),
                rpEtTenagaMesin.getText().toString(),
                rpEtJabatan.getText().toString(),
                rpEtTglNaik.getText().toString(),
                rpEtTglTurun.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("error").equals("false")) {

                            RiwayatPelayaranFormFragment.this.recyclerID = jsonObject.getInt("last_id");
                            Toast.makeText(mContext, "data kapal berhasil disimpan", Toast.LENGTH_SHORT).show();

                            rpBtnDelete.setVisibility(View.VISIBLE);

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

    @OnClick(R.id.rp_btnDelete)
    public void onRpBtnDeleteClicked() {

        new AlertDialog.Builder(mContext)
                .setTitle("Menghapus data")
                .setMessage("Apakah anda yakin ingin menghapus data ini ?")
                .setPositiveButton("HAPUS !", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loading = ProgressDialog.show(mContext, null, "Menghapus data, Mohon tunggu...", true, false);
                        mBaseApiService.delRiwayatPelayaranRequest(
                                RiwayatPelayaranFormFragment.this.recyclerID
                        ).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    loading.dismiss();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        if (jsonObject.getString("error").equals("false")) {

                                            Toast.makeText(mContext, "data riwayat pelayaran berhasil dihapus", Toast.LENGTH_SHORT).show();

                                            RiwayatPelayaranFragment mf = new RiwayatPelayaranFragment();
                                            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .add(R.id.nav_host_fragment, mf, RiwayatPelayaranFragment.class.getSimpleName())
                                                    .addToBackStack(null);
                                            ft.commit();

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
                }).setNegativeButton("Batal", null).show();


    }

    @Override
    //Pressed return button
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                RiwayatPelayaranFragment mf = new RiwayatPelayaranFragment();
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, RiwayatPelayaranFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }
}
