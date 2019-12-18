package com.kapal.dokumenkapal.ui.kapal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kapal.dokumenkapal.R;

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
        holder.tvNama.setText(dataList.get(position).getNama());
        holder.tvJenis.setText(dataList.get(position).getJenis());
        holder.tvImoNumber.setText(dataList.get(position).getImo_number());
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
