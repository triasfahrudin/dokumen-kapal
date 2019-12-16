package com.kapal.dokumenkapal.ui.sertifikatkeselamatan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.kapal.dokumenkapal.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SertifikatKeselamatanFragment extends Fragment {

    private final static String TAG_FRAGMENT = "TAG_KESELAMATANKAPAL_FRAGMENT";

    private SertifikatKeselamatanViewModel sertifikatKeselamatanViewModel;

    @BindView(R.id.cvRiwayatSertifikatKeselamatan)
    CardView cvSertifikatKeselamatan;

    @BindView(R.id.cvTambahSertifikatKeselamatan)
    CardView cvTambahSertifikatKeselamatan;

    private Context mContext;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sertifikatKeselamatanViewModel =
                ViewModelProviders.of(this).get(SertifikatKeselamatanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_permohonan_menu_sertifikat, container, false);

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



}
