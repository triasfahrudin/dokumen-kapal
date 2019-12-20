package com.kapal.dokumenkapal.ui.riwayatpelayaran;

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
import com.kapal.dokumenkapal.ui.profiledata.MenuProfileDataFragment;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPelayaranFragment extends Fragment {

    private RiwayatPelayaranAdapter riwayatPelayaranAdapter;
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

        mBaseApiService.getRiwayatPelayaran(sharedPrefManager.getSPID())
                .enqueue(new Callback<RiwayatPelayaranModelList>() {
                    @Override
                    public void onResponse(Call<RiwayatPelayaranModelList> call, Response<RiwayatPelayaranModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generateRiwayatPelayaranList(response.body().getRiwayatPelayaranArrayList());

                        } else {
                            loading.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<RiwayatPelayaranModelList> call, Throwable t) {
                        Toasty.error(mContext, "Ada kesalahan!", Toast.LENGTH_LONG, true).show();
                    }
                });

        return root;
    }

    private void generateRiwayatPelayaranList(ArrayList<RiwayatPelayaranModelRecycler> riwayatPelayaranArrayList) {

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_riwayat_pelayaran_list);

        riwayatPelayaranAdapter = new RiwayatPelayaranAdapter(riwayatPelayaranArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(riwayatPelayaranAdapter);
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

