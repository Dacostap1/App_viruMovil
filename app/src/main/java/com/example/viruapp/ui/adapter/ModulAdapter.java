package com.example.viruapp.ui.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viruapp.Model.Modul;

import com.example.viruapp.R;


import java.util.ArrayList;

public class ModulAdapter extends RecyclerView.Adapter<ModulAdapter.ViewHolder> {

    private ArrayList<Modul> mDataSet;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_name;
        public TextView txt_solicitud;
        public TextView txt_memo;


        public ViewHolder(View v) {

            super(v);
            txt_name = v.findViewById(R.id.txt_name);
            txt_solicitud = v.findViewById(R.id.txt_solicitud);
            txt_memo = v.findViewById(R.id.txt_memo);

        }
    }

    public ModulAdapter(Context context) {
        this.context = context;
        mDataSet = new ArrayList<>(); //Inicializamos un ArrayList vacio para que no falle al no encontrar elementos al inicio
    }

    public void setDataSet(ArrayList<Modul> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged(); //Para indicar que hay cambios
    }

    // El layout manager invoca este método
    @Override
    public ModulAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // Creamos una nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.modul_view, parent, false);

        return new ViewHolder(v);
    }

    // Este método reemplaza el contenido de cada view,
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - obtenemos un elemento del dataset según su posición
        final Modul modul = mDataSet.get(position);

        holder.txt_name.setText(modul.getName());
        holder.txt_solicitud.setText(modul.getSolicitud());
        holder.txt_memo.setText(modul.getMemorandum());
    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo (por ejemplo si implementamos filtros o búsquedas)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}