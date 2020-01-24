package com.kapal.dokumenkapal.ui.masalayar;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kapal.dokumenkapal.MainActivity;
import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.menupermohonan.MenuPermohonanFragment;
import com.kapal.dokumenkapal.ui.menuprofiledata.MenuProfileDataFragment;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasaLayarFragment extends Fragment {

    private MasaLayarAdapter masaLayarAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;

    private Context mContext;
    private BaseApiService mBaseApiService;
    private SharedPrefManager sharedPrefManager;

    private ProgressDialog loading;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_listview_masalayar, container, false);
        swipe = root.findViewById(R.id.masalayar_swipeContainer);

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Masa Layar");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.show();

            floatingActionButton.setOnClickListener(view -> {

                //Cek apakah ada permohonan masa layar yang belum selesai ?
                //karena permohonan masa layar hanya boleh ada satu yang aktif

                mBaseApiService.getMasaLayarActiveCountRequest(sharedPrefManager.getSPID())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                if (response.isSuccessful()) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        if (jsonObject.getString("error").equals("false")) {
                                            int req_act_count = jsonObject.getJSONObject("masa_layar").getInt("active_req");
                                            if (req_act_count == 0) {
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("id", 0);
                                                bundle.putString("kode", "");
                                                bundle.putString("tgl_mohon", "");
                                                bundle.putString("status", "");

                                                MasaLayarFormFragment fragment = new MasaLayarFormFragment();
                                                fragment.setArguments(bundle);
                                                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                                                activity.getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.nav_host_fragment, fragment, MasaLayarFormFragment.class.getSimpleName())
                                                        .addToBackStack(null)
                                                        .commit();
                                            } else {
                                                Toasty.error(mContext, "Ada permohonan pembuatan Masa Layar yang belum selesai!\nPermohonan baru tidak diperkenankan", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            String error_message = jsonObject.getString("error_msg");
                                            Toasty.error(mContext, error_message, Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {

                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                                Toasty.error(mContext, "Ada kesalahan!\n" + t.toString(), Toast.LENGTH_LONG, true).show();
                                loading.dismiss();
                            }
                        });


            });
        }

        swipe.setOnRefreshListener(() -> {
            swipe.setRefreshing(false);
            MasaLayarFragment.this.loadData();
        });

        loadData();

        return root;
    }


    private void loadData() {

        loading = ProgressDialog.show(mContext, null, "Mengambil data ...", true, false);

        mBaseApiService.getMasaLayar(sharedPrefManager.getSPID())
                .enqueue(new Callback<MasaLayarModelList>() {
                    @Override
                    public void onResponse(@NonNull Call<MasaLayarModelList> call, @NonNull Response<MasaLayarModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generateMasaLayarList(Objects.requireNonNull(response.body()).getMasaLayarArrayList());
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<MasaLayarModelList> call, Throwable t) {
                        Toasty.error(mContext, "Ada kesalahan!", Toast.LENGTH_LONG, true).show();
                        loading.dismiss();
                    }
                });


    }

    private void generateMasaLayarList(ArrayList<MasaLayarModelRecycler> masaLayarArrayList) {

        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recycler_view_masalayar_list);
        masaLayarAdapter = new MasaLayarAdapter(masaLayarArrayList);

        masaLayarAdapter.onBindCallBack = (jenis, viewHolder, position) -> {

            if ("upload_file".equals(jenis)) {
                Bundle bundle = new Bundle();
                bundle.putInt("recyclerId", viewHolder.rowId);
                bundle.putDouble("biaya", viewHolder.biaya);

                MasaLayarFormBayarFragment fragment = new MasaLayarFormBayarFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) getView().getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, MasaLayarFormBayarFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();


            } else if ("give_rating".equals(jenis)) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", viewHolder.rowId);
                bundle.putFloat("rating_kepuasan", viewHolder.rating_kepuasan);
                bundle.putString("komentar", viewHolder.komentar);

                MasaLayarRatingFragment fragment = new MasaLayarRatingFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) getView().getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, MasaLayarRatingFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();

            } else if ("revisi_berkas".equals(jenis)) {

                new AlertDialog.Builder(mContext)
                        .setTitle("Revisi Berkas")
                        .setMessage("Apakah anda yakin semua persyaratan dokumen sudah anda perbaiki ?")
                        .setPositiveButton("YA", (dialog, which) -> {
                            loading = ProgressDialog.show(mContext, null, "Merubah status permohonan, mohon menunggu...", true, false);
                            mBaseApiService.masaLayarUbahStatusRequest(viewHolder.rowId, "210")
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                            if (response.isSuccessful()) {
                                                loading.dismiss();
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                                    if (jsonObject.getString("error").equals("false")) {

                                                        Toast.makeText(mContext, "Status permohonan berhasil diubah", Toast.LENGTH_SHORT).show();

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
                        }).setNegativeButton("Belum", (dialog, which) -> {

                    MenuProfileDataFragment mf = new MenuProfileDataFragment();
                    FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.nav_host_fragment, mf, MenuProfileDataFragment.class.getSimpleName())
                            .addToBackStack(null);
                    ft.commit();

                }).show();


            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        };


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(masaLayarAdapter);

    }

    @Override
    //Pressed return button
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                MenuPermohonanFragment mf = new MenuPermohonanFragment();
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, MenuPermohonanFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }


}
