package com.kapal.dokumenkapal.ui.kapal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.masalayar.MasaLayarViewModel;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KapalFragment extends Fragment {

    private KapalAdapter kapalAdapter;
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

        View root = inflater.inflate(R.layout.fragment_listview_kapal, container, false);

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        loading = ProgressDialog.show(mContext, null, "Mengambil data ...", true, false);

        mBaseApiService.getKapal(sharedPrefManager.getSPID())
                .enqueue(new Callback<KapalModelList>() {
                    @Override
                    public void onResponse(Call<KapalModelList> call, Response<KapalModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generateKapalList(response.body().getKapalArrayList());

                        } else {
                            loading.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<KapalModelList> call, Throwable t) {
                        Toasty.error(mContext, "Ada kesalahan!", Toast.LENGTH_LONG, true).show();
                    }
                });

        return root;
    }

    private void generateKapalList(ArrayList<KapalModelRecycler> kapalArrayList) {

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_kapal_list);

        kapalAdapter = new KapalAdapter(kapalArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(kapalAdapter);
    }
}
