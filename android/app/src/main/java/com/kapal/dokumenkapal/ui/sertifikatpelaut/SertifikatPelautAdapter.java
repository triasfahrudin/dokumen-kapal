package com.kapal.dokumenkapal.ui.sertifikatpelaut;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kapal.dokumenkapal.R;

import java.util.ArrayList;

public class SertifikatPelautAdapter extends RecyclerView.Adapter<SertifikatPelautAdapter.SertifikatPelautViewHolder> {

    private ArrayList<SertifikatPelautModelRecycler> dataList;

    public SertifikatPelautAdapter(ArrayList<SertifikatPelautModelRecycler> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SertifikatPelautAdapter.SertifikatPelautViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_sertifikat_pelaut_list, parent, false);
        return new SertifikatPelautAdapter.SertifikatPelautViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SertifikatPelautAdapter.SertifikatPelautViewHolder holder, int position) {
        holder.tvNamaSertifikat.setText(dataList.get(position).getNamaSertifikat());
        holder.tvPenerbit.setText(dataList.get(position).getPenerbit());
        holder.tvTanggal.setText(dataList.get(position).getTanggal());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class SertifikatPelautViewHolder extends RecyclerView.ViewHolder{

        TextView tvNamaSertifikat,tvPenerbit,tvTanggal;

        SertifikatPelautViewHolder(View itemView){
            super(itemView);
            tvNamaSertifikat = (TextView) itemView.findViewById(R.id.rowSertifikatPelaut_tvNamaSertifikat);
            tvPenerbit = (TextView) itemView.findViewById(R.id.rowSertifikatPelaut_tvPenerbit);
            tvTanggal = (TextView) itemView.findViewById(R.id.rowSertifikatPelaut_tvTanggal);
        }
    }
}
