package com.kapal.dokumenkapal.ui.riwayatpelayaran;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kapal.dokumenkapal.R;

import java.util.ArrayList;

public class RiwayatPelayaranAdapter extends RecyclerView.Adapter<RiwayatPelayaranAdapter.RiwayatPelayaranViewHolder> {

    private ArrayList<RiwayatPelayaranModelRecycler> dataList;

    public RiwayatPelayaranAdapter(ArrayList<RiwayatPelayaranModelRecycler> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RiwayatPelayaranAdapter.RiwayatPelayaranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_riwayat_pelayaran_list, parent, false);
        return new RiwayatPelayaranAdapter.RiwayatPelayaranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatPelayaranAdapter.RiwayatPelayaranViewHolder holder, int position) {
        holder.tvNamaKapal.setText(dataList.get(position).getNamaKapal());
        holder.tvJabatan.setText(dataList.get(position).getJabatan());
        holder.tvTanggal.setText(dataList.get(position).getTanggal());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RiwayatPelayaranViewHolder extends RecyclerView.ViewHolder{

        TextView tvNamaKapal,tvJabatan,tvTanggal;

        RiwayatPelayaranViewHolder(View itemView){
            super(itemView);
            tvNamaKapal = (TextView) itemView.findViewById(R.id.rowRiwayatPelayaran_tvNamaKapal);
            tvJabatan = (TextView) itemView.findViewById(R.id.rowRiwayatPelayaran_tvJabatan);
            tvTanggal = (TextView) itemView.findViewById(R.id.rowRiwayatPelayaran_tvTanggal);
        }
    }
}