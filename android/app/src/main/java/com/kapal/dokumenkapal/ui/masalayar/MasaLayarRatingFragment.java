package com.kapal.dokumenkapal.ui.masalayar;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kapal.dokumenkapal.MainActivity;
import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasaLayarRatingFragment extends Fragment {

    @BindView(R.id.penilaian)
    RatingBar penilaian;
    @BindView(R.id.taKomentarRating)
    EditText taKomentarRating;
    @BindView(R.id.submit)
    Button submit;

    private Context mContext;
    private BaseApiService mBaseApiService;
    private ProgressDialog loading;
    private float rating;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_form_rating, container, false);
        ButterKnife.bind(this, root);

        mBaseApiService = UtilsApi.getAPIService();

        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Rating pelayanan");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toasty.error(mContext, "Ada kesalahan!", Toast.LENGTH_LONG, true).show();
                }
            });
        }

        rating = Objects.requireNonNull(getArguments()).getFloat("rating_kepuasan");
        penilaian.setRating(rating);
        taKomentarRating.setText(getArguments().getString("komentar"));

        return root;
    }

    private void updateRating() {
        loading = ProgressDialog.show(mContext, null, "Merubah data, Mohon tunggu...", true, false);
        mBaseApiService.updateRating(
                "masa_layar",
                getArguments().getInt("id"),
                penilaian.getRating(),
                taKomentarRating.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("error").equals("false")) {

                            Toast.makeText(mContext, "Terimakasih atas penilaian anda", Toast.LENGTH_SHORT).show();

                            MasaLayarFragment mf = new MasaLayarFragment();
                            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.nav_host_fragment, mf, MasaLayarFragment.class.getSimpleName())
                                    .addToBackStack(null);
                            ft.commit();

                        } else {
                            String error_message = jsonObject.getString("error_msg");
                            Toasty.error(mContext, error_message, Toast.LENGTH_SHORT).show();
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
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
            }
        });
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {

        if (rating > 0.0) {

            new AlertDialog.Builder(mContext)
                    .setTitle("Merubah penilaian")
                    .setMessage("Apakah anda ingin merubah penilaian anda?")
                    .setPositiveButton("UBAH", (dialog, which) -> {
                        updateRating();
                    }).setNegativeButton("Batal", null).show();


        } else {
            updateRating();
        }
    }

    @Override
    //Pressed return button
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                MasaLayarFragment mf = new MasaLayarFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, MasaLayarFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }

}
