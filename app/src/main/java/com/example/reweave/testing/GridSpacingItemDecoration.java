package com.example.reweave.testing;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Kelas untuk mengatur jarak antar item dalam tampilan grid RecyclerView
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    // Jumlah kolom dalam tampilan grid
    private int spanCount;
    // Ukuran jarak antar item dalam piksel
    private int spacing;
    // Menentukan apakah jarak diterapkan pada tepi luar grid
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // Mendapatkan posisi item dalam grid
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        if (includeEdge) {
            // Perhitungan jarak untuk grid dengan tepi luar
            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;

            // Menambahkan jarak atas untuk item pada baris pertama
            if (position < spanCount) {
                outRect.top = spacing;
            }
            outRect.bottom = spacing;
        } else {
            // Perhitungan jarak untuk grid tanpa tepi luar
            outRect.left = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing / spanCount;
            // Menambahkan jarak atas untuk item setelah baris pertama
            if (position >= spanCount) {
                outRect.top = spacing;
            }
        }
    }
} 