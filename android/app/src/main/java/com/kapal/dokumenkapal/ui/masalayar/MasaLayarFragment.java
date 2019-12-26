package com.kapal.dokumenkapal.ui.masalayar;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kapal.dokumenkapal.MainActivity;
import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.menupermohonan.MenuPermohonanFragment;
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

public class MasaLayarFragment extends Fragment {

    private MasaLayarAdapter masaLayarAdapter;
    private RecyclerView recyclerView;

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

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Data Permohonan Masa Layar");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.show();

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
                }
            });
        }

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        loading = ProgressDialog.show(mContext, null, "Mengambil data ...", true, false);

        mBaseApiService.getMasaLayar(sharedPrefManager.getSPID())
                .enqueue(new Callback<MasaLayarModelList>() {
                    @Override
                    public void onResponse(Call<MasaLayarModelList> call, Response<MasaLayarModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generateMasaLayarList(response.body().getMasaLayarArrayList());
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

        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            uploadFile("masa_layar",requestCode,FileUtils.getPath(mContext,uri));
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

    private void generateMasaLayarList(ArrayList<MasaLayarModelRecycler> masaLayarArrayList) {

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_masalayar_list);
        masaLayarAdapter = new MasaLayarAdapter(masaLayarArrayList);

        masaLayarAdapter.onBindCallBack = (viewHolder, position) -> {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Pilih Image"), viewHolder.rowId);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
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
