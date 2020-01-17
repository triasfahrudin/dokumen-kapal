package com.kapal.dokumenkapal.ui.bongkarmuat;

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
import com.kapal.dokumenkapal.ui.menupermohonan.MenuPermohonanFragment;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BongkarMuatFragment extends Fragment {

    private BongkarMuatAdapter bongkarMuatAdapter;
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

        View root = inflater.inflate(R.layout.fragment_listview_bongkarmuat, container, false);
        swipe = root.findViewById(R.id.bongkarmuat_swipeContainer);

        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Data Permohonan Bongkar Muat");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.show();

            floatingActionButton.setOnClickListener(view -> {

                Bundle bundle = new Bundle();
                bundle.putInt("id", 0);
                bundle.putString("kode_biaya", "bm_gas");
                bundle.putString("jenis_muatan", "");
                bundle.putDouble("bobot", 0);
                bundle.putString("nama_kapal", "");
                bundle.putString("angkutan_nopol", "");
                bundle.putString("angkutan_supir", "");

                BongkarMuatFormFragment fragment = new BongkarMuatFormFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, BongkarMuatFormFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            });
        }

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        swipe.setOnRefreshListener(() -> {
            swipe.setRefreshing(false);
            loadData();
        });

        loadData();


        return root;
    }

    private void loadData() {
        loading = ProgressDialog.show(mContext, null, "Mengambil data ...", true, false);

        mBaseApiService.getBongkarMuat(sharedPrefManager.getSPID())
                .enqueue(new Callback<BongkarMuatModelList>() {
                    @Override
                    public void onResponse(Call<BongkarMuatModelList> call, Response<BongkarMuatModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generateBongkarMuatList(Objects.requireNonNull(response.body()).getBongkarMuatArrayList());
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<BongkarMuatModelList> call, Throwable t) {
                        Toasty.error(mContext, "Ada kesalahan!\n" + t.toString(), Toast.LENGTH_LONG, true).show();
                        loading.dismiss();
                    }
                });
    }


    private void generateBongkarMuatList(ArrayList<BongkarMuatModelRecycler> bongkarMuatArrayList) {

        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recycler_view_bongkarmuat_list);
        bongkarMuatAdapter = new BongkarMuatAdapter(bongkarMuatArrayList);

        bongkarMuatAdapter.onBindCallBack = (jenis, viewHolder, position) -> {

            if ("upload_file".equals(jenis)) {
                Bundle bundle = new Bundle();
                bundle.putInt("recyclerId", viewHolder.rowId);
                bundle.putDouble("biaya", viewHolder.biaya);

                BongkarMuatFormBayarFragment fragment = new BongkarMuatFormBayarFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) getView().getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, BongkarMuatFormBayarFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            } else if ("give_rating".equals(jenis)) {

                Bundle bundle = new Bundle();
                bundle.putInt("id", viewHolder.rowId);
                bundle.putFloat("rating_kepuasan", viewHolder.rating_kepuasan);
                bundle.putString("komentar", viewHolder.komentar);

                BongkarMuatRatingFragment fragment = new BongkarMuatRatingFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) getView().getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, BongkarMuatRatingFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            } else if ("revisi_berkas".equals(jenis)) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", viewHolder.rowId);
                bundle.putString("kode_biaya", viewHolder.kode_biaya);
                bundle.putString("jenis_muatan", viewHolder.jenis_muatan);
                bundle.putDouble("bobot", viewHolder.bobot);
                bundle.putString("nama_kapal", viewHolder.nama_kapal);
                bundle.putString("angkutan_nopol", viewHolder.angkutan_nopol);
                bundle.putString("angkutan_supir", viewHolder.angkutan_supir);

                BongkarMuatFormFragment fragment = new BongkarMuatFormFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) getView().getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, BongkarMuatFormFragment.class.getSimpleName())
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
        recyclerView.setAdapter(bongkarMuatAdapter);

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
