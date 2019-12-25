package com.kapal.dokumenkapal.ui.masalayar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class MasaLayarAdapter extends RecyclerView.Adapter<MasaLayarAdapter.MasaLayarViewHolder> {

    ProgressDialog loading;

    private Context mContext;
    private BaseApiService mBaseApiService;
    private SharedPrefManager sharedPrefManager;

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

    OnBindCallBack onBindCallBack;

    @Override
    public void onBindViewHolder(@NonNull MasaLayarAdapter.MasaLayarViewHolder holder, int position) {
        holder.tvKode.setText(String.format("Kode: PML-%s", dataList.get(position).getKode()));
        holder.tvTglMohon.setText(
                String.format("Tanggal mohon: %s %n Update terakhir : %s",
                        dataList.get(position).getTgl_mohon(),
                        dataList.get(position).getTgl_update())
        );

        holder.tvStatus.setText(String.format("Status: %s", dataList.get(position).getStatus().toUpperCase()));
        holder.rowId = dataList.get(position).getId();

        this.mContext = holder.itemView.getContext();

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

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



        holder.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBindCallBack != null) {
                    onBindCallBack.OnViewBind(holder, position);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MasaLayarViewHolder extends RecyclerView.ViewHolder {

        TextView tvKode, tvTglMohon, tvStatus;
        Button btnUpload;
        int rowId;

        public MasaLayarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = (TextView) itemView.findViewById(R.id.rowMasalayar_tvKode);
            tvTglMohon = (TextView) itemView.findViewById(R.id.rowMasalayar_tvTglMohon);
            tvStatus = (TextView) itemView.findViewById(R.id.rowMasalayar_tvStatus);
            btnUpload = (Button) itemView.findViewById(R.id.rowMasalayar_btnUpload);
            rowId = 0;
        }
    }
}
