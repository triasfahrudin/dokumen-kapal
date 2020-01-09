package com.kapal.dokumenkapal.ui.riwayatpelayaran;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.kapal.KapalFormFragment;

import java.util.ArrayList;

public class RiwayatPelayaranAdapter extends RecyclerView.Adapter<RiwayatPelayaranAdapter.RiwayatPelayaranViewHolder> {

    private ArrayList<RiwayatPelayaranModelRecycler> dataList;

    public RiwayatPelayaranAdapter(ArrayList<RiwayatPelayaranModelRecycler> dataList) {
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
        holder.tvNamaKapal.setText(dataList.get(position).getNama_kapal());
        holder.tvJabatan.setText(dataList.get(position).getJabatan());
        holder.tvTanggal.setText(String.format("Masa berlayar : %s s/d %s", dataList.get(position).getTgl_naik(), dataList.get(position).getTgl_turun()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", dataList.get(position).getId());
                bundle.putString("nama_kapal", dataList.get(position).getNama_kapal());
                bundle.putString("tenaga_mesin", dataList.get(position).getTenaga_mesin());
                bundle.putString("jabatan", dataList.get(position).getJabatan());
                bundle.putString("tgl_naik", dataList.get(position).getTgl_naik());
                bundle.putString("tgl_turun", dataList.get(position).getTgl_turun());

                RiwayatPelayaranFormFragment fragment = new RiwayatPelayaranFormFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, KapalFormFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RiwayatPelayaranViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaKapal, tvJabatan, tvTanggal;

        RiwayatPelayaranViewHolder(View itemView) {
            super(itemView);
            tvNamaKapal = itemView.findViewById(R.id.rowRiwayatPelayaran_tvNamaKapal);
            tvJabatan = itemView.findViewById(R.id.rowRiwayatPelayaran_tvJabatan);
            tvTanggal = itemView.findViewById(R.id.rowRiwayatPelayaran_tvTanggal);
        }
    }
}