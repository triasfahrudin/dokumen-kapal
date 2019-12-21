package com.kapal.dokumenkapal.ui.kapal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.sertifikatpelaut.SertifikatPelautFormFragment;

import java.util.ArrayList;

public class KapalAdapter extends RecyclerView.Adapter<KapalAdapter.KapalViewHolder> {

    private ArrayList<KapalModelRecycler> dataList;

    public KapalAdapter(ArrayList<KapalModelRecycler> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public KapalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_kapal_list, parent, false);
        return new KapalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KapalViewHolder holder, int position) {
        holder.tvNama.setText(String.format("Kapal: %s",dataList.get(position).getNama_kapal()));
        holder.tvJenis.setText(String.format("Jenis: %s", dataList.get(position).getJenis_kapal()));
        holder.tvImoNumber.setText(String.format("IMO Number: %s", dataList.get(position).getImo_number()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", dataList.get(position).getId());
                bundle.putString("nama_kapal", dataList.get(position).getNama_kapal());
                bundle.putString("jenis_kapal", dataList.get(position).getJenis_kapal());
                bundle.putString("imo_number", dataList.get(position).getImo_number());
                bundle.putInt("grt", dataList.get(position).getGrt());
                bundle.putInt("kapasitas_penumpang", dataList.get(position).getKapasitas_penumpang());
                bundle.putInt("kapasitas_roda_dua", dataList.get(position).getKapasitas_roda_dua());
                bundle.putInt("kapasitas_roda_empat", dataList.get(position).getKapasitas_roda_empat());

                KapalFormFragment fragment = new KapalFormFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,fragment,KapalFormFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class KapalViewHolder extends RecyclerView.ViewHolder{

        TextView tvNama,tvJenis,tvImoNumber;

        KapalViewHolder(View itemView){
            super(itemView);
            tvNama = (TextView) itemView.findViewById(R.id.rowkapal_tvNama);
            tvJenis = (TextView) itemView.findViewById(R.id.rowkapal_tvJenis);
            tvImoNumber = (TextView) itemView.findViewById(R.id.rowkapal_tvImoNumber);
        }
    }
}
