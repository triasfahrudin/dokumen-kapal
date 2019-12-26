package com.kapal.dokumenkapal.ui.sertifikatkeselamatan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;

import java.util.ArrayList;

public class SertifikatKeselamatanAdapter extends RecyclerView.Adapter<SertifikatKeselamatanAdapter.SertifikatKeselamatanViewHolder> {

    private Context context;
    private ArrayList<SertifikatKeselamatanModelRecycler> dataList;

    SertifikatKeselamatanAdapter(ArrayList<SertifikatKeselamatanModelRecycler> dataList){
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public SertifikatKeselamatanAdapter.SertifikatKeselamatanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_sertifikatkeselamatan_list, parent, false);
        return new SertifikatKeselamatanAdapter.SertifikatKeselamatanViewHolder(view);
    }

    OnBindCallBack onBindCallBack;

    @Override
    public void onBindViewHolder(@NonNull SertifikatKeselamatanViewHolder holder, int position) {
        holder.tvKode.setText(String.format("Kode: PBM-%s", dataList.get(position).getKode()));
        holder.tvTglMohon.setText(
                String.format("Tanggal mohon: %s %n Update terakhir : %s",
                        dataList.get(position).getTgl_mohon(),
                        dataList.get(position).getTgl_update())
        );

        holder.tvStatus.setText(String.format("Status: %s", dataList.get(position).getStatus().toUpperCase()));
        holder.tvKapal.setText(String.format("Kapal: %s",dataList.get(position).getNama_kapal().toUpperCase()));
        holder.rowId = dataList.get(position).getId();

        Context mContext = holder.itemView.getContext();

        BaseApiService mBaseApiService = UtilsApi.getAPIService();
        SharedPrefManager sharedPrefManager = new SharedPrefManager(mContext);

        if("diambil".equals(dataList.get(position).getStatus())){
            holder.tvStatus.setText("Status: Berkas sudah diambil");
            holder.tvStatus.setTextColor(Color.GRAY);
            holder.btnUpload.setEnabled(false);
            holder.btnUpload.setBackground(ContextCompat.getDrawable(mContext,R.drawable.navigation_item_background_default));
        }

        if("ditolak".equals(dataList.get(position).getStatus())){
            holder.tvStatus.setText("Status: Berkas ditolak (klik untuk detail)");
            holder.tvStatus.setTextColor(Color.RED);
        }



        holder.btnUpload.setOnClickListener(v -> {
            if (onBindCallBack != null) {
                onBindCallBack.OnViewBind(holder, position);
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

        TextView tvKode, tvTglMohon, tvStatus,tvKapal;
        Button btnUpload;
        int rowId;

        SertifikatKeselamatanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = (TextView) itemView.findViewById(R.id.rowSertifikatkeselamatan_tvKode);
            tvTglMohon = (TextView) itemView.findViewById(R.id.rowSertifikatkeselamatan_tvTglMohon);
            tvStatus = (TextView) itemView.findViewById(R.id.rowSertifikatkeselamatan_tvStatus);
            tvKapal = (TextView) itemView.findViewById(R.id.rowSertifikatkeselamatan_tvKapal);
            btnUpload = (Button) itemView.findViewById(R.id.rowSertifikatkeselamatan_btnUpload);
            rowId = 0;
        }
    }
}
