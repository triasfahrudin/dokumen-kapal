package com.kapal.dokumenkapal.ui.profiledata;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.bukupelaut.BukuPelautFragment;
import com.kapal.dokumenkapal.ui.home.HomeFragment;
import com.kapal.dokumenkapal.ui.kapal.KapalFragment;
import com.kapal.dokumenkapal.ui.profile.ProfileFragment;
import com.kapal.dokumenkapal.ui.riwayatpelayaran.RiwayatPelayaranFragment;
import com.kapal.dokumenkapal.ui.sertifikatpelaut.SertifikatPelautFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuProfileDataFragment extends Fragment {

    private final static String TAG = MenuProfileDataFragment.class.getSimpleName();

    @BindView(R.id.profile_cvKapal)
    CardView cvKapal;
    @BindView(R.id.profile_cvBukuPelaut)
    CardView cvBukuPelaut;
    @BindView(R.id.profile_cvProfile)
    CardView cvProfile;
    @BindView(R.id.profile_cvRiwayatPelayaran)
    CardView cvRiwayatPelayaran;
    @BindView(R.id.profile_cvSertifikatPelaut)
    CardView cvSertifikatPelaut;


    private Context mContext;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_menu_profile_data, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @OnClick(R.id.profile_cvKapal)
    public void cvKapalClicked() {

        KapalFragment fragment = new KapalFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, "TAG_KAPAL_FRAGMENT")
                .addToBackStack(null);
        ft.commit();
    }

    @OnClick(R.id.profile_cvBukuPelaut)
    public void cvBukupelautClicked() {

        BukuPelautFragment fragment = new BukuPelautFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, BukuPelautFragment.class.getSimpleName())
                .addToBackStack(null);
        ft.commit();
    }


    //Pressed return button
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                HomeFragment mf = new HomeFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, "TAG_HOME_FRAGMENT")
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }


    @OnClick(R.id.profile_cvProfile)
    public void onCvProfileClicked() {

        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, ProfileFragment.class.getSimpleName())
                .addToBackStack(null);
        ft.commit();
    }

    @OnClick(R.id.profile_cvRiwayatPelayaran)
    public void onCvRiwayatPelayaranClicked() {

        RiwayatPelayaranFragment fragment = new RiwayatPelayaranFragment();
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, RiwayatPelayaranFragment.class.getSimpleName())
                .addToBackStack(null);
        ft.commit();

    }

    @OnClick(R.id.profile_cvSertifikatPelaut)
    public void onCvSertifikatPelautClicked() {

        SertifikatPelautFragment fragment = new SertifikatPelautFragment();
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, SertifikatPelautFragment.class.getSimpleName())
                .addToBackStack(null);
        ft.commit();

    }
}
