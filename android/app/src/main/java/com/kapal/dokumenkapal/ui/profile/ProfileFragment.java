package com.kapal.dokumenkapal.ui.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kapal.dokumenkapal.MainActivity;
import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.menuprofiledata.MenuProfileDataFragment;
import com.kapal.dokumenkapal.util.SetDate;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private static final int BUFFER_SIZE = 1024 * 2;
    @BindView(R.id.profile_etNamaLengkap)
    EditText etNamaLengkap;

    @BindView(R.id.profile_etEmail)
    EditText etEmail;

    @BindView(R.id.profile_etNoTelp)
    EditText etNoTelp;

    @BindView(R.id.profile_etAlamat)
    EditText etAlamat;

    @BindView(R.id.profile_btnUpdate)
    Button profileBtnUpdate;

    ProgressDialog loading;

    @BindView(R.id.profile_etNamaLengkapWrapper)
    TextInputLayout profileEtNamaLengkapWrapper;

    @BindView(R.id.profile_etTempatlahir)
    EditText etTempatlahir;

    @BindView(R.id.profile_etTempatlahirWrapper)
    TextInputLayout profileEtTempatlahirWrapper;

    @BindView(R.id.profile_etTanggallahir)
    EditText etTanggallahir;

    @BindView(R.id.profile_etTanggalLahirWrapper)
    TextInputLayout profileEtTanggalLahirWrapper;
    @BindView(R.id.profile_error_msg)
    TextView profileErrorMsg;
    @BindView(R.id.profile_foto_btnUpload)
    Button profileFotoBtnUpload;
    @BindView(R.id.profile_foto_etUpload)
    EditText profileFotoEtUpload;

    private Context mContext;
    private BaseApiService mBaseApiService;
    private SharedPrefManager sharedPrefManager;
    private ProfileViewModel profileViewModel;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }


    @OnClick(R.id.profile_foto_btnUpload)
    public void btnLoadFotoClicked() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri filepath = Objects.requireNonNull(data.getData());
            profileFotoEtUpload.setText(filepath.getPath());
            uploadFile(filepath);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadFile(Uri path) {
        String pdfname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        File file = new File(getRealPathFromURI(mContext,path));
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("filename", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), pdfname);

        loading = ProgressDialog.show(mContext, null, "Proses upload file, Mohon tunggu ...", true, false);

        mBaseApiService.uploadFile("profile", sharedPrefManager.getSPID(), fileToUpload, filename)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.e("", "Response returned by website is : " + response.code());

                        Toast.makeText(mContext, "Upload file berhasil", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Log.e("", "Response returned by website is : " + t.getMessage());
                    }
                });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_form_profile, container, false);

        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Profile ");

        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();

            floatingActionButton.setOnClickListener(
                    view -> Toasty.error(mContext, "Ada kesalahan!", Toast.LENGTH_LONG, true).show()
            );
        }

        ButterKnife.bind(this, root);
        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);


        if ("perusahaan".equals(sharedPrefManager.getSPJenis())) {
            profileEtNamaLengkapWrapper.setHint("Nama Perusahaan");
            profileEtTempatlahirWrapper.setVisibility(View.GONE);
            profileEtTanggalLahirWrapper.setVisibility(View.GONE);
        } else {
            SetDate tglLahir = new SetDate(etTanggallahir, mContext);
        }

        loading = ProgressDialog.show(mContext, null, "Mengambil data ...", true, false);
        mBaseApiService.getProfileRequest(sharedPrefManager.getSPID())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.getString("error").equals("false")) {

                                    etNamaLengkap.setText(jsonObject.getJSONObject("user").getString("nama"));
                                    etAlamat.setText(jsonObject.getJSONObject("user").getString("alamat"));
                                    etEmail.setText(jsonObject.getJSONObject("user").getString("email"));
                                    etNoTelp.setText(jsonObject.getJSONObject("user").getString("no_telp"));

                                    if ("perorangan".equals(sharedPrefManager.getSPJenis())) {
                                        etTempatlahir.setText(jsonObject.getJSONObject("user").getString("tempat_lahir"));
                                        etTanggallahir.setText(jsonObject.getJSONObject("user").getString("tanggal_lahir"));
                                    }

                                } else {
                                    String error_message = jsonObject.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
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

                    }
                });


        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    @Override
    //Pressed return button
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                MenuProfileDataFragment mf = new MenuProfileDataFragment();
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, MenuProfileDataFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }

    @OnClick(R.id.profile_btnUpdate)
    public void btnUpdateClicked() {

        profileErrorMsg.setVisibility(View.GONE);
        loading = ProgressDialog.show(mContext, null, "Update Profile, Mohon tunggu...", true, false);

        if ("perorangan".equals(sharedPrefManager.getSPJenis())) {
            mBaseApiService.updateProfileRequest(
                    sharedPrefManager.getSPID(),
                    etNamaLengkap.getText().toString(),
                    etEmail.getText().toString(),
                    etNoTelp.getText().toString(),
                    etTempatlahir.getText().toString(),
                    etTanggallahir.getText().toString(),
                    etAlamat.getText().toString()
            ).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        loading.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getString("error").equals("false")) {

                                Toast.makeText(mContext, "Profile berhasil diupdate", Toast.LENGTH_SHORT).show();

                            } else {
                                String error_message = jsonObject.getString("error_msg");
                                Toast.makeText(mContext, "Error!", Toast.LENGTH_SHORT).show();
                                profileErrorMsg.setVisibility(View.VISIBLE);
                                profileErrorMsg.setText(error_message);
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
        } else {
            mBaseApiService.updateProfileRequest(
                    sharedPrefManager.getSPID(),
                    etNamaLengkap.getText().toString(),
                    etEmail.getText().toString(),
                    etNoTelp.getText().toString(),
                    etAlamat.getText().toString()
            ).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        loading.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getString("error").equals("false")) {

                                Toast.makeText(mContext, "Profile berhasil diupdate", Toast.LENGTH_SHORT).show();

                            } else {
                                String error_message = jsonObject.getString("error_msg");
                                Toast.makeText(mContext, "Error!", Toast.LENGTH_SHORT).show();
                                profileErrorMsg.setVisibility(View.VISIBLE);
                                profileErrorMsg.setText(error_message);
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


    }
}