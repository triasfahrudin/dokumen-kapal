package com.kapal.dokumenkapal.ui.bongkarmuat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.util.SharedPrefManager;
import com.kapal.dokumenkapal.util.api.BaseApiService;
import com.kapal.dokumenkapal.util.api.UtilsApi;


import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class BongkarMuatAdapter extends RecyclerView.Adapter<BongkarMuatAdapter.BongkarMuatViewHolder> {

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

    OnBindCallBack onBindCallBack;

    @Override
    public void onBindViewHolder(@NonNull BongkarMuatViewHolder holder, int position) {
        holder.tvKode.setText(String.format("Kode: PBM-%s", dataList.get(position).getKode()));
        holder.tvTglMohon.setText(
                String.format("Tanggal mohon: %s %n Update terakhir : %s",
                        dataList.get(position).getTgl_mohon(),
                        dataList.get(position).getTgl_update())
        );

        holder.tvStatus.setText(String.format("Status: %s", dataList.get(position).getStatus().toUpperCase()));
        holder.rowId = dataList.get(position).getId();
        holder.rating_kepuasan = (float) dataList.get(position).getRating_kepuasan();
        holder.komentar = dataList.get(position).getKomentar();

        Context mContext = holder.itemView.getContext();

        BaseApiService mBaseApiService = UtilsApi.getAPIService();
        SharedPrefManager sharedPrefManager = new SharedPrefManager(mContext);

        if ("diambil".equals(dataList.get(position).getStatus())) {
            holder.tvStatus.setText("Status: Berkas sudah diambil");
            holder.tvStatus.setTextColor(Color.GRAY);
            holder.btnUpload.setVisibility(View.GONE);
            //holder.btnUpload.setEnabled(false);
            //holder.btnUpload.setBackground(ContextCompat.getDrawable(mContext,R.drawable.navigation_item_background_default));
        }

        if ("ditolak".equals(dataList.get(position).getStatus())) {
            holder.tvStatus.setText("Status: Berkas ditolak (klik untuk detail)");
            holder.tvStatus.setTextColor(Color.RED);
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

        holder.itemView.setOnClickListener(v -> {


        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class BongkarMuatViewHolder extends RecyclerView.ViewHolder {

        TextView tvKode, tvTglMohon, tvStatus;
        Button btnUpload, btnRating;
        int rowId;
        float rating_kepuasan;
        String komentar;
        BongkarMuatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = (TextView) itemView.findViewById(R.id.rowBongkarmuat_tvKode);
            tvTglMohon = (TextView) itemView.findViewById(R.id.rowBongkarmuat_tvTglMohon);
            tvStatus = (TextView) itemView.findViewById(R.id.rowBongkarmuat_tvStatus);
            btnUpload = (Button) itemView.findViewById(R.id.rowBongkarmuat_btnUpload);
            btnRating = (Button) itemView.findViewById(R.id.rowBongkarmuat_btnRating);
            //rowId = 0;
        }
    }
}
