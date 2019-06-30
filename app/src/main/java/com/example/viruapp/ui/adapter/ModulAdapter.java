package com.example.viruapp.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viruapp.Model.Modul;
import com.example.viruapp.R;
import com.example.viruapp.io.AppViruApiAdapter;
import com.example.viruapp.ui.activity.HomeActivity;
import com.example.viruapp.ui.activity.fragments.ModulDetailFragment;
import com.example.viruapp.ui.activity.fragments.ModulListFragment;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModulAdapter extends PagerAdapter implements Callback<Void> {

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

        CardView cardmodul;
        ImageView img_modul;
        TextView nombre;

        nombre = view.findViewById(R.id.txt_name);
        img_modul = view.findViewById(R.id.img_modul);
        cardmodul = view.findViewById(R.id.card_modul);

        nombre.setText(modul.getName());

        if(modul.getMemorandum()==null||modul.getSolicitud()==null||modul.getInforme()==null||
           modul.getRecibo().contentEquals("0")||modul.getProyecto().contentEquals("0")||modul.getF_supervision().contentEquals("0")){
            img_modul.setImageResource(R.drawable.imcompleto);
        }else{
            img_modul.setImageResource(R.drawable.completo);
        }

        cardmodul.setOnClickListener(v -> {
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
        });

        cardmodul.setOnLongClickListener(v -> {
            ShowDelateModulDialog(modul.getId(), modul.getName(),container,view);
            return true;
        });

        container.addView(view,0);
        return view;
    }


    private void ShowDelateModulDialog(int id, String name,ViewGroup container, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Eliminar");
        builder.setMessage("Seguro de eliminar el "+ name);
        SharedPreferences preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String token = "Bearer " + preferences.getString("token", "");
        builder.setPositiveButton("Si", (dialog, which) -> {
            Call<Void> call = AppViruApiAdapter.getApiService().deleteModul(token, id);
            call.enqueue(this);
            container.removeView(view);
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        Toast.makeText(context, "Módulo eliminado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Toast.makeText(context, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object );
    }

}

