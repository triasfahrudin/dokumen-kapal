package com.kapal.dokumenkapal.ui.sertifikatpelaut;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.profile.MenuProfileDataFragment;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SertifikatPelautFragment extends Fragment {

    private SertifikatPelautAdapter sertifikatPelautAdapter;
    private RecyclerView recyclerView;

    Context mContext;
    BaseApiService mBaseApiService;
    SharedPrefManager sharedPrefManager;

    ProgressDialog loading;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_listview_riwayat_pelayaran, container, false);

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        loading = ProgressDialog.show(mContext, null, "Mengambil data ...", true, false);

        mBaseApiService.getSertifikatPelaut(sharedPrefManager.getSPID())
                .enqueue(new Callback<SertifikatPelautModelList>() {
                    @Override
                    public void onResponse(Call<SertifikatPelautModelList> call, Response<SertifikatPelautModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generateSertifikatPelautList(response.body().getSertifikatPelautArrayList());

                        } else {
                            loading.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<SertifikatPelautModelList> call, Throwable t) {
                        Toasty.error(mContext, "Ada kesalahan!", Toast.LENGTH_LONG, true).show();
                    }
                });

        return root;
    }

    private void generateSertifikatPelautList(ArrayList<SertifikatPelautModelRecycler> sertifikatPelautArrayList) {

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_riwayat_pelayaran_list);

        sertifikatPelautAdapter = new SertifikatPelautAdapter(sertifikatPelautArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sertifikatPelautAdapter);
    }

    @Override
    //Pressed return button
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                MenuProfileDataFragment mf = new MenuProfileDataFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf,MenuProfileDataFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }
}

