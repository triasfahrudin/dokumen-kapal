package com.kapal.dokumenkapal.ui.masalayar;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kapal.dokumenkapal.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MasaLayarFormFragment extends Fragment {

    private final static String TAG_FRAGMENT = "TAG_FORM_MASALAYAR_FRAGMENT";
    @BindView(R.id.masalayar_btnKirim)
    Button masalayarBtnKirim;


    private Context mContext;

    private String browseFor = "bukupelaut";

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }

    private static final String[][] DATA_TO_SHOW = { { "This", "is", "a", "test" },
            { "and", "a", "second", "test" } };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /*
        * nama, tgl_mohon,nomor_buku,kode_pelaut
        * ==> nama_sertifikat,nomor,penerbit
        *
        * */
        View root = inflater.inflate(R.layout.fragment_form_masalayar, container, false);

        ButterKnife.bind(this, root);


        return root;
    }


    @Override
    //Pressed return button - returns to the results menu
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                MasaLayarFragment mf = new MasaLayarFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, TAG_FRAGMENT)
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }

    @OnClick(R.id.masalayar_btnKirim)
    public void onViewClicked() {
    }
}
