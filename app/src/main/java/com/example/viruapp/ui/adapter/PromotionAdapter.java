package com.example.viruapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viruapp.Model.Promotion;
import com.example.viruapp.R;
import com.example.viruapp.ui.activity.StudentActivity;

import java.util.ArrayList;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {

    private ArrayList<Promotion> mDataSet;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_name;
        public ImageView imageView;

        public ViewHolder(View v) {

            super(v);
            txt_name = v.findViewById(R.id.tv_promoName);
            imageView = v.findViewById(R.id.imgPromo);
        }
    }

    public PromotionAdapter(Context context) {
        this.context = context;
        mDataSet = new ArrayList<>(); //Inicializamos un ArrayList vacio para que no falle al no encontrar elementos al inicio
    }

    public void setDataSet(ArrayList<Promotion> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged(); //Para indicar que hay cambios
    }

    // El layout manager invoca este método
    @Override
    public PromotionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // Creamos una nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promotion_view, parent, false);

        return new ViewHolder(v);
    }

    // Este método reemplaza el contenido de cada view,
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - obtenemos un elemento del dataset según su posición
        final Promotion promotion = mDataSet.get(position);

        holder.txt_name.setText(promotion.getName());
        holder.imageView.setOnClickListener(v -> {
                Intent intent = new Intent(context, StudentActivity.class);
                intent.putExtra("promo_id", promotion.getId());
                context.startActivity(intent);
        });
    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo (por ejemplo si implementamos filtros o búsquedas)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}