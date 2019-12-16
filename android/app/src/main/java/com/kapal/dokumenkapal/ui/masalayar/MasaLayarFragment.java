package com.kapal.dokumenkapal.ui.masalayar;

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
import androidx.lifecycle.ViewModelProviders;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.permohonan.PermohonanFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MasaLayarFragment extends Fragment {

    private final static String TAG_FRAGMENT = "TAG_MASALAYAR_FRAGMENT";

    private MasaLayarViewModel masalayarViewModel;

    @BindView(R.id.cvRiwayatMasaLayar)
    CardView cvRiwayatMasaLayar;

    @BindView(R.id.cvTambahMasaLayar)
    CardView cvTambahMasaLayar;

    private Context mContext;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        masalayarViewModel =
                ViewModelProviders.of(this).get(MasaLayarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_permohonan_menu_masalayar, container, false);

        ButterKnife.bind(this, root);



//        final TextView textView = root.findViewById(R.id.text_permohonan);
//        masalayarViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            textView.setText(s);
//            }
//        });
        return root;
    }


    @OnClick(R.id.cvTambahMasaLayar)
    public void cvMasaLayarClicked() {

        MasaLayarFormFragment mf = new MasaLayarFormFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.nav_host_fragment, mf,TAG_FRAGMENT)
                .addToBackStack(null);
        ft.commit();
    }


    @Override
    //Pressed return button - returns to the results menu
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                //Toasty.info(mContext, "cvBongkarMuat Clicked", Toast.LENGTH_LONG, true).show();

                PermohonanFragment mf = new PermohonanFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf,TAG_FRAGMENT)
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }


}
