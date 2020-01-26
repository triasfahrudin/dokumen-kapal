package com.kapal.dokumenkapal;

import android.app.Activity;
import android.os.Bundle;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Notification extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("msg")) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Notifikasi")
                    .setContentText(getIntent().getExtras().getString("msg"))
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            finish();
                        }
                    })
                    .show();
        }

    }
}
