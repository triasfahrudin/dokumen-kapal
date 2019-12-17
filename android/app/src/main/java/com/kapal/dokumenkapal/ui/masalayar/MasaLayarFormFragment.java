package com.kapal.dokumenkapal.ui.masalayar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.kapal.dokumenkapal.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class MasaLayarFormFragment extends Fragment {

    private final static String TAG_FRAGMENT = "TAG_FORM_MASALAYAR_FRAGMENT";

    @BindView(R.id.etFormName)
    EditText etFormName;

    @BindView(R.id.etFormNomorBukuPelaut)
    EditText etFormNomorBukuPelaut;

    @BindView(R.id.etFormUploadBukuPelaut)
    EditText etFormUploadBukuPelaut;

    @BindView(R.id.btnFormUploadBukuPelaut)
    Button btnFormUploadBukuPelaut;


    @BindView(R.id.etFormNomorIjazah)
    EditText etFormNomorIjazah;

    @BindView(R.id.etFormUploadIjazah)
    EditText etFormUploadIjazah;

    @BindView(R.id.btnFormUploadIjazah)
    Button btnFormUploadIjazah;


    private Context mContext;

    private String browseFor = "bukupelaut";

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ViewModelProviders.of(this).get(MasaLayarFormViewModel.class);
        View root = inflater.inflate(R.layout.fragment_form_masalayar, container, false);

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

    @OnClick(R.id.btnFormUploadIjazah)
    public void btnFormUploadIjazahClicked(){

        this.browseFor = "ijazah";

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, 1);
    }


    @OnClick(R.id.btnFormUploadBukuPelaut)
    public void btnFormUploadBukuPelautClicked(){

        this.browseFor = "bukupelaut";

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if("bukupelaut".equals(this.browseFor)){
                etFormUploadBukuPelaut.setText(data.getData().getPath());
            }else{
                etFormUploadIjazah.setText(data.getData().getPath());
            }

        }

        super.onActivityResult(requestCode, resultCode, data);

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

                MasaLayarFragment mf = new MasaLayarFragment();
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
