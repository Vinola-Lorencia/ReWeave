package com.example.reweave.testing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reweave.R;

import java.util.ArrayList;

public class PromoItemAdapter extends RecyclerView.Adapter<PromoItemAdapter.ViewHolder> {
    private final ArrayList<PromoItem> promoList;
    private final Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PromoItem promo);
    }

    public PromoItemAdapter(Context context, ArrayList<PromoItem> promoList) {
        this.context = context;
        this.promoList = promoList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_promo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PromoItem promo = promoList.get(position);
        
        holder.tvPromo.setText(promo.getTitle());
        holder.tvPoint.setText(promo.getPoints() + " Point");
        holder.ivPromo.setImageResource(promo.getImageResource());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(promo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return promoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPromo;
        TextView tvPromo;
        TextView tvPoint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPromo = itemView.findViewById(R.id.iv_promo);
            tvPromo = itemView.findViewById(R.id.tv_promo);
            tvPoint = itemView.findViewById(R.id.tv_point);
        }
    }
}
