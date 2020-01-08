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
import com.kapal.dokumenkapal.ui.kapal.KapalFormFragment;
import com.kapal.dokumenkapal.ui.menuprofiledata.MenuProfileDataFragment;
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

    Context mContext;
    BaseApiService mBaseApiService;
    SharedPrefManager sharedPrefManager;
    ProgressDialog loading;
    private RiwayatPelayaranAdapter riwayatPelayaranAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_listview_riwayat_pelayaran, container, false);
        swipe = root.findViewById(R.id.riwayatpelayaran_swipeContainer);


        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Data Riwayat Pelayaran");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.show();

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", 0);
                    bundle.putString("nama_kapal", "");
                    bundle.putString("tenaga_mesin", "");
                    bundle.putString("jabatan", "");
                    bundle.putString("tgl_naik", "");
                    bundle.putString("tgl_turun", "");

                    RiwayatPelayaranFormFragment fragment = new RiwayatPelayaranFormFragment();
                    fragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();

                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, fragment, RiwayatPelayaranFormFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();

                    // Toasty.error(mContext, "Ada kesalahan!", Toast.LENGTH_LONG, true).show();
                }
            });
        }

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                //swipe.setEnabled(false);
                loadData();
            }
        });

        loadData();


        return root;
    }

    private void loadData() {
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
                        Toasty.error(mContext, "Ada kesalahan!\n" + t.toString(), Toast.LENGTH_LONG, true).show();
                        loading.dismiss();
                    }
                });

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

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                MenuProfileDataFragment mf = new MenuProfileDataFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, MenuProfileDataFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }
}

