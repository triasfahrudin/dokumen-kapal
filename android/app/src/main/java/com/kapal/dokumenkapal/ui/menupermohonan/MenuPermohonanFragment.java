package com.kapal.dokumenkapal.ui.menupermohonan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.bongkarmuat.BongkarMuatFragment;
import com.kapal.dokumenkapal.ui.masalayar.MasaLayarFragment;
import com.kapal.dokumenkapal.ui.sertifikatkeselamatan.SertifikatKeselamatanFragment;
import com.kapal.dokumenkapal.ui.sertifikatpelaut.SertifikatPelautFragment;
import com.kapal.dokumenkapal.util.SharedPrefManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuPermohonanFragment extends Fragment {

    private final static String TAG_FRAGMENT = "TAG_PERMOHONAN_FRAGMENT";
    @BindView(R.id.permohonan_linierlayout1)
    LinearLayout permohonanLinierlayout1;
    @BindView(R.id.permohonan_linierlayout2)
    LinearLayout permohonanLinierlayout2;

    private MenuPermohonanViewModel menuPermohonanViewModel;

    @BindView(R.id.cvMasaLayar)
    CardView cvMasaLayar;
    @BindView(R.id.cvSertifikatKeselamatan)
    CardView cvSertifikat;
    @BindView(R.id.cvBongkarMuat)
    CardView cvBongkarMuat;

    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        mContext = context;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuPermohonanViewModel =
                ViewModelProviders.of(this).get(MenuPermohonanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu_permohonan, container, false);

        ButterKnife.bind(this, root);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(mContext);
        String jenisPemohon = sharedPrefManager.getSPJenis();

        if ("perorangan".equals(jenisPemohon)) {
            permohonanLinierlayout2.setVisibility(View.GONE);
        } else {
            permohonanLinierlayout1.setVisibility(View.GONE);
        }

        menuPermohonanViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }


    @OnClick(R.id.cvMasaLayar)
    void cvMasaLayarClicked() {
        MasaLayarFragment mf = new MasaLayarFragment();
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, mf, MasaLayarFragment.class.getSimpleName())
                .addToBackStack(null);
        ft.commit();
    }

    @OnClick(R.id.cvSertifikatKeselamatan)
    void cvSertifikatClicked() {
        SertifikatKeselamatanFragment kk = new SertifikatKeselamatanFragment();
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, kk, SertifikatPelautFragment.class.getSimpleName())
                .addToBackStack(null);
        ft.commit();

    }


    @OnClick(R.id.cvBongkarMuat)
    void cvBongkarMuatClicked() {
        BongkarMuatFragment mf = new BongkarMuatFragment();
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, mf, BongkarMuatFragment.class.getSimpleName())
                .addToBackStack(null);
        ft.commit();
    }
}