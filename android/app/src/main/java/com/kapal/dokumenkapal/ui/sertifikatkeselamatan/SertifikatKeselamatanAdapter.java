package com.kapal.dokumenkapal.ui.sertifikatkeselamatan;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import java.util.ArrayList;
import java.util.Arrays;

public class SertifikatKeselamatanAdapter extends RecyclerView.Adapter<SertifikatKeselamatanAdapter.SertifikatKeselamatanViewHolder> {

    OnBindCallBack onBindCallBack;
    private ArrayList<SertifikatKeselamatanModelRecycler> dataList;


    SertifikatKeselamatanAdapter(ArrayList<SertifikatKeselamatanModelRecycler> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SertifikatKeselamatanAdapter.SertifikatKeselamatanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_sertifikatkeselamatan_list, parent, false);
        return new SertifikatKeselamatanAdapter.SertifikatKeselamatanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SertifikatKeselamatanViewHolder holder, int position) {
        if ("400".equals(dataList.get(position).getStatus())) {
            holder.tvKode.setText(String.format("Kode: PS-%s [SELESAI]", dataList.get(position).getKode()));
            holder.tvKode.setTextColor(Color.GRAY);
            holder.tvKode.setPaintFlags(holder.tvKode.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (Arrays.asList("299", "399").contains(dataList.get(position).getStatus())) {
            holder.tvKode.setText(String.format("Kode: PS-%s [REVISI]", dataList.get(position).getKode()));
            holder.tvKode.setTextColor(Color.RED);
        } else {
            holder.tvKode.setText(String.format("Kode: PS-%s [PROSES]", dataList.get(position).getKode()));
            holder.tvKode.setTextColor(Color.BLUE);
        }

        holder.tvTglMohon.setText(
                String.format("Tanggal mohon: %s %n Update terakhir : %s",
                        dataList.get(position).getTgl_mohon(),
                        dataList.get(position).getTgl_update())
        );

        if ("299".equals(dataList.get(position).getStatus()) || "399".equals(dataList.get(position).getStatus())) {
            //jika ada kegagalan dalam validasi
            holder.tvStatus.setText(String.format("Status: %n%s [ %s ] %n%nAlasan:%n%s",
                    dataList.get(position).getArti_status(),
                    dataList.get(position).getStatus().toUpperCase(),
                    dataList.get(position).getAlasan_status()
            ));
            holder.tvStatus.setTextColor(Color.RED);
        } else {
            holder.tvStatus.setText(String.format("Status: %n%s [ %s ]",
                    dataList.get(position).getArti_status(),
                    dataList.get(position).getStatus().toUpperCase()
            ));
        }

        if(dataList.get(position).getRating_kepuasan() > 0.0){
            holder.btnRating.setText("Terimakasih atas penilaian anda");
        }

        holder.tvKapal.setText(String.format("Kapal: %s", dataList.get(position).getNama_kapal().toUpperCase()));
        holder.rowId = dataList.get(position).getId();
        holder.rating_kepuasan = (float) dataList.get(position).getRating_kepuasan();
        holder.komentar = dataList.get(position).getKomentar();
        holder.biaya = dataList.get(position).getBiaya();

        Context mContext = holder.itemView.getContext();

        BaseApiService mBaseApiService = UtilsApi.getAPIService();
        SharedPrefManager sharedPrefManager = new SharedPrefManager(mContext);

        if (Arrays.asList("210", "310", "399", "400").contains(dataList.get(position).getStatus())) {
            holder.btnUpload.setVisibility(View.GONE);
        }

        if ("400".equals(dataList.get(position).getStatus())) {
            holder.tvStatus.setTextColor(Color.GRAY);
            holder.btnRating.setVisibility(View.VISIBLE);
        }

        if ("399".equals(dataList.get(position).getStatus())) {
            holder.btnRevisi.setVisibility(View.VISIBLE);
        }


        holder.btnUpload.setOnClickListener(v -> {
            if (onBindCallBack != null) {
                onBindCallBack.OnViewBind("upload_file", holder, position);
            }
        });

        holder.btnRating.setOnClickListener(v -> {
            if (onBindCallBack != null) {
                onBindCallBack.OnViewBind("give_rating", holder, position);
            }
        });

        holder.btnRevisi.setOnClickListener(v -> {
            if (onBindCallBack != null) {
                onBindCallBack.OnViewBind("revisi_berkas", holder, position);
            }
        });


        holder.itemView.setOnClickListener(v -> {


        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class SertifikatKeselamatanViewHolder extends RecyclerView.ViewHolder {

        Double biaya;
        TextView tvKode;
        TextView tvTglMohon;
        TextView tvStatus;
        TextView tvKapal;

        Button btnUpload;
        Button btnRating;
        Button btnRevisi;

        int rowId;
        float rating_kepuasan;
        String komentar;

        SertifikatKeselamatanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.rowSertifikatkeselamatan_tvKode);
            tvTglMohon = itemView.findViewById(R.id.rowSertifikatkeselamatan_tvTglMohon);
            tvStatus = itemView.findViewById(R.id.rowSertifikatkeselamatan_tvStatus);
            tvKapal = itemView.findViewById(R.id.rowSertifikatkeselamatan_tvKapal);
            btnUpload = itemView.findViewById(R.id.rowSertifikatkeselamatan_btnUpload);
            btnRating = itemView.findViewById(R.id.rowSertifikatkeselamatan_btnRating);
            btnRevisi = itemView.findViewById(R.id.rowSertifikatkeselamatan_btnRevisiBerkas);
        }
    }
}
