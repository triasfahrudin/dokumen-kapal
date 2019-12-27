package com.kapal.dokumenkapal.ui.sertifikatkeselamatan;

import com.kapal.dokumenkapal.ui.sertifikatpelaut.SertifikatPelautAdapter;

public interface OnBindCallBack {
    void OnViewBind(String jenis,SertifikatKeselamatanAdapter.SertifikatKeselamatanViewHolder viewHolder, int position);
}
