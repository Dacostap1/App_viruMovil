package com.example.viruapp.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viruapp.Model.Modul;
import com.example.viruapp.R;
import com.example.viruapp.ui.activity.HomeActivity;
import com.example.viruapp.ui.activity.fragments.ModulDetailFragment;

import java.util.ArrayList;

public class ModulAdapter extends PagerAdapter {

    private ArrayList<Modul> mDataSet;

    public ModulAdapter(Context context) {
        this.mDataSet = new ArrayList<>();
        this.context = context;
    }

    public void setDataSet(ArrayList<Modul> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged(); //Para indicar que hay cambios
    }

    private LayoutInflater layoutInflater;
    private Context context;

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.modul_view, container, false);

        final Modul modul = mDataSet.get(position);

        TextView nombre, solicitud;
        nombre = view.findViewById(R.id.txt_name);
        solicitud = view.findViewById(R.id.txt_solicitud);

        nombre.setText(modul.getName());
        solicitud.setText(modul.getSolicitud());

        nombre.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("modul_id", modul.getId());
            bundle.putString("modul_name", modul.getName());
            bundle.putString("modul_solicitud", modul.getSolicitud());
            bundle.putString("modul_informe", modul.getInforme());
            bundle.putString("modul_memo", modul.getMemorandum());
            bundle.putString("modul_recibo", modul.getRecibo());
            bundle.putString("modul_proyecto", modul.getProyecto());
            bundle.putString("modul_fsupervision", modul.getF_supervision());
            bundle.putString("modul_fevaluacion", modul.getF_evaluacion());

            ModulDetailFragment fragment = new ModulDetailFragment();
            fragment.setArguments(bundle);
            FragmentManager manager = ((HomeActivity)context).getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();

            ft.replace(R.id.content_home, fragment);
            ft.addToBackStack(null);
            ft.commit();
            Toast.makeText(context, "Modul_id = " + modul.getRecibo(), Toast.LENGTH_SHORT).show();
        });

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object );
    }
}
