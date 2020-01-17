package com.kapal.dokumenkapal.ui.masalayar;

import com.kapal.dokumenkapal.ui.masalayar.MasaLayarAdapter.MasaLayarViewHolder;

public interface OnBindCallBack {

    void OnViewBind(String jenis, MasaLayarViewHolder viewHolder, int position);
}
