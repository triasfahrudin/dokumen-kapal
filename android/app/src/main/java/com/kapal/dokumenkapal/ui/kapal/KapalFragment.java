package com.kapal.dokumenkapal.ui.kapal;

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
import com.kapal.dokumenkapal.ui.masalayar.MasaLayarFragment;
import com.kapal.dokumenkapal.ui.menuprofiledata.MenuProfileDataFragment;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KapalFragment extends Fragment {

    private KapalAdapter kapalAdapter;
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

        View root = inflater.inflate(R.layout.fragment_listview_kapal, container, false);
        swipe = root.findViewById(R.id.kapal_swipeContainer);

        Toolbar toolbar = (Toolbar) Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Data Kapal");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.show();

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", 0);
                    bundle.putString("nama_kapal", "");
                    bundle.putString("jenis_kapal", "");
                    bundle.putString("imo_number", "");
                    bundle.putInt("grt", 0);
                    bundle.putInt("kapasitas_penumpang", 0);
                    bundle.putInt("kapasitas_roda_dua", 0);
                    bundle.putInt("kapasitas_roda_empat", 0);

                    KapalFormFragment fragment = new KapalFormFragment();
                    fragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();

                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, fragment, KapalFormFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }
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

        mBaseApiService.getKapal(sharedPrefManager.getSPID())
                .enqueue(new Callback<KapalModelList>() {
                    @Override
                    public void onResponse(@NonNull Call<KapalModelList> call, @NonNull Response<KapalModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generateKapalList(Objects.requireNonNull(response.body()).getKapalArrayList());

                        } else {
                            loading.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<KapalModelList> call, Throwable t) {
                        Toasty.error(mContext, String.format("Ada kesalahan! %s", t.getMessage()), Toast.LENGTH_LONG, true).show();
                        loading.dismiss();
                    }
                });
    }

    private void generateKapalList(ArrayList<KapalModelRecycler> kapalArrayList) {

        recyclerView = getView().findViewById(R.id.recycler_view_kapal_list);
        kapalAdapter = new KapalAdapter(kapalArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(kapalAdapter);
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
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
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
