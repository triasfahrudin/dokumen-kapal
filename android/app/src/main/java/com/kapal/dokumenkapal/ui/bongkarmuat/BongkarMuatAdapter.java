package com.kapal.dokumenkapal.ui.bongkarmuat;

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

public class BongkarMuatAdapter extends RecyclerView.Adapter<BongkarMuatAdapter.BongkarMuatViewHolder> {

    OnBindCallBack onBindCallBack;
    private Context context;
    private ArrayList<BongkarMuatModelRecycler> dataList;
    BongkarMuatAdapter(ArrayList<BongkarMuatModelRecycler> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public BongkarMuatAdapter.BongkarMuatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_bongkarmuat_list, parent, false);
        return new BongkarMuatAdapter.BongkarMuatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BongkarMuatViewHolder holder, int position) {
        if ("400".equals(dataList.get(position).getStatus())) {
            holder.tvKode.setText(String.format("Kode: PBM-%s [SELESAI]", dataList.get(position).getKode()));
            holder.tvKode.setTextColor(Color.GRAY);
            holder.tvKode.setPaintFlags(holder.tvKode.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (Arrays.asList("299", "399").contains(dataList.get(position).getStatus())) {
            holder.tvKode.setText(String.format("Kode: PBM-%s [REVISI]", dataList.get(position).getKode()));
            holder.tvKode.setTextColor(Color.RED);
        } else {
            holder.tvKode.setText(String.format("Kode: PBM-%s [PROSES]", dataList.get(position).getKode()));
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

        holder.rowId = dataList.get(position).getId();
        holder.kode_biaya = dataList.get(position).getKode_biaya();
        holder.jenis_muatan = dataList.get(position).getJenis_muatan();
        holder.bobot = dataList.get(position).getBobot();
        holder.nama_kapal = dataList.get(position).getNama_kapal();
        holder.jenis_kapal = dataList.get(position).getJenis_kapal();
        holder.gt_kapal = dataList.get(position).getGt_kapal();
        holder.agen_kapal = dataList.get(position).getAgen_kapal();

        holder.angkutan_nopol = dataList.get(position).getAngkutan_nopol();
        holder.angkutan_supir = dataList.get(position).getAngkutan_supir();

        holder.tgl_pelaksanaan = dataList.get(position).getTgl_pelaksanaan();

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

    class BongkarMuatViewHolder extends RecyclerView.ViewHolder {

        String kode_biaya;
        String jenis_muatan;
        Double bobot;
        String nama_kapal;
        String jenis_kapal;
        String gt_kapal;
        String agen_kapal;
        String angkutan_nopol;
        String angkutan_supir;
        String tgl_pelaksanaan;

        Double biaya;
        TextView tvKode;
        TextView tvTglMohon;
        TextView tvStatus;

        Button btnUpload;
        Button btnRating;
        Button btnRevisi;

        int rowId;
        float rating_kepuasan;
        String komentar;

        BongkarMuatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.rowBongkarmuat_tvKode);
            tvTglMohon = itemView.findViewById(R.id.rowBongkarmuat_tvTglMohon);
            tvStatus = itemView.findViewById(R.id.rowBongkarmuat_tvStatus);
            btnUpload = itemView.findViewById(R.id.rowBongkarmuat_btnUpload);
            btnRating = itemView.findViewById(R.id.rowBongkarmuat_btnRating);
            btnRevisi = itemView.findViewById(R.id.rowBongkarmuat_btnRevisiBerkas);
        }
    }
}
