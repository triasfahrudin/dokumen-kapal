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
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.kapal.dokumenkapal.ui.masalayar.MasaLayarFragment;
import com.kapal.dokumenkapal.ui.menupermohonan.MenuPermohonanFragment;
import com.kapal.dokumenkapal.ui.sertifikatpelaut.SertifikatPelautFragment;
import com.kapal.dokumenkapal.util.FileUtils;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class SertifikatKeselamatanFragment extends Fragment {

    private SertifikatKeselamatanAdapter sertifikatKeselamatanAdapter;
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

        View root = inflater.inflate(R.layout.fragment_listview_sertifikat_keselamatan, container, false);
        swipe = root.findViewById(R.id.sertifikatkeselamatan_swipeContainer);

        Toolbar toolbar = (Toolbar) Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Data Permohonan Pembuatan Sertifikat Keselamatan");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.show();

            floatingActionButton.setOnClickListener(view -> {

                Bundle bundle = new Bundle();
                bundle.putInt("id", 0);
                bundle.putString("kode", "");
                bundle.putString("tgl_mohon", "");
                bundle.putString("status", "");

                SertifikatKeselamatanFormFragment fragment = new SertifikatKeselamatanFormFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, SertifikatKeselamatanFormFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            });
        }

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        swipe.setOnRefreshListener(() -> {
            swipe.setRefreshing(false);
            //swipe.setEnabled(false);
            loadData();
        });

        loadData();

        return root;
    }

    private void loadData() {

        loading = ProgressDialog.show(mContext, null, getString(R.string.mengambil_data), true, false);

        mBaseApiService.getSertifikatKeselamatan(sharedPrefManager.getSPID())
                .enqueue(new Callback<SertifikatKeselamatanModelList>() {
                    @Override
                    public void onResponse(@NonNull Call<SertifikatKeselamatanModelList> call, @NonNull Response<SertifikatKeselamatanModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generateSertifikatKeselamatanList(Objects.requireNonNull(response.body()).getSertifikatKeselamatanArrayList());
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SertifikatKeselamatanModelList> call, Throwable t) {
                        Toasty.error(mContext, "Ada kesalahan!\n" + t.toString(), Toast.LENGTH_LONG, true).show();
                        loading.dismiss();
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            uploadFile("sertifikat_keselamatan", requestCode, FileUtils.getPath(mContext, uri));
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadFile(String jenis, int recyclerID, String path) {
        String fileName = String.valueOf(Calendar.getInstance().getTimeInMillis());

        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("filename", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), fileName);

        loading = ProgressDialog.show(mContext, null, "Proses upload file, Mohon tunggu ...", true, false);

        mBaseApiService.uploadFile(jenis, recyclerID, sharedPrefManager.getSPID(), fileToUpload, filename)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.getString("error").equals("false")) {
                                    Toast.makeText(mContext, "Upload file berhasil", Toast.LENGTH_SHORT).show();
                                    loadData();
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
    }

    private void generateSertifikatKeselamatanList(ArrayList<SertifikatKeselamatanModelRecycler> sertifikatKeselamatanArrayList) {

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_sertifikatkeselamatan_list);
        sertifikatKeselamatanAdapter = new SertifikatKeselamatanAdapter(sertifikatKeselamatanArrayList);

        sertifikatKeselamatanAdapter.onBindCallBack = (jenis, viewHolder, position) -> {

            if ("upload_file".equals(jenis)) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Pilih Image"), viewHolder.rowId);
            } else if ("give_rating".equals(jenis)) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", viewHolder.rowId);
                bundle.putFloat("rating_kepuasan", viewHolder.rating_kepuasan);
                bundle.putString("komentar", viewHolder.komentar);

                SertifikatKeselamatanRatingFragment fragment = new SertifikatKeselamatanRatingFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) getView().getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, SertifikatKeselamatanRatingFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        };


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sertifikatKeselamatanAdapter);

    }

    @Override
    //Pressed return button
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                MenuPermohonanFragment mf = new MenuPermohonanFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager()
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
