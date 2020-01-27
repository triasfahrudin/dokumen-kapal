package com.kapal.dokumenkapal.ui.masalayar;

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

public class MasaLayarAdapter extends RecyclerView.Adapter<MasaLayarAdapter.MasaLayarViewHolder> {

    OnBindCallBack onBindCallBack;
    private ArrayList<MasaLayarModelRecycler> dataList;

    MasaLayarAdapter(ArrayList<MasaLayarModelRecycler> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MasaLayarAdapter.MasaLayarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_masalayar_list, parent, false);
        return new MasaLayarAdapter.MasaLayarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MasaLayarAdapter.MasaLayarViewHolder holder, int position) {

        if ("400".equals(dataList.get(position).getStatus())) {
            holder.tvKode.setText(String.format("Kode: PML-%s [SELESAI]", dataList.get(position).getKode()));
            holder.tvKode.setTextColor(Color.GRAY);
            holder.tvKode.setPaintFlags(holder.tvKode.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (Arrays.asList("299", "399").contains(dataList.get(position).getStatus())) {
            holder.tvKode.setText(String.format("Kode: PML-%s [REVISI]", dataList.get(position).getKode()));
            holder.tvKode.setTextColor(Color.RED);
        } else {
            holder.tvKode.setText(String.format("Kode: PML-%s [PROSES]", dataList.get(position).getKode()));
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MasaLayarViewHolder extends RecyclerView.ViewHolder {

        TextView tvKode;
        TextView tvTglMohon;
        TextView tvStatus;

        Button btnUpload;
        Button btnRating;
        Button btnRevisi;

        int rowId;
        float rating_kepuasan;

        String komentar;
        Double biaya;

        MasaLayarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.rowMasalayar_tvKode);
            tvTglMohon = itemView.findViewById(R.id.rowMasalayar_tvTglMohon);
            tvStatus = itemView.findViewById(R.id.rowMasalayar_tvStatus);
            btnUpload = itemView.findViewById(R.id.rowMasalayar_btnUpload);
            btnRating = itemView.findViewById(R.id.rowMasalayar_btnRating);
            btnRevisi = itemView.findViewById(R.id.rowMasalayar_btnRevisiBerkas);
        }
    }
}
