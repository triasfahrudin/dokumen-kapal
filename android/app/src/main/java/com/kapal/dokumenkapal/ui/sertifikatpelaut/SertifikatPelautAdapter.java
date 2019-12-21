package com.kapal.dokumenkapal.ui.sertifikatpelaut;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kapal.dokumenkapal.R;
import com.kapal.dokumenkapal.ui.bukupelaut.BukuPelautFragment;
import com.kapal.dokumenkapal.ui.profile.ProfileFragment;

import java.util.ArrayList;

public class SertifikatPelautAdapter extends RecyclerView.Adapter<SertifikatPelautAdapter.SertifikatPelautViewHolder> {

    private ArrayList<SertifikatPelautModelRecycler> dataList;

    public SertifikatPelautAdapter(ArrayList<SertifikatPelautModelRecycler> dataList) {
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
        holder.tvPenerbit.setText("Penerbit : " + dataList.get(position).getPenerbit());
        holder.tvTanggal.setText("Masa Berlaku : " + dataList.get(position).getTglTerbit() + " s/d " + dataList.get(position).getTglBerakhir());

        /*

        @SerializedName("id")
        private int id;
        @SerializedName("nama_sertifikat")
        private String namaSertifikat;
        @SerializedName("nomor")
        private String nomor;
        @SerializedName("penerbit")
        private String penerbit;
        @SerializedName("tglTerbit")
        private String tglTerbit;
        @SerializedName("tglBerakhir")
        private String tglBerakhir;

    */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(),BukuPelautFragment.class);
//                intent.putExtra("id",dataList.get(position).getId());
//                intent.putExtra("nama_sertifikat",dataList.get(position).getNamaSertifikat());
//                intent.putExtra("nomor",dataList.get(position).getNomor());
//                intent.putExtra("penerbit",dataList.get(position).getPenerbit());
//                intent.putExtra("tgl_terbit",dataList.get(position).getTglTerbit());
//                intent.putExtra("tgl_berakhir",dataList.get(position).getTglBerakhir());

                Bundle bundle = new Bundle();
                bundle.putInt("id", dataList.get(position).getId());
                bundle.putString("nama_sertifikat", dataList.get(position).getNamaSertifikat());
                bundle.putString("nomor", dataList.get(position).getNomor());
                bundle.putString("penerbit", dataList.get(position).getPenerbit());
                bundle.putString("tgl_terbit", dataList.get(position).getTglTerbit());
                bundle.putString("tgl_berakhir", dataList.get(position).getTglBerakhir());

//                Context context = view.getContext();
//                context.startActivity(intent);

                ProfileFragment fragment = new ProfileFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,fragment)
                        .addToBackStack(null)
                        .commit();


//                FragmentTransaction ft =  getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.nav_host_fragment, fragment, BukuPelautFragment.class.getSimpleName())
//                        .addToBackStack(null);
//                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class SertifikatPelautViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaSertifikat, tvPenerbit, tvTanggal;

        SertifikatPelautViewHolder(View itemView) {
            super(itemView);
            tvNamaSertifikat = (TextView) itemView.findViewById(R.id.rowSertifikatPelaut_tvNamaSertifikat);
            tvPenerbit = (TextView) itemView.findViewById(R.id.rowSertifikatPelaut_tvPenerbit);
            tvTanggal = (TextView) itemView.findViewById(R.id.rowSertifikatPelaut_tvTanggal);
        }
    }
}
