package com.kapal.dokumenkapal.ui.signout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.kapal.dokumenkapal.LoginActivity;
import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.util.SharedPrefManager;

public class SignOutFragment extends Fragment {

    private SendViewModel sendViewModel;
    Context mContext;

    SharedPrefManager sharedPrefManager;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.menu_signout, container, false);

        sharedPrefManager = new SharedPrefManager(mContext);

        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);


//        final TextView textView = root.findViewById(R.id.text_send);
//        sendViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}