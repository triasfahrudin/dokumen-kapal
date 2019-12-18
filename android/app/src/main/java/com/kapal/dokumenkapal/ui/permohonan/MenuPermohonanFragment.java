package com.kapal.dokumenkapal.ui.permohonan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.sertifikatkeselamatan.SertifikatKeselamatanFragment;
import com.kapal.dokumenkapal.ui.masalayar.MasaLayarFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class MenuPermohonanFragment extends Fragment {

    private final static String TAG_FRAGMENT = "TAG_PERMOHONAN_FRAGMENT";

    private MenuPermohonanViewModel menuPermohonanViewModel;

    @BindView(R.id.cvMasaLayar)
    CardView cvMasaLayar;


    @BindView(R.id.cvSertifikatKeselamatan)
    CardView cvSertifikat;

    @BindView(R.id.cvBongkarMuat)
    CardView cvBongkarMuat;


    private Context mContext;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuPermohonanViewModel =
                ViewModelProviders.of(this).get(MenuPermohonanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu_permohonan, container, false);

        ButterKnife.bind(this, root);


//        final TextView textView = root.findViewById(R.id.text_permohonan);
        menuPermohonanViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

//                textView.setText(s);
            }
        });
        return root;
    }



    @OnClick(R.id.cvMasaLayar)
    public void cvMasaLayarClicked() {
        //Toast.makeText(mContext, "cvMasaLayar Clicked", Toast.LENGTH_SHORT).show();

        MasaLayarFragment mf = new MasaLayarFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, mf,"TAG_MASALAYAR_FRAGMENT")
                .addToBackStack(null);
        ft.commit();

//        ProfileFragment profileFragment = new ProfileFragment();
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.nav_host_fragment,profileFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

    @OnClick(R.id.cvSertifikatKeselamatan)
    public void cvSertifikatClicked() {
//        Toast.makeText(mContext, "cvSertifikat Clicked", Toast.LENGTH_SHORT).show();
        SertifikatKeselamatanFragment kk = new SertifikatKeselamatanFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, kk,TAG_FRAGMENT)
                .addToBackStack(null);
        ft.commit();

    }


    @OnClick(R.id.cvBongkarMuat)
    public void cvBongkarMuatClicked() {
        Toasty.info(mContext, "cvBongkarMuat Clicked", Toast.LENGTH_LONG, true).show();
    }
}